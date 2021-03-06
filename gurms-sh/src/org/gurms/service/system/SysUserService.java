package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysUser;
import org.gurms.entity.system.SysUserConfig;
import org.gurms.entity.system.SysUserInfo;

public interface SysUserService{

	public List<SysUser> query(Map<String, Object> request);
	
	public PageResult<SysUser> query(Map<String, Object> request, PageRequest page);
	
	@GurmsValid(type=SysUser.class, props={"userid","username"})
	public PageResult<SysUser> save(SysUser user);

	@GurmsValid(type=SysUser.class, props={"userid","username"})
	public PageResult<SysUser> insert(SysUser user);

	public SysUser getById(String userid);
	
	public PageResult<SysUser> deleteById(String id);
	
	@GurmsValid(type=SysUser.class, props={"userid","loginpassword"})
	public PageResult<SysUser> login(SysUser user);
	
	public boolean isAdmin(String userid);
	
	@GurmsValid(type=SysUser.class, props={"userid","loginpassword"})
	public PageResult<SysUser> setPassword(SysUser user);
	
	public SysUserInfo getUserInfoById(String userid);
	
	@GurmsValid(type=SysUserInfo.class)
	public PageResult<SysUserInfo> saveUserInfo(SysUserInfo userinfo);
	
	public SysUserConfig getUserConfigById(String userid);
	
	@GurmsValid(type=SysUserConfig.class)
	public PageResult<SysUser> saveUserConfig(SysUserConfig config);
}
