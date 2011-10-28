package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysRole;

public interface SysRoleService {

	public List<SysRole> query(Map<String, Object> request);
	
	public PageResult<SysRole> query(Map<String, Object> request, PageRequest page);
	
	public List<SysRole> getAll();
	
	public SysRole get(String id);
	
	@GurmsValid(type=SysRole.class, props={"roleid","rolename","roletype","creator"})
	public PageResult<SysRole> save(SysRole role);
	
	@GurmsValid(type=SysRole.class, props={"rolename","roletype","creator"})
	public PageResult<SysRole> insert(SysRole role);
	
	@GurmsValid(type=SysRole.class, props={"roleid"})
	public void delete(String id);
	
	public void freshRole();
}
