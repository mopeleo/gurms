package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysOrg;

public interface SysOrgService {

	public List<SysOrg> query(Map<String, Object> request);

	public PageResult<SysOrg> query(Map<String, Object> request, PageRequest page);
	
	public SysOrg getById(String id);

	@GurmsValid(type=SysOrg.class)
	public PageResult<SysOrg> save(SysOrg org);
	
	public PageResult<SysOrg> deleteById(String id);
	
	public SysOrg getRoot();
}
