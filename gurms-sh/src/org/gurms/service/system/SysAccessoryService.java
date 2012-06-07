package org.gurms.service.system;

import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysAccessory;

public interface SysAccessoryService {
	
	public PageResult<SysAccessory> query(Map<String, Object> request, PageRequest page);

	public PageResult<SysAccessory> save(SysAccessory role);
	
	public void delete(long id);
}
