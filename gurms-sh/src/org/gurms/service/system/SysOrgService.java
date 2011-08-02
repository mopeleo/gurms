package org.gurms.service.system;

import java.util.Map;

import org.gurms.common.validate.GurmsValid;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysOrg;

public interface SysOrgService {

	public PageResult<SysOrg> query(Map<String, Object> request, PageRequest page);
	
	public SysOrg get(String id);

	@GurmsValid(type=SysOrg.class)
	public PageResult<SysOrg> save(SysOrg org);
	
	public void delete(String id);
	
	public SysOrg getRoot();
}
