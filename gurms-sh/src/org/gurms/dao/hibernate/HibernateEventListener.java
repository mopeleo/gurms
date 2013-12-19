package org.gurms.dao.hibernate;

import javax.persistence.Table;

import org.gurms.common.util.DateUtil;
import org.gurms.common.util.SpringUtil;
import org.gurms.entity.Logable;
import org.gurms.entity.system.SysLogOperate;
import org.gurms.entity.system.SysUser;
import org.gurms.web.WebConstants;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

public class HibernateEventListener implements PostInsertEventListener,
		PostUpdateEventListener, PostDeleteEventListener {

	private static final String DELETE = "删除";
	private static final String INSERT = "新增";
	private static final String UPDATE = "修改";

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		doLog(event.getSession(), event.getEntity(), event.getId().toString(), DELETE);
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		doLog(event.getSession(), event.getEntity(), event.getId().toString(), UPDATE);
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		doLog(event.getSession(), event.getEntity(), event.getId().toString(), INSERT);
	}
	
	private void doLog(Session session, Object entity, String id, String operatetype){
		if (entity.getClass().isAnnotationPresent(Logable.class)) {
			SysUser user = (SysUser)SpringUtil.getHttpSession().getAttribute(WebConstants.S_KEY_USER);
			SysLogOperate log = new SysLogOperate();
			log.setOperatedate(DateUtil.getCurrentDate());
			log.setOperatetime(DateUtil.getCurrentTime());
			log.setOperatetype(operatetype);
			if(user == null){
				log.setUserid("-login-");
			}else{
				log.setUserid(user.getUserid());
			}
			log.setOperateurl(SpringUtil.getHttpRequest().getServletPath());
			log.setDataid(id);			
			Table table = entity.getClass().getAnnotation(Table.class);
			if(table != null){
				log.setOperatetable(table.name());
			}else{
				log.setOperatetable(entity.getClass().getName());
			}
			saveLog(session, log);
		}
	}

	// 另外启动一个session处理审核日志的保存
	private void saveLog(Session session, SysLogOperate entry) {
		Session tempSession = session.getSessionFactory().openSession();
		Transaction tx = tempSession.beginTransaction();
		try {
			tx.begin();
			tempSession.save(entry);
			tempSession.flush();
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
		}
		tempSession.close();
	}
}
