package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysLogLogin;
import org.gurms.entity.system.SysLogOperate;

public interface SysLogService {
	
	public List<SysLogLogin> queryLogin(Map<String, Object> request);

	public PageResult<SysLogLogin> queryLogin(Map<String, Object> request, PageRequest page);

	public List<SysLogOperate> queryOperate(Map<String, Object> request);
	
	public PageResult<SysLogOperate> queryOperate(Map<String, Object> request, PageRequest page);

}
