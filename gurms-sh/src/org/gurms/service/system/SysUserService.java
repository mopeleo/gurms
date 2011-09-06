package org.gurms.service.system;

import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysUser;

public interface SysUserService{

	@GurmsValid(type=SysUser.class, props={"userid","username"})
	public PageResult<SysUser> save(SysUser user);

	public SysUser get(String userid);
	
	public PageResult<SysUser> query(Map<String, Object> request, PageRequest page);
	
	public PageResult<SysUser> delete(String id);
	
	@GurmsValid(type=SysUser.class, props={"userid","loginpassword"})
	public PageResult<SysUser> login(SysUser user);
	
	public boolean isAdmin(String userid);
	
	@GurmsValid(type=SysUser.class, props={"userid","loginpassword"})
	public PageResult<SysUser> setPassword(SysUser user);
}
