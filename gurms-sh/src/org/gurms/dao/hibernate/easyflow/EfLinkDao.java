package org.gurms.dao.hibernate.easyflow;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.easyflow.EfLink;
import org.springframework.stereotype.Repository;

@Repository
public class EfLinkDao extends HibernateDao<EfLink>{
	
	protected static final String DELETE_BYFLOWID = "delete from EfLink t where t.flow.flowid = ?"; 
	
	public void deleteByFlowId(long flowid){
		batchExecute(DELETE_BYFLOWID, flowid);
	}
}
