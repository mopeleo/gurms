package org.gurms.dao.hibernate.system;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageRequest.Sort;
import org.gurms.entity.system.SysDict;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class SysDictDao extends HibernateDao<SysDict> {
	@Override
	protected void setDefaultOrderBy(final Criteria c, final PageRequest pageRequest){
		Sort dictType = new Sort("dicttype", Sort.ASC);
		Sort dictOrder = new Sort("dictorder", Sort.ASC);
		this.setOrderByToCriteria(c, pageRequest, dictType);
		this.setOrderByToCriteria(c, pageRequest, dictOrder);
	}
}
