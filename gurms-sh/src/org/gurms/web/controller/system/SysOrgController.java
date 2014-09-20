package org.gurms.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.exception.GurmsException;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysOrg;
import org.gurms.service.system.SysOrgService;
import org.gurms.web.ServletUtil;
import org.gurms.web.TreeView;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Controller
public class SysOrgController extends BaseController {

	public static final String ORG_LIST = "/sysorg/list";
	
	@Autowired
	private SysOrgService orgService;
	
	@RequestMapping
	public void list(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<SysOrg> result = orgService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void detail(String orgid, Model model){
		if(StringUtils.isNotBlank(orgid)){
			SysOrg org = orgService.getById(orgid);
			if(GlobalParam.ORG_ROOTID.equals(org.getOrgid())){
			    throw new GurmsException("["+org.getShortname() + "]是根节点，不能修改");
			}
			model.addAttribute(WebConstants.KEY_RESULT, org);
		}
	}
	
	@RequestMapping
	public String save(SysOrg org){
		orgService.save(org);
		return redirect(ORG_LIST);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult<SysOrg> ajaxGet(String orgid){
		PageResult<SysOrg> page = new PageResult<SysOrg>();
		if(StringUtils.isNotBlank(orgid)){
			SysOrg org = orgService.getById(orgid);
			//解决spring mvc JSON 无限死循环 .1、手工设置循环引用属性为空，2.实体类使用@JsonIgnoreProperties(value={"sysusers","suborgs"})过滤属性
//			org.setSuborgs(null);
//			org.setSysusers(null);
//			if(org.getParentorg() != null){
//				org.getParentorg().setSuborgs(null);
//				org.getParentorg().setParentorg(null);
//				org.getParentorg().setSysusers(null);
//			}
			page.addResult(org);
		}else{
			page.setSuccess(false);
			page.setReturnmsg("机构ID不能为空");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxSave(SysOrg org){
		PageResult page = null;
		try{
			page = orgService.save(org);
		}catch(Exception e){
			page = processException(e, "保存机构信息出错");
		}
		return page;
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(String orgid){
		PageResult page = null;
		try{
			page = orgService.deleteById(orgid);
		}catch(Exception e){
			page = processException(e, "删除机构信息出错");
		}
		return page;
	}

	@RequestMapping
	@ResponseBody
	public List<TreeView> ajaxOrgTree(String root, String endnode){
		List<TreeView> list = new ArrayList<TreeView>();
		if(StringUtils.isBlank(root)){
			SysOrg org = orgService.getRoot();
			list.add(TreeView.getSysOrgTree(org, endnode));
		}else{
			SysOrg org = orgService.getById(root);
			for(SysOrg o : org.getSuborgs()){
				list.add(TreeView.getSysOrgTree(o, endnode));
			}
		}		
		return list;
	}
}
