package org.gurms.service.impl.system;

import org.gurms.dao.hibernate.system.SysSerialnoDao;
import org.gurms.entity.system.SysSerialno;
import org.gurms.service.system.SysSerialnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysSerialnoServiceImpl implements SysSerialnoService {

	@Autowired
	private SysSerialnoDao serialnoDao;
	
	public SysSerialnoDao getSerialnoDao() {
		return serialnoDao;
	}

	public void setSerialnoDao(SysSerialnoDao serialnoDao) {
		this.serialnoDao = serialnoDao;
	}

	@Override
	public String getId(String type) {
		// TODO Auto-generated method stub
		SysSerialno serial = serialnoDao.getId(type);
		return serial.generateId();
	}

	@Override
	public String[] getBatchId(String type, int num) {
		// TODO Auto-generated method stub
		SysSerialno serial = serialnoDao.getBatchId(type, num);
		
		SysSerialno copy = new SysSerialno();
		copy.setFixflag(serial.getFixflag());
		copy.setFixlength(serial.getFixlength());
		copy.setPrefix(serial.getPrefix());
		copy.setPrevalue(serial.getPrevalue());
		copy.setSuffix(serial.getSuffix());
		String[] ids = new String[num];
		long value = copy.getPrevalue();
		for(int i = 0; i < num; i++){
			copy.setPrevalue(value + i);
			String id = copy.generateId();
			ids[i] = id;
		}
		return ids;
	}	
}
