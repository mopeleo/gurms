package org.gurms.common.validate;

import java.io.Serializable;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.gurms.common.exception.GurmsException;


public class GurmsValidRule implements Serializable{

	private String sep = "|";
	
	private String field;
	private String rule;
	private String msg;
	private String is;
	private String minimum;
	private String maximum;
	private String pattern;
	private String within;
	private String match;
	private boolean onlyInteger;
	
	private String value;      //属性对应的值
	private String script;     //客户端效验JS
	
	private enum Rule {
		Presence,Numericality,Format,Email,Length,
		Inclusion,Exclusion,Confirmation,Acceptance;
	}

	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getIs() {
		return is;
	}
	public void setIs(String is) {
		this.is = is;
	}
	public String getMinimum() {
		return minimum;
	}
	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}
	public String getMaximum() {
		return maximum;
	}
	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getWithin() {
		return within;
	}
	public void setWithin(String within) {
		this.within = within;
	}
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public boolean isOnlyInteger() {
		return onlyInteger;
	}
	public void setOnlyInteger(boolean onlyInteger) {
		this.onlyInteger = onlyInteger;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getJsonMsg(){
		return "{" + getField() + ":'" + getMsg() + "'}";
	}

	//new LiveValidation("userid").add(Validate.Presence,{failureMessage:'用户名不能为空'});
	public String getScript(){
		if(this.script != null){
			return this.script;
		}
		if(StringUtils.isBlank(getField())){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("new LiveValidation('").append(getField()).append("')");

		String r = addFunc(this);
		this.script = sb.append(r).toString();
		return this.script;
	}
	
	public GurmsValidRule addScript(GurmsValidRule g){
		String comp = getScript();
		if(this.equals(g)){
			comp = comp.substring(0, comp.length()-1) + addFunc(g);
		}else{
			comp += g.getScript();
			this.field = g.getField();
		}
		this.script = comp;
		return this;
	}
	
	public boolean validField(){
		if(StringUtils.isEmpty(getRule())){
			throw new GurmsException("请定义正确的效验规则");
		}

		Rule r = Enum.valueOf(Rule.class, getRule());
		String val = getValue();
		Pattern pattern = null;
		boolean result = true;
		switch(r){
			case Presence:
				return StringUtils.isNotBlank(val);
			case Numericality:
				if(NumberUtils.isNumber(val)){
					if(isOnlyInteger()){
						result = result && NumberUtils.isDigits(val);
					}
					if(StringUtils.isNotEmpty(getIs())){
						result = result && String.valueOf(getIs()).equals(val);
					}
					if(StringUtils.isNotEmpty(getMinimum())){
						result = result && (NumberUtils.compare(NumberUtils.toFloat(val), NumberUtils.toFloat(getMinimum())) >= 0);
					}
					if(StringUtils.isNotEmpty(getMaximum())){
						result = result && (NumberUtils.compare(NumberUtils.toFloat(val), NumberUtils.toFloat(getMinimum())) <= 0);
					}
				}else{
					result = false;
				}
				return result;
			case Format:
				pattern = Pattern.compile(getPattern());
				return pattern.matcher(val).matches();
			case Email:
				pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",Pattern.CASE_INSENSITIVE);
				return pattern.matcher(val).matches();
			case Length:
				if(StringUtils.isNotEmpty(val)){
					if(StringUtils.isNotEmpty(getIs())){
						result = result && (Integer.parseInt(getIs()) == val.length());
					}
					if(StringUtils.isNotEmpty(getMinimum())){
						result = result && (Integer.parseInt(getMinimum()) <= val.length());
					}
					if(StringUtils.isNotEmpty(getMaximum())){
						result = result && (Integer.parseInt(getMaximum()) >= val.length());
					}
				}else{
					result = false;
				}
				return result;
			case Inclusion:
				String[] includ = getWithin().split(sep);
				return ArrayUtils.contains(includ, val);
			case Exclusion:
				String[] exclud = getWithin().split(sep);
				return !ArrayUtils.contains(exclud, val);
//			case Confirmation:
//				return StringUtils.isNumeric(val);
//			case Acceptance:
//				return StringUtils.isNumeric(val);
			default:
				return false;			
		}
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null || !(o instanceof GurmsValidRule)){
			return false;
		}else{
			GurmsValidRule gvr = (GurmsValidRule)o;
			if(gvr.getField() == null){
				return false;
			}else{
				return gvr.getField().equals(field);
			}
		}
	}
	
	@Override
	public int hashCode(){
		if(field == null)
			return super.hashCode();
		return field.hashCode();
	}
	
	//.add(Validate.Presence,{failureMessage:'msg'});
	private String addFunc(GurmsValidRule gvr){
		Rule r = Enum.valueOf(Rule.class, gvr.getRule());
		switch(r){
			case Presence:
				return ".add(Validate.Presence,{failureMessage:'" + gvr.getMsg() + "'});";
			case Numericality:
				StringBuffer num = new StringBuffer();
				num.append(".add(Validate.Numericality,{");
				if(isOnlyInteger()){
					num.append("onlyInteger:true,notAnIntegerMessage:'"+gvr.getMsg()+"',");
				}
				if(StringUtils.isNotEmpty(gvr.getIs())){
					num.append("is:" + gvr.getIs() + ",wrongNumberMessage:'"+gvr.getMsg()+"',");
				}
				if(StringUtils.isNotEmpty(gvr.getMinimum())){
					num.append("minimum:" + gvr.getMinimum() + ",tooLowMessage:'" + gvr.getMsg() + "',");
				}
				if(StringUtils.isNotEmpty(gvr.getMaximum())){
					num.append("maximum:" + gvr.getMaximum() + ",tooHighMessage:'" + gvr.getMsg() + "',");
				}
				if(StringUtils.isEmpty(gvr.getMinimum())&&StringUtils.isEmpty(gvr.getMaximum())){
					num.append("notANumberMessage:'" + gvr.getMsg() + "',");
				}
				num.deleteCharAt(num.length()-1).append("});");
				return num.toString();
			case Format:
				return ".add(Validate.Format,{pattern:" + gvr.getPattern() + ",failureMessage:'" + gvr.getMsg() + "'});";
			case Email:
				return ".add(Validate.Email,{failureMessage:'" + gvr.getMsg() + "'});";
			case Length:
				StringBuffer len = new StringBuffer();
				len.append(".add(Validate.Length,{");
				if(StringUtils.isNotEmpty(gvr.getIs())){
					len.append("is:" + gvr.getIs() + ",");
				}
				if(StringUtils.isNotEmpty(gvr.getMinimum())){
					len.append("minimum:" + gvr.getMinimum() + ",tooShortMessage:'" + gvr.getMsg() + "',");
				}
				if(StringUtils.isNotEmpty(gvr.getMaximum())){
					len.append("maximum:" + gvr.getMaximum() + ",tooLongMessage:'" + gvr.getMsg() + "',");
				}
				if(StringUtils.isEmpty(gvr.getMinimum())&&StringUtils.isEmpty(gvr.getMaximum())){
					len.append("failureMessage:'" + gvr.getMsg() + "',");
				}
				len.deleteCharAt(len.length()-1).append("});");
				return len.toString();
			case Inclusion:
				String[] includ = gvr.getWithin().split(sep);
				StringBuffer in = new StringBuffer();
				in.append(".add(Validate.Inclusion,{within:[");
				for(String tmp : includ){
					in.append("'").append(tmp).append("',");
				}
				in.deleteCharAt(in.length()-1).append("],failureMessage:'" + gvr.getMsg() + "'});");
				return in.toString();
			case Exclusion:
				String[] exclud = gvr.getWithin().split(sep);
				StringBuffer ex = new StringBuffer();
				ex.append(".add(Validate.Exclusion,{within:[");
				for(String tmp : exclud){
					ex.append("'").append(tmp).append("',");
				}
				ex.deleteCharAt(ex.length()-1).append("],failureMessage:'" + gvr.getMsg() + "'});");
				return ex.toString();
			case Confirmation:
				return ".add(Validate.Confirmation,{match:'" + gvr.getMatch() + "',failureMessage:'" + gvr.getMsg() + "'});";
			case Acceptance:
				return ".add(Validate.Acceptance,{failureMessage:'" + gvr.getMsg() + "'});";
			default:
				return "";			
		}
	}
	
}
