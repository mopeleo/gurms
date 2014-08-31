package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SysDictValuePk implements Serializable {
	private int dictcode;
	private String dictitem;

	public SysDictValuePk(){}
	
	public SysDictValuePk(int dictcode, String dictitem){
		this.dictitem = dictitem;
		this.dictcode = dictcode;
	}
	

	public int getDictcode() {
		return dictcode;
	}

	public void setDictcode(int dictcode) {
		this.dictcode = dictcode;
	}

	public String getDictitem() {
		return dictitem;
	}

	public void setDictitem(String dictitem) {
		this.dictitem = dictitem;
	}

	@Transient
	public boolean isNull(){
		return dictcode == 0 || dictitem == null;
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysDictValuePk)) {
			return false;
		} else {
			SysDictValuePk pk = (SysDictValuePk) o;
			return new EqualsBuilder().append(pk.getDictitem(), dictitem)
					.append(pk.getDictcode(), dictcode).isEquals();
		}
	}

	public int hashCode() {
		return new HashCodeBuilder().append(dictitem).append(dictcode).hashCode();
	}

	@Override
	public String toString(){
		return "dictitem: " + dictitem + ", dictcode: " + dictcode;
	}
}
