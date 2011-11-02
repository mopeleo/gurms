package org.gurms.dao.hibernate.system;

import java.io.Serializable;
import java.util.List;

import org.gurms.dao.hibernate.HibernateDao;
import org.gurms.entity.system.SysMenu;
import org.springframework.stereotype.Repository;


@Repository
public class SysMenuDao extends HibernateDao<SysMenu>{
	
	public static final String QUERY_MENUS_BY_USERID = "select distinct m from SysMenu m,SysRole r,SysUser u where m in elements(r.sysmenus) and r in elements(u.sysroles) and r.rolestatus='1' and r.enable='1' and u.userid=? ";
	
	public List<SysMenu> getUserMenus(Serializable userid){
		return createQuery(QUERY_MENUS_BY_USERID, userid).list();
	}
}
