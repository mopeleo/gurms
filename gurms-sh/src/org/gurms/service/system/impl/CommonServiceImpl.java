package org.gurms.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.gurms.dao.hibernate.HibernateNativeDao;
import org.gurms.entity.PageResult;
import org.gurms.entity.SQLParam;
import org.gurms.service.system.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	private HibernateNativeDao nativeDao;

	public HibernateNativeDao getNativeDao() {
		return nativeDao;
	}

	public void setNativeDao(HibernateNativeDao nativeDao) {
		this.nativeDao = nativeDao;
	}

	public PageResult query(){
		SQLParam p = new SQLParam(1, SQLParam.Type.CURSOR, null, SQLParam.Direct.OUT);
		List<SQLParam> params = new ArrayList<SQLParam>();
		params.add(p);
		return nativeDao.spExecute("fxq_query.querycountry", params);
	}
}
