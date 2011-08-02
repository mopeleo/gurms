package org.gurms.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gurms.entity.system.SysParam;
import org.gurms.service.system.SysParamService;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysParamController extends BaseController {

	public static final String PARAM_LIST = "/sysparam/list";
	
	@Autowired
	private SysParamService sysParamService;
	
	@RequestMapping
	public void list(Model model){
		List<SysParam> paramList = sysParamService.getParamList();
		model.addAttribute(WebConstants.KEY_RESULT, paramList);
	}
	
	@RequestMapping
	public String save(HttpServletRequest request){
		String[] paramids = request.getParameterValues("paramid");
		String[] paramvalues = request.getParameterValues("paramvalue");
		if(paramids != null && paramids.length >0){
			List<SysParam> paramList = new ArrayList<SysParam>();
			for(int i = 0; i< paramids.length; i++){
				paramList.add(new SysParam(paramids[i], paramvalues[i]));
			}
			sysParamService.save(paramList);
		}
		return redirect(PARAM_LIST);
	}
}
