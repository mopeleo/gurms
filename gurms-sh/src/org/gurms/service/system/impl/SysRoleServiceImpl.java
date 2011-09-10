package org.gurms.service.system.impl;

import java.util.List;
import java.util.Map;

import org.gurms.dao.hibernate.system.SysMenuDao;
import org.gurms.dao.hibernate.system.SysRoleDao;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.PropertyFilter;
import org.gurms.entity.system.SysMenu;
import org.gurms.entity.system.SysRole;
import org.gurms.service.system.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	private Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

	@Autowired
	private SysRoleDao sysRoleDao;	
	
	@Autowired
	private SysMenuDao sysMenuDao;
	
	public SysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	public SysMenuDao getSysMenuDao() {
		return sysMenuDao;
	}

	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<SysRole> query(Map<String, Object> request, PageRequest page) {
		return sysRoleDao.findPage(page, PropertyFilter.buildFromRequestMap(request));
	}

	@Override
	@Transactional(readOnly = true)
	public SysRole get(String id) {
		return sysRoleDao.get(id);
	}

	@Override
	public void save(SysRole role) {
		if(role.getSysmenuids() != null){
			for(String menuid : role.getSysmenuids()){
				SysMenu menu = sysMenuDao.get(menuid);
				role.getSysmenus().add(menu);
			}
		}
		sysRoleDao.save(role);
	}

	@Override
	public void delete(String id) {
		sysRoleDao.delete(id);
	}

	@Override
	public List<SysRole> getAll() {
		return sysRoleDao.getAll();
	}	

}
