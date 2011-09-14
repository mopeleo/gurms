package org.gurms.common.validate;

import java.util.List;

import org.gurms.common.util.ReflectionUtil;
import org.gurms.common.validate.GurmsValid.FilterType;

public class GurmsValidator {

	public static String serverValid(String clzName, String[] props, FilterType filter, Object o){
		List<GurmsValidRule> rules = GurmsValidConfig.getBeanRules(clzName, props, filter, true);
		//若没有配置规则，则不验证
		if(rules == null || rules.size() == 0){
			return null;
		}
		
		StringBuffer sb = new StringBuffer(2048);
		for(GurmsValidRule g : rules){
			g.setValue(String.valueOf(ReflectionUtil.recGetPropertyValue(o, g.getField())));
			if(!g.validField()){
				sb.append(g.getMsg()).append(",");
//				if("1".equals(GurmsValidConfig.MSG_TYPE)){
//					sb.append(g.getMsg()).append(",");
//				}else{
//					sb.append(g.getJsonMsg()).append(",");
//				}
			}
		}
		
		if(sb.length() > 1){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	public static String script(String clzName){
		return script(clzName, "_validForm");
	}
	
	public static String script(String clzName, String formId){
		return script(clzName, formId, null);
	}
	
	public static String script(String clzName, String formId, String[] props){
		return script(clzName, formId, props, FilterType.INCLUDE);
	}
	
	public static String script(String clzName, String formId, String[] props, FilterType filter){
		StringBuffer script = new StringBuffer();
		script.append("<script>");

		try{
			List<GurmsValidRule> rules = GurmsValidConfig.getBeanRules(clzName, props, filter, false);
			
			GurmsValidRule temp = new GurmsValidRule();
			for(GurmsValidRule g : rules){
				temp.addScript(g);
			}
			script.append(temp.getScript());
			script.append("function _validForm(){");
			script.append(temp.getScript());
			script.append("var validform=document.getElementById('").append(formId).append("');");
			script.append("return LiveValidationForm.getInstance(validform).validForm();}");
		}catch(Exception e){
			script.append("function _getScriptException(){alert(\"");
			script.append(e.getMessage());
			script.append("\");} _getScriptException();");
		}
		script.append("</script>");
		return script.toString();
	}
}
