package org.gurms.dao.hibernate.system;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageRequest.Sort;
import org.gurms.entity.system.SysDictValue;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class SysDictValueDao extends HibernateDao<SysDictValue> {
	@Override
	protected void setDefaultOrderBy(final Criteria c, final PageRequest pageRequest){
		Sort dictCode = new Sort("dictcode", Sort.ASC);
		Sort dictOrder = new Sort("dictorder", Sort.ASC);
		this.setOrderByToCriteria(c, pageRequest, dictCode);
		this.setOrderByToCriteria(c, pageRequest, dictOrder);
	}
}
