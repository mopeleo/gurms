package org.gurms.web.controller.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysDict;
import org.gurms.entity.system.SysDictPK;
import org.gurms.service.system.SysDictService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysDictController extends BaseController{

	public static final String DICT_LIST = "/sysdict/list";

	@Autowired
	private SysDictService dictService;
	
	@RequestMapping
	public void list(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<SysDict> result = dictService.query(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void detail(SysDictPK pk, Model model){
		if(pk != null && !pk.isNull()){
			SysDict dict = dictService.get(pk);
			model.addAttribute(WebConstants.KEY_RESULT, dict);
		}
	}
	
	@RequestMapping
	public String save(HttpServletRequest request, SysDict dict){
		dictService.save(dict);
		
		//更新缓存
		Map<String, List<SysDict>> dictMap = (Map<String, List<SysDict>>)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_DICT);
		List<SysDict> list = dictMap.get(dict.getDicttype());
		if(list.contains(dict)){
			list.set(list.indexOf(dict), dict);
		}else{
			list.add(dict);
		}
		return redirect(DICT_LIST);
	}
	
	@RequestMapping
	public String delete(HttpServletRequest request, SysDictPK pk){
		dictService.delete(pk);
		
		//更新缓存
		SysDict dict = new SysDict(pk);
		Map dictMap = (Map)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_DICT);
		List<SysDict> list = (List<SysDict>)dictMap.get(dict.getDicttype());
		list.remove(dict);
		if(list.size() == 0){
			dictMap.remove(dict.getDicttype());
		}
		return redirect(DICT_LIST);
	}
}

