package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysRole;

public interface SysRoleService {

	public PageResult<SysRole> query(Map<String, Object> request, PageRequest page);
	
	public SysRole get(String id);
	
	public List<SysRole> getAll();
	
	public PageResult<SysRole> save(SysRole role);
	
	public PageResult<SysRole> insert(SysRole role);
	
	public void delete(String id);
}
