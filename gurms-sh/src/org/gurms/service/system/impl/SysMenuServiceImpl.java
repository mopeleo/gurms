package org.gurms.service.system.impl;

import java.util.Iterator;
import java.util.List;

import org.gurms.common.config.GlobalParam;
import org.gurms.dao.hibernate.system.SysMenuDao;
import org.gurms.entity.system.SysMenu;
import org.gurms.service.system.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuDao sysMenuDao;
	
	public SysMenuDao getSysMenuDao() {
		return sysMenuDao;
	}

	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}

	@Override
	@Transactional(readOnly = true)
	public SysMenu getMenuTree(String nodeId) {
		SysMenu root = sysMenuDao.get(nodeId);
		initMenus(root);
		return root;
	}

	@Override
	@Transactional(readOnly = true)
	public SysMenu getUserMenuTree(String userid, SysMenu root) {
		SysMenu allMenu = null;
		if(root == null || !GlobalParam.MENU_ROOTID.equals(root.getMenuid())){
			allMenu = getMenuTree(GlobalParam.MENU_ROOTID);
		}else{
			allMenu = root;
		}
		List<SysMenu> menus = sysMenuDao.getUserMenus(userid);
		createMenuTree(allMenu, menus);
		return allMenu;
	}
	
	@Transactional(readOnly = true)
	private void initMenus(SysMenu root){
		sysMenuDao.initProxyObject(root);
		for(SysMenu menu : root.getSubmenus()){
			initMenus(menu);
		}
	}

	@Transactional(readOnly = true)
	private void createMenuTree(SysMenu root, List<SysMenu> existList){
		Iterator<SysMenu> it = root.getSubmenus().iterator();
		while(it.hasNext()){
			SysMenu menu = it.next();
			if(existList.contains(menu)){
				createMenuTree(menu, existList);
			}else{
				it.remove();
			}
		}
	}	
}
