package org.gurms.service.impl.easyflow;

import java.util.List;
import java.util.Map;

import org.gurms.dao.hibernate.easyflow.EfFlowDao;
import org.gurms.dao.hibernate.easyflow.EfLinkDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.easyflow.EfFlow;
import org.gurms.entity.easyflow.EfLink;
import org.gurms.service.easyflow.EfFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EfFlowServiceImpl implements EfFlowService {

	@Autowired
	private EfFlowDao efFlowDao;
	
	@Autowired
	private EfLinkDao efLinkDao;
	
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
	public EfFlow getById(Long id){
		return efFlowDao.getById(id);
	}
	
	@Override
	public PageResult<EfFlow> save(EfFlow entity){
		PageResult<EfFlow> result = new PageResult<EfFlow>();
		efFlowDao.save(entity);
		return result;
	}

	@Override
	public PageResult<EfFlow> deleteById(Long flowid){
		PageResult<EfFlow> result = new PageResult<EfFlow>();
		if(flowid == null){
			result.setSuccess(false);
			result.setReturnmsg("flowid不能为空");
		}else{
			efLinkDao.deleteByFlowId(flowid);
			efFlowDao.deleteById(flowid);
		}
		return result;
	}

	@Override
	public PageResult<EfFlow> save(EfFlow flow, List<EfLink> links) {
		PageResult<EfFlow> result = new PageResult<EfFlow>();
		flow.setLinks(links);
		efFlowDao.save(flow);
		
		//先删除link
		if(flow.getFlowid() != null){
			efLinkDao.deleteByFlowId(flow.getFlowid());
		}
		for(EfLink link : links){
			link.setFlow(flow);
			efLinkDao.save(link);
		}

		return result;
	}
}
