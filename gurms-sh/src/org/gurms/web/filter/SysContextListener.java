package org.gurms.web.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.gurms.common.config.GlobalMessage;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.SpringUtil;
import org.gurms.entity.system.SysDictIndex;
import org.gurms.entity.system.SysDictValue;
import org.gurms.entity.system.SysMenu;
import org.gurms.service.system.SysDictService;
import org.gurms.service.system.SysMenuService;
import org.gurms.service.system.SysParamService;
import org.gurms.service.system.SysRoleService;
import org.gurms.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

public class SysContextListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().removeAttribute("base");
		event.getServletContext().removeAttribute("bundle");
		event.getServletContext().removeAttribute("statics");
		event.getServletContext().removeAttribute(WebConstants.C_KEY_DICT);
		event.getServletContext().removeAttribute(WebConstants.C_KEY_PARAM);
		event.getServletContext().removeAttribute(WebConstants.C_KEY_MENU);
//		event.getServletContext().removeAttribute(WebConstants.C_KEY_ORG);
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		context.setAttribute("base", context.getContextPath());

		// freemark 初始化-------------------------
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		ResourceBundleModel rbm = new ResourceBundleModel(GlobalMessage.message, wrapper);
		context.setAttribute("bundle", rbm);
		context.setAttribute("statics", wrapper.getStaticModels());
		logger.info("initialize freemark resource success......");
		
		// 缓存数据----------------------------------
		SysDictService dictService = SpringUtil.getBean("sysDictServiceImpl");
		List<SysDictIndex> dictType = dictService.getDictIndex();
		context.setAttribute(WebConstants.C_KEY_DICTTYPE, dictType);

		Map<String, List<SysDictValue>> dictMap = dictService.getDictMap();
		context.setAttribute(WebConstants.C_KEY_DICT, dictMap);
		logger.info("cache sys_dict data success......");
		
		SysParamService paramService = SpringUtil.getBean("sysParamServiceImpl");
		Map<Integer, String> paramMap = paramService.getParamMap();
		context.setAttribute(WebConstants.C_KEY_PARAM, paramMap);
		logger.info("cache sys_param data success......");
		
		SysMenuService menuService = SpringUtil.getBean("sysMenuServiceImpl");
		SysMenu menuList = menuService.getMenuTree(GlobalParam.MENU_ROOTID);
		context.setAttribute(WebConstants.C_KEY_MENU, menuList);
		logger.info("cache sys_menu data success......");
		
//		SysOrgService orgService = SpringUtil.getBean("sysOrgServiceImpl");
//		SysOrg org = orgService.getRoot();
//		context.setAttribute(WebConstants.C_KEY_ORG, org);
//		logger.info("cache sys_org data success.....");
		
		// 更新数据----------------------------------
		SysRoleService roleService = SpringUtil.getBean("sysRoleServiceImpl");
		roleService.freshRole();
		logger.info("fresh sys_role success.....");
	}
}
