package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysAccessory;

public interface SysAccessoryService {
	
	public List<SysAccessory> query(Map<String, Object> request);

	public PageResult<SysAccessory> query(Map<String, Object> request, PageRequest page);

	public PageResult<SysAccessory> save(SysAccessory accessory);
	
	public void deleteById(String id);
	
	public SysAccessory getById(String id);
}
