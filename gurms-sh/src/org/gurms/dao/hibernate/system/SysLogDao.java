package org.gurms.dao.hibernate.system;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageRequest.Sort;
import org.gurms.entity.system.SysLog;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class SysLogDao extends HibernateDao<SysLog> {
	
	@Override
	protected void setDefaultOrderBy(final Criteria c, final PageRequest pageRequest){
		Sort loginDate = new Sort("operatedate", Sort.DESC);
		Sort loginTime = new Sort("operatetime", Sort.DESC);
		this.setOrderByToCriteria(c, pageRequest, loginDate);
		this.setOrderByToCriteria(c, pageRequest, loginTime);
	}
}
