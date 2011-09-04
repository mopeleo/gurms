package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysDict;
import org.gurms.entity.system.SysDictPK;

public interface SysDictService {

	public PageResult<SysDict> query(Map<String, Object> request, PageRequest page);
	
	public SysDict get(SysDictPK id);
	
	public Map<String,List<SysDict>> getDictMap();

	@GurmsValid(type=SysDict.class)
	public PageResult<SysDict> save(SysDict org);
	
	public PageResult<SysDict> delete(SysDictPK id);
	
	public List<SysDict> getDictType();
}
