package org.gurms.service.impl.system;

import java.util.List;
import java.util.Map;

import org.gurms.dao.hibernate.system.SysAccessoryDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysAccessory;
import org.gurms.service.system.SysAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysAccessoryServiceImpl implements SysAccessoryService {

	@Autowired
	private SysAccessoryDao sysAccessoryDao;

	public SysAccessoryDao getSysAccessoryDao() {
		return sysAccessoryDao;
	}

	public void setSysAccessoryDao(SysAccessoryDao sysAccessoryDao) {
		this.sysAccessoryDao = sysAccessoryDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysAccessory> query(Map<String, Object> request, PageRequest page) {
		return sysAccessoryDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysAccessory> query(Map<String, Object> request) {
		return sysAccessoryDao.find(PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	public PageResult<SysAccessory> save(SysAccessory accessory) {
		PageResult<SysAccessory> page = new PageResult<SysAccessory>();
		sysAccessoryDao.save(accessory);
		return page;
	}

	@Override
	public void delete(String id) {
		sysAccessoryDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public SysAccessory get(String id) {
		return sysAccessoryDao.get(id);
	}

}
