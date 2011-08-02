package org.gurms.service.system;

import java.util.List;
import java.util.Map;

import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysParam;

public interface SysParamService {

	public PageResult<SysParam> save(List<SysParam> paramList);
	
	public List<SysParam> getParamList();
	
	public SysParam getParam(String id);
	
	public Map<String, String> getParamMap();
}
