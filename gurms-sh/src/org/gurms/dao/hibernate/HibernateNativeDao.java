package org.gurms.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gurms.common.config.GlobalParam;
import org.gurms.entity.PageResult;
import org.gurms.entity.SQLParam;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateNativeDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Set<String> getAllEntity(){
		return sessionFactory.getAllClassMetadata().keySet();
	}
	
	public PageResult spExecute(String spName, List<SQLParam> params) {
		PageResult result = new PageResult();
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			cs = createCallableStatement(conn, spName, params);
			cs.execute();
			
			//寻找出参
			Iterator<SQLParam> it = params.iterator();
			while(it.hasNext()){
				SQLParam s = it.next();
				if(s.getDir() == SQLParam.Direct.IN){
					it.remove();
				}
			}
			if(params.size() > 0){
				for(SQLParam p : params){
					if(p.getType() == SQLParam.Type.INT){
						if(cs.getInt(p.getIndex()) == GlobalParam.SP_SUCCESS_INT){
							result.setSuccess(true);
						}else{
							result.setSuccess(false);
						}
					}else if(p.getType() == SQLParam.Type.CURSOR){
						rs = (ResultSet) cs.getObject(p.getIndex());
						while(rs.next()){
							Map<String, String> row = new HashMap<String, String>();
							ResultSetMetaData rsmd = rs.getMetaData();
							int col = rsmd.getColumnCount() + 1;
							for(int i = 1; i < col; i++){
								String colName = rsmd.getColumnName(i);
								row.put(colName.toLowerCase(), rs.getString(colName));
							}
							result.addResult(row);
						}
					}else{
						result.setReturnmsg(cs.getString(p.getIndex()));
					}
				}
			}
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
			result.setSuccess(false);
			result.setReturnmsg("执行[" + spName + "]出错");
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (cs != null) {
					cs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				
				result.setSuccess(false);
				result.setReturnmsg(ex.getMessage());
			}
		}
		
		return result;
	}

	private Connection getConnection() throws SQLException {
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory())
				.getConnectionProvider();
		return cp.getConnection();
	}

	private CallableStatement createCallableStatement(Connection conn, String spName, 
					List<SQLParam> params) throws SQLException {
		StringBuffer sp = new StringBuffer();
		sp.append("{call ");
		sp.append(spName);
		sp.append("(");
		if (params != null && params.size()>0) {
			int loop = params.size() - 1;
			for (int i = 0; i < loop; i++) {
				sp.append("?,");
			}
			sp.append("?");
		}
		sp.append(")}");

		CallableStatement cs = conn.prepareCall(sp.toString());
		for (SQLParam param : params) {
			if(param.getDir() == SQLParam.Direct.IN){
				setInParam(cs, param);
			}else{
				cs.registerOutParameter(param.getIndex(), param.getType().getValue());
			}
		}

		return cs;
	}
	
	private void setInParam(CallableStatement cs, SQLParam param) throws SQLException{
		switch (param.getType()){
			case INT:
				cs.setInt(param.getIndex(),(Integer)param.getValue());
				break;
			case FLOAT:
				cs.setFloat(param.getIndex(),(Float)param.getValue());
				break;
			default :
				cs.setString(param.getIndex(), String.valueOf(param.getValue()));
				break;
		}
	}
}
