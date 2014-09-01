package org.gurms.web.controller.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysDictValue;
import org.gurms.entity.system.SysDictValueId;
import org.gurms.service.system.SysDictService;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysDictController extends BaseController{

	public static final String DICT_LIST = "/sysdict/list";

	@Autowired
	private SysDictService dictService;
	
	@RequestMapping
	public void list(HttpServletRequest request, PageRequest page, Model model){
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<SysDictValue> result = dictService.queryDict(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}
	
	@RequestMapping
	public void detail(SysDictValueId pk, Model model){
		if(pk != null && !pk.isNull()){
			SysDictValue dict = dictService.getDict(pk);
			model.addAttribute(WebConstants.KEY_RESULT, dict);
		}
	}
	
	@RequestMapping
	public String save(HttpServletRequest request, SysDictValue dict){
		dictService.saveDict(dict);
		
		//更新缓存
		Map<String, List<SysDictValue>> dictMap = (Map<String, List<SysDictValue>>)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_DICT);
		List<SysDictValue> list = dictMap.get(dict.getDictitem());
		if(list.contains(dict)){
			list.set(list.indexOf(dict), dict);
		}else{
			list.add(dict);
		}
		return redirect(DICT_LIST);
	}
	
	@RequestMapping
	public String delete(HttpServletRequest request, SysDictValueId pk){
		dictService.deleteDict(pk);
		
		//更新缓存
		SysDictValue dict = new SysDictValue(pk);
		Map dictMap = (Map)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_DICT);
		List<SysDictValue> list = (List<SysDictValue>)dictMap.get(dict.getDictitem());
		list.remove(dict);
		if(list.size() == 0){
			dictMap.remove(dict.getDictitem());
		}
		return redirect(DICT_LIST);
	}
	
	@RequestMapping
	@ResponseBody
	public PageResult ajaxDelete(HttpServletRequest request, SysDictValueId pk) {
		PageResult page = null;
		try {
			dictService.deleteDict(pk);
			//更新缓存
			SysDictValue dict = new SysDictValue(pk);
			Map dictMap = (Map)ServletUtil.getContext(request).getAttribute(WebConstants.C_KEY_DICT);
			List<SysDictValue> list = (List<SysDictValue>)dictMap.get(dict.getDictitem());
			list.remove(dict);
			if(list.size() == 0){
				dictMap.remove(dict.getDictitem());
			}
			page = new PageResult();
		} catch (Exception e) {
			page = processException(e, "删除字典出错");
		}
		return page;
	}

}

