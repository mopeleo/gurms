package org.gurms.service.system.impl;

import org.gurms.dao.hibernate.system.SysSerialnoDao;
import org.gurms.entity.system.SysSerialno;
import org.gurms.service.system.SysSerialnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysSerialnoImpl implements SysSerialnoService {

	@Autowired
	private SysSerialnoDao serialnoDao;
	
	public SysSerialnoDao getSerialnoDao() {
		return serialnoDao;
	}

	public void setSerialnoDao(SysSerialnoDao serialnoDao) {
		this.serialnoDao = serialnoDao;
	}

	@Override
	@Transactional
	public long next(String type) {
		return serialnoDao.next(type);
	}
}
