package org.gurms.web.controller.easyflow;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.easyflow.EfFlow;
import org.gurms.entity.system.SysUser;
import org.gurms.service.easyflow.EfFlowService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EfFlowController extends BaseController {

	public static final String EFFLOW_LIST = "/efflow/list";

	@Autowired
	private EfFlowService efFlowService;	

	@RequestMapping
	public void list(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<EfFlow> result = efFlowService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}

	@RequestMapping
	public void detail(Long flowid, Model model){
		if(flowid != null){
			EfFlow entity = efFlowService.getById(flowid);
			model.addAttribute(WebConstants.KEY_RESULT, entity);
		}
	}
	
	@RequestMapping
	public void popdetail(Long flowid, Model model){
		if(flowid != null){
			EfFlow entity = efFlowService.getById(flowid);
			model.addAttribute(WebConstants.KEY_RESULT, entity);
		}
	}
	
	@RequestMapping
	public String save(HttpServletRequest request, EfFlow entity){
		if(entity.getFlowid() == null){
			SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
			entity.setUserid(user.getUserid());
		}
		efFlowService.save(entity);		
		return redirect(EFFLOW_LIST);
	}
	
	@RequestMapping
	public String delete(Long flowid){
		efFlowService.deleteById(flowid);
		return redirect(EFFLOW_LIST);
	}

	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(HttpServletRequest request, EfFlow entity){
		PageResult page = null;
		try{
			if(entity.getFlowid() == null){
				SysUser user = (SysUser)request.getSession().getAttribute(WebConstants.S_KEY_USER);
				entity.setUserid(user.getUserid());
			}
			page = efFlowService.save(entity);
		}catch(Exception e){
			page = processException(e, "保存数据出错:"+e.getMessage());
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(Long flowid){
		PageResult page = null;
		try{
			page = efFlowService.deleteById(flowid);
		}catch(Exception e){
			page = processException(e, "删除数据出错:"+e.getMessage());
		}
		return page;
	}
}
