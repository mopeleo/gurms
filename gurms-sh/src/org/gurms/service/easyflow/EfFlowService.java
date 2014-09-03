package org.gurms.service.easyflow;

import java.util.List;
import java.util.Map;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.easyflow.EfFlow;

public interface EfFlowService{

	public List<EfFlow> query(Map<String, Object> request);
	
	public PageResult<EfFlow> query(Map<String, Object> request, PageRequest page);
	
	public EfFlow getById(Long flowid);
	
	public PageResult<EfFlow> save(EfFlow obj);

	public PageResult<EfFlow> deleteById(Long flowid);
}
