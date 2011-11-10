package org.gurms.dao.hibernate.system;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.system.SysSerialno;
import org.springframework.stereotype.Repository;

@Repository
public class SysSerialnoDao extends HibernateDao<SysSerialno> {
	
	public synchronized long next(String type){
		SysSerialno serial = get(type);
		long nextvalue = serial.getPrevalue() + 1;
		serial.setPrevalue(nextvalue);
		return nextvalue;
	}
}
