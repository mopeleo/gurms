package org.gurms.common.validate;

import java.util.List;

import org.gurms.common.util.ReflectionUtil;

public class GurmsValidator {

	public static String valid(String clzName, String[] props, Object o){
		List<GurmsValidRule> rules = GurmsValidConfig.getBeanRules(clzName, props);
		//若没有配置规则，则不验证
		if(rules == null || rules.size() == 0){
			return null;
		}
		
		StringBuffer sb = new StringBuffer(2048);
		if("1".equals(GurmsValidConfig.MSG_TYPE)){
			for(GurmsValidRule g : rules){
				g.setValue((String)ReflectionUtil.getFieldValue(o, g.getField()));
				boolean tmp = g.validField();
				if(!tmp){
					sb.append(g.getMsg()).append(",");
				}
			}
		}else{
			for(GurmsValidRule g : rules){
				g.setValue((String)ReflectionUtil.getFieldValue(o, g.getField()));
				boolean tmp = g.validField();
				if(!tmp){
					sb.append(g.getJsonMsg()).append(",");
				}
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
		StringBuffer script = new StringBuffer();
		script.append("<script>");

		try{
			List<GurmsValidRule> rules = GurmsValidConfig.getBeanRules(clzName, props);
			
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
