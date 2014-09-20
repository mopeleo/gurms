package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.exception.GurmsException;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_serialno")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysSerialno implements Serializable {

	private String serialtype;
	private long prevalue;
	private String prefix;
	private String suffix;
	private String fixflag;
	private int fixlength;

	@Id
	public String getSerialtype() {
		return serialtype;
	}

	public void setSerialtype(String serialtype) {
		this.serialtype = serialtype;
	}

	public long getPrevalue() {
		return prevalue;
	}

	public void setPrevalue(long prevalue) {
		this.prevalue = prevalue;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFixflag() {
		return fixflag;
	}

	public void setFixflag(String fixflag) {
		this.fixflag = fixflag;
	}

	public int getFixlength() {
		return fixlength;
	}

	public void setFixlength(int fixlength) {
		this.fixlength = fixlength;
	}

	public String generateId(){
		String fillStr = getFillString();
		return getPrefix() + fillStr + getPrevalue() + getSuffix();
	}
	
	@Transient
	private String getFillString(){
		String fillStr = "";
		if(getFixflag().equals(GlobalParam.DICT_YESORNO_NO)){
			return fillStr;
		}
		int serialLen = getFixlength();
		if(StringUtils.isNotBlank(getPrefix())){
			serialLen = serialLen - getPrefix().length();
		}
		if(StringUtils.isNotBlank(getSuffix())){
			serialLen = serialLen - getSuffix().length();
		}
		int fillLen = serialLen - String.valueOf(getPrevalue()).length();
		if(fillLen < 0){
			throw new GurmsException("主键定长长度设置过短");
		}else{
			for(int i = 0; i < fillLen; i++){
				fillStr += "0";
			}
		}
		return fillStr;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysSerialno)) {
			return false;
		} else {
			SysSerialno serial = (SysSerialno) o;
			if (serial.getSerialtype() == null) {
				return false;
			} else {
				return serial.getSerialtype().equals(serialtype);
			}
		}
	}

	public int hashCode() {
		if (serialtype == null)
			return super.hashCode();
		return serialtype.hashCode();
	}

}
