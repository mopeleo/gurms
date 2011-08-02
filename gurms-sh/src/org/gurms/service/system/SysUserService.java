package org.gurms.service.system;

import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysUser;

public interface SysUserService{

	@GurmsValid(type=SysUser.class)
	public void save(SysUser user);

	public SysUser get(String userid);
	
	public PageResult<SysUser> query(Map<String, Object> request, PageRequest page);
	
	public void delete(String id);
	
	public boolean isAdmin(String userid);
	
	@GurmsValid(type=SysUser.class, props={"userid","loginpassword"})
	public PageResult<SysUser> setPassword(SysUser user);
}
