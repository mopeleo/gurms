package org.gurms.dao.hibernate.system;

import java.io.Serializable;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.system.SysRole;
import org.gurms.entity.system.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleDao extends HibernateDao<SysRole> {

	@Override
	/**
	 * 删除sysrole不级联删除sysuser，但要删除两者之间的关系
	 * 所以要手动更新user的sysroles属性
	 */
	public void delete(Serializable id){
		SysRole role = get(id);
		for(SysUser user : role.getSysusers()){
			user.getSysroles().remove(role);
		}
		super.delete(id);
	}
}
