package org.gurms.service.impl.easyflow;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gurms.dao.hibernate.easyflow.EfFlowDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.easyflow.EfFlow;
import org.gurms.service.easyflow.EfFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EfFlowServiceImpl implements EfFlowService {

	@Autowired
	private EfFlowDao efFlowDao;
	
	public EfFlowDao getEfFlowDao() {
		return efFlowDao;
	}

	public void setEfFlowDao(EfFlowDao efFlowDao) {
		this.efFlowDao = efFlowDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EfFlow> query(Map<String, Object> request){
		return efFlowDao.find(PropertyFilter.buildFromRequestMap(request));
	}
	
	@Override
	@Transactional(readOnly = true)
	public PageResult<EfFlow> query(Map<String, Object> request, PageRequest page){
		return efFlowDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}
	
	@Override
	@Transactional(readOnly = true)
	public EfFlow get(String id){
		return efFlowDao.get(id);
	}
	
	@Override
	public PageResult<EfFlow> save(EfFlow entity){
		PageResult<EfFlow> result = new PageResult<EfFlow>();
		efFlowDao.save(entity);
		return result;
	}

	@Override
	public PageResult<EfFlow> delete(String id){
		PageResult<EfFlow> result = new PageResult<EfFlow>();
		if(StringUtils.isBlank(id)){
			result.setSuccess(false);
			result.setReturnmsg("ID不能为空");
		}else{
			efFlowDao.delete(id);
		}
		return result;
	}
}
