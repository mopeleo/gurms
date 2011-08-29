package org.gurms.service.system.impl;

import java.util.Map;

import org.gurms.dao.hibernate.system.SysLogLoginDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysLogLogin;
import org.gurms.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysLogServiceImpl implements SysLogService{

	@Autowired
	private SysLogLoginDao sysLogLoginDao;
	
	public SysLogLoginDao getSysLogLoginDao() {
		return sysLogLoginDao;
	}

	public void setSysLogLoginDao(SysLogLoginDao sysLogLoginDao) {
		this.sysLogLoginDao = sysLogLoginDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysLogLogin> queryLogin(Map<String, Object> request, PageRequest page) {
		return sysLogLoginDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

}
