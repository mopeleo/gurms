package org.gurms.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateNativeDao {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 采用@Autowired按类型注入SessionFactory,当有多个SesionFactory的时候Override本函数.
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Set<String> getAllEntity(){
		return sessionFactory.getAllClassMetadata().keySet();
	}
	
	public void spQuery(String spName, List<SPParam> params) {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cs = createCallableStatement(conn, spName, params);
			cs.execute();
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				System.out.println(rs.getString(1) + " : " + rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("数据库操作失败", e);
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
				logger.error("数据库关闭连接失败", ex);
			}
		}
	}

	public void spUpdate(String procName, Object... args) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(procName);
		query.executeUpdate();
		session.close();
	}

	private Connection getConnection() throws SQLException {
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory())
				.getConnectionProvider();
		return cp.getConnection();
	}

	private CallableStatement createCallableStatement(Connection conn, String spName, 
					List<SPParam> params) throws SQLException {
		StringBuffer sp = new StringBuffer();
		sp.append("{call ");
		sp.append(spName);
		sp.append("(");
		int paramsize = params.size();
		if (paramsize > 0) {
			for (int i = 0; i < paramsize - 1; i++) {
				sp.append("?,");
			}
			sp.append("?");
		}
		sp.append(")}");

		CallableStatement cs = conn.prepareCall(sp.toString());
		for (SPParam param : params) {

		}

		return cs;
	}

	public class SPParam {
		private int index;
		private int type;
		private Object value;
		private int dir;

		public SPParam() {}

		public SPParam(int index, int type, Object value, int dir) {
			this.index = index;
			this.type = type;
			this.value = value;
			this.dir = dir;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public int getDir() {
			return dir;
		}

		public void setDir(int dir) {
			this.dir = dir;
		}
	}
}
