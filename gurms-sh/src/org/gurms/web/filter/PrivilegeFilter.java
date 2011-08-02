package org.gurms.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalMessage;
import org.gurms.common.exception.GurmsException;
import org.gurms.common.util.CommonUtil;
import org.gurms.entity.system.SysMenu;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;

/**     
 * @author huangyh Create on 2011-3-23   
 * @version 1.0
 */
public class PrivilegeFilter implements Filter {

	private String[] ignoreSuffix;

	@Override
	public void init(FilterConfig config) throws ServletException {
		String param = config.getInitParameter("ignoreSuffix");
		if(!StringUtils.isBlank(param)){
			ignoreSuffix = param.split(",");
		}
	}

	@Override
	public void destroy() {
		ignoreSuffix = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;		
		String requestUrl = request.getServletPath();
		
		//不在过滤后缀列表中的要效验session和检查菜单权限
		if(!CommonUtil.existSuffix(requestUrl,ignoreSuffix)){
			Object user = request.getSession().getAttribute(WebConstants.S_KEY_USER);
			if(user == null){
				throw new GurmsException(GlobalMessage.getMessage("9500"));
			}

			// TODO 菜单、请求的权限
			SysMenu root = (SysMenu)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_MENU);
			//请求的url首先必须在配置的权限管理返回之内
			if(hasPrivilege(requestUrl, root)){
				SysMenu privilege = (SysMenu)request.getSession().getAttribute(WebConstants.S_KEY_MENU);
				//再判断用户是否有权限
				if(!hasPrivilege(requestUrl, privilege)){
					throw new GurmsException(GlobalMessage.getMessage("9500"));
				}
			}			
		}
		
		chain.doFilter(request, response);
	}

	private boolean hasPrivilege(String url, SysMenu menus){
		if(menus == null){
			return false;
		}
		
		if("0".equals(menus.getMenutype())){
			for(SysMenu m : menus.getSubmenus()){
				if(hasPrivilege(url, m)){
					return true;
				}
			}
		}else{
			return url.endsWith(menus.getMenuurl());
		}
		return false;
	}
}
