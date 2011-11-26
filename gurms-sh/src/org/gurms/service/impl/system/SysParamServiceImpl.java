package org.gurms.service.impl.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gurms.dao.hibernate.system.SysParamDao;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysParam;
import org.gurms.service.system.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysParamServiceImpl implements SysParamService {

	@Autowired
	private SysParamDao paramDao;
	
	public SysParamDao getParamDao() {
		return paramDao;
	}

	public void setParamDao(SysParamDao paramDao) {
		this.paramDao = paramDao;
	}

	@Override
	public PageResult<SysParam> save(List<SysParam> paramList) {
		PageResult<SysParam> result = new PageResult<SysParam>();
		for(SysParam param : paramList){
			paramDao.save(param);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysParam> getParamList() {
		return paramDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public SysParam getParam(String id) {
		return paramDao.get(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> getParamMap() {
		Map<String, String> paramMap = new HashMap<String, String>();
		List<SysParam> list = paramDao.getAll();
		if(list != null){
			for(SysParam param : list){
				paramMap.put(param.getParamid(), param.getParamvalue());
			}
		}
		return paramMap;
	}

}
