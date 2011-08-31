package org.gurms.dao.hibernate.system;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageRequest.Sort;
import org.gurms.entity.system.SysLogLogin;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class SysLogLoginDao extends HibernateDao<SysLogLogin> {
	
	@Override
	protected void setDefaultOrderBy(final Criteria c, final PageRequest pageRequest){
		Sort loginDate = new Sort("logindate", Sort.DESC);
		Sort loginTime = new Sort("logintime", Sort.DESC);
		this.setOrderByToCriteria(c, pageRequest, loginDate);
		this.setOrderByToCriteria(c, pageRequest, loginTime);
	}
}
