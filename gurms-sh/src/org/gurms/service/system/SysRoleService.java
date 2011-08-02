package org.gurms.service.system;

import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysRole;

public interface SysRoleService {

	public PageResult<SysRole> query(Map<String, Object> request, PageRequest page);
	
	public SysRole get(String id);
	
	public void save(SysRole role);
	
	public void delete(String id);
}
