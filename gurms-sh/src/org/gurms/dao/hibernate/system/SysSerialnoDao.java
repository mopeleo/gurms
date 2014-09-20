package org.gurms.dao.hibernate.system;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.system.SysSerialno;
import org.springframework.stereotype.Repository;

@Repository
public class SysSerialnoDao extends HibernateDao<SysSerialno> {
	
	public synchronized SysSerialno getId(String type){
		SysSerialno serial = getById(type);
		long nextvalue = serial.getPrevalue() + 1;
		serial.setPrevalue(nextvalue);
		return serial;
	}
	
	public synchronized SysSerialno getBatchId(String type, int num){
		SysSerialno serial = getById(type);
		long nextvalue = serial.getPrevalue() + num;
		serial.setPrevalue(nextvalue);
		return serial;
	}
}
