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
		List<SysParam> entityList = paramDao.getAll();
		if(paramList != null){
			for(SysParam param : paramList){
				int index = entityList.indexOf(param);
				if(index != -1){
					SysParam entity = entityList.get(index);
					entity.setParamvalue(param.getParamvalue());
					paramDao.save(entity);
				}
			}
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
	public SysParam getParam(int id) {
		return paramDao.get(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Integer, String> getParamMap() {
		Map<Integer, String> paramMap = new HashMap<Integer, String>();
		List<SysParam> list = paramDao.getAll();
		if(list != null){
			for(SysParam param : list){
				paramMap.put(param.getParamid(), param.getParamvalue());
			}
		}
		return paramMap;
	}
	
	public void restoreInitSet() {
		List<SysParam> list = paramDao.getAll();
		if(list != null){
			for(SysParam param : list){
				param.setParamvalue(param.getInitvalue());
				paramDao.save(param);
			}
		}
	}

}
