package org.gurms.dao.hibernate.system;

import java.util.List;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageRequest.Sort;
import org.gurms.entity.system.SysDictValue;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class SysDictValueDao extends HibernateDao<SysDictValue> {
	
//	private static final String GET_DICT_BY_TYPE = "select m from SysDictValue m,SysDictIndex n where n.dicttype=? and m in elements(n.dictvalue)";
	private static final String GET_DICT_BY_TYPE = "select n.dictvalue from SysDictIndex n where n.dicttype=?";

	@Override
	protected void setDefaultOrderBy(final Criteria c, final PageRequest pageRequest){
		Sort dictCode = new Sort("dictcode", Sort.ASC);
		Sort dictOrder = new Sort("dictorder", Sort.ASC);
		this.setOrderByToCriteria(c, pageRequest, dictCode);
		this.setOrderByToCriteria(c, pageRequest, dictOrder);
	}
	
	public List<SysDictValue> getDictByType(String dicttype){
		return createQuery(GET_DICT_BY_TYPE, dicttype).list();
	}
	
	public List<SysDictValue> getAll(){
		return getSession().createCriteria(SysDictValue.class).addOrder(Order.asc("dictorder")).list();
	}
}
