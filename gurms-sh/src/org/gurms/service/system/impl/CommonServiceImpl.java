package org.gurms.service.system.impl;

import org.gurms.dao.hibernate.HibernateNativeDao;
import org.gurms.service.system.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {
	
	private Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
	
	@Autowired
	private HibernateNativeDao nativeDao;

	public HibernateNativeDao getNativeDao() {
		return nativeDao;
	}

	public void setNativeDao(HibernateNativeDao nativeDao) {
		this.nativeDao = nativeDao;
	}

}
