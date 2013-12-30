package org.gurms.common.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.util.CommonUtil;
import org.gurms.common.validate.GurmsValid.FilterType;

public class GurmsValidConfig {
	
    private static final ResourceBundle rb = ResourceBundle.getBundle("gurms-validate");
    private static final Set<String> keySet = rb.keySet(); 

    //验证信息返回的格式
    public static final String MSG_TYPE = rb.getString("msg_type");
    
    private GurmsValidConfig(){}
    
    /**
     * 根据类名，获得全部属性的效验规则
     * @param className 类名
     * @return ArrayList<GurmsValidRule>
     */
    public static List<List<GurmsValidRule>> getBeanRules(String className, boolean serverValid){
    	List<List<GurmsValidRule>> rules = new ArrayList<List<GurmsValidRule>>();
    	String tmpKey = className + ".";
    	for(String key : keySet){
    		if(key.startsWith(tmpKey)){
    			String rule = rb.getString(key);
    			String field = key.substring(tmpKey.length());
				List<GurmsValidRule> gvr = json2Rule(rule, field, serverValid);
    			rules.add(gvr);
    		}
    	}
    	return rules;
    }
    
    /**
     * 根据类名，获得全部属性的效验规则
     * @param className 类名
     * @param props 属性集合
     * @return ArrayList<GurmsValidRule>
     */
    public static List<List<GurmsValidRule>> getBeanRules(String className, String[] props, FilterType filter, boolean serverValid){
		List<List<GurmsValidRule>> rules = null;
		if(props == null || props.length == 0){
			rules = GurmsValidConfig.getBeanRules(className, serverValid);			
		}else{
			rules = new ArrayList<List<GurmsValidRule>>();
			if(filter == FilterType.INCLUDE){
				for(String field : props){
					String validField = className + "." + field;
					String rule = rb.getString(validField);
					List<GurmsValidRule> gvr = json2Rule(rule, field, serverValid);
					rules.add(gvr);
				}
			}else{
		    	String tmpKey = className + ".";
		    	for(String key : keySet){
		    		if(key.startsWith(tmpKey)){
		    			if(!CommonUtil.existSuffix(key, props)){
		    				String rule = rb.getString(key);
		    				String field = key.substring(tmpKey.length());
							List<GurmsValidRule> gvr = json2Rule(rule, field, serverValid);
			    			rules.add(gvr);
		    			}
		    		}
		    	}
			}
		}
		return rules;
    }
    
    /**
     * 从json字符串转换为GurmsValidRule对象
     * @param 完整的key
     * @return GurmsValidRule
     */
    private static List<GurmsValidRule> json2Rule(String rule, String field, boolean serverValid){
    	JSONArray o = JSONArray.fromObject(rule);
    	List<GurmsValidRule> list = JSONArray.toList(o, GurmsValidRule.class);
    	for(GurmsValidRule gvr : list){
			//若是服务器端验证或者没有指定执行页面控件的ID，则默认为属性名
			if(serverValid || StringUtils.isBlank(gvr.getField())){
	    		gvr.setField(field);
	    	}
    	}
		return list;    	
    }    
}
