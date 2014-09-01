package org.gurms.dao.hibernate.system;

import java.io.Serializable;

import org.gurms.common.util.DateUtil;
import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.system.SysRole;
import org.gurms.entity.system.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleDao extends HibernateDao<SysRole> {

	private static final String UPDATE_INVALID = "update SysRole set rolestatus ='0' where rolestatus='1' and (startdate > ? or enddate < ?)";
	private static final String UPDATE_VALID = "update SysRole set rolestatus ='1' where rolestatus='0' and ? between startdate and enddate";

	@Override
	/**
	 * 删除sysrole不级联删除sysuser，但要删除两者之间的关系
	 * 所以要手动更新user的sysroles属性
	 */
	public void deleteById(Serializable id){
		SysRole role = getById(id);
		for(SysUser user : role.getSysusers()){
			user.getSysroles().remove(role);
		}
		super.deleteById(id);
	}
	
	public void freshRole(){
		String currentDate = DateUtil.getCurrentDate();
		batchExecute(UPDATE_VALID, currentDate);
		batchExecute(UPDATE_INVALID, currentDate, currentDate);
	} 
}
