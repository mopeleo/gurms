package org.gurms.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.util.ValidCodeGenerator;
import org.gurms.common.validate.GurmsValid.FilterType;
import org.gurms.common.validate.GurmsValidator;
import org.gurms.entity.system.SysDictValue;
import org.gurms.service.system.SysDictService;
import org.gurms.web.MediaTypes;
import org.gurms.web.SelectDivData;
import org.gurms.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController extends BaseController {

    @Autowired
    private SysDictService dictService;
	// 生成验证码
	@RequestMapping
	public void validcode(HttpSession session, HttpServletResponse response) {
		try {
			response.setContentType(MediaTypes.TYPE_IMG);
			String code = ValidCodeGenerator.generate(response.getOutputStream());
			session.setAttribute(WebConstants.S_KEY_VALIDCODE, code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 生成前段效验的JS代码
	@RequestMapping
	public void validscript(HttpServletRequest request, HttpServletResponse response){
		response.setContentType(MediaTypes.TEXT_PLAIN);
//		String js = "<script>function test(){alert("+className+");}test();</script>";
		String className = request.getParameter("className");
		String formId = request.getParameter("formId");
		String prop = request.getParameter("props");
		String filter = request.getParameter("filter");
		FilterType filterType = FilterType.INCLUDE;
		if(StringUtils.isNotBlank(filter)){
			filterType = Enum.valueOf(FilterType.class, filter.toUpperCase());
		}
		String[] props = null;
		if(StringUtils.isNotBlank(prop)){
			props = StringUtils.split(prop, GlobalParam.STRING_SEPARATOR);
		}
		String js = GurmsValidator.script(className, formId, props, filterType);
		try {
			response.getOutputStream().write(js.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//生成字典下拉数据
	//var datas=[{display:'aaaaaa',relvalue:'1'},{display:'bbbbbbbb',relvalue:'2'},{display:'ccccccccccccc',relvalue:'3'}];
    @RequestMapping
    @ResponseBody
	public List<SelectDivData> genDictData(int dicttype, String prefix, int returnnum){
        List<SelectDivData> list = new ArrayList<SelectDivData>();
        List<SysDictValue> search = dictService.getDict(dicttype, prefix);
        if(returnnum < 1){
            returnnum = 10;
        }
        for(int i = 0 ; i < search.size(); i++){
            if(i >= returnnum){
                break;
            }
            SelectDivData  sdd = new SelectDivData();
            sdd.setDisplay(search.get(i).getItemname());
            sdd.setRelvalue(search.get(i).getDictitem());
            list.add(sdd);
        }
        return list;
	}
}
