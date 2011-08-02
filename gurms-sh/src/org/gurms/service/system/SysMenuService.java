package org.gurms.service.system;

import org.gurms.entity.system.SysMenu;

public interface SysMenuService {

	/**
	 * 获得一个节点下(包括该节点)的所有数据，数据结构为树形
	 * @param nodeId  节点名称
	 */
	public SysMenu getMenuTree(String nodeId);
	
	/**
	 * 获得某个用户的权限树
	 * @param userid
	 * @param root  所有菜单的菜单树
	 * @return
	 */
	public SysMenu getUserMenuTree(String userid, SysMenu root);
}
