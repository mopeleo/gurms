package org.gurms.service.system;

import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysLogLogin;

public interface SysLogService {
	
	public PageResult<SysLogLogin> queryLogin(Map<String, Object> request, PageRequest page);

}
