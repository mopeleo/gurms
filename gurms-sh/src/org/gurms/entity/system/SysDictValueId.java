package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SysDictValueId implements Serializable {
	private Integer dictcode;
	private String dictitem;

	public SysDictValueId(){}
	
	public SysDictValueId(Integer dictcode, String dictitem){
		this.dictitem = dictitem;
		this.dictcode = dictcode;
	}
	

	public Integer getDictcode() {
		return dictcode;
	}

	public void setDictcode(Integer dictcode) {
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
		return dictcode == null || StringUtils.isBlank(dictitem);
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysDictValueId)) {
			return false;
		} else {
			SysDictValueId id = (SysDictValueId) o;
			return new EqualsBuilder().append(id.getDictitem(), dictitem)
					.append(id.getDictcode(), dictcode).isEquals();
		}
	}

	public int hashCode() {
		return new HashCodeBuilder().append(dictitem).append(dictcode).toHashCode();
	}

	@Override
	public String toString(){
		return "dictitem: " + dictitem + ", dictcode: " + dictcode;
	}	
}
