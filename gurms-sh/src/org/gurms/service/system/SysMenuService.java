package org.gurms.service.system;

import java.util.List;

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
	 * @return
	 */
	public SysMenu getUserMenuTree(String userid);

	/**
	 * 获得多个菜单
	 * @param ids 菜单ID的集合
	 * @return
	 */
	public List<SysMenu> get(List ids);
}
