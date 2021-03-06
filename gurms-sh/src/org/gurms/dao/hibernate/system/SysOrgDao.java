package org.gurms.dao.hibernate.system;

import java.io.Serializable;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.system.SysOrg;
import org.gurms.entity.system.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class SysOrgDao extends HibernateDao<SysOrg> {

	@Override
	/**
	 * 删除sysorg不级联删除sysuser，所以要手动更新user的sysorg属性
	 */
	public void deleteById(Serializable id){
		SysOrg org = getById(id);
		for(SysUser user : org.getSysusers()){
			user.setSysorg(null);
		}
		
		//手动删除父子关系
		SysOrg parent = org.getParentorg();
		parent.getSuborgs().remove(org);
		super.deleteById(id);
	}
}
