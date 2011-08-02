package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Transient;

public class SysDictPK implements Serializable {
	private String dicttype;
	private String dictcode;

	public SysDictPK(){}
	
	public SysDictPK(String dicttype, String dictcode){
		this.dicttype = dicttype;
		this.dictcode = dictcode;
	}
	
	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getDictcode() {
		return dictcode;
	}

	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}

	@Transient
	public boolean isNull(){
		return dicttype == null || dictcode == null;
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysDictPK)) {
			return false;
		} else {
			SysDictPK pk = (SysDictPK) o;
			if (pk.dicttype == null || pk.dictcode == null) {
				return false;
			} else {
				return pk.dicttype.equals(dicttype)
						&& pk.dictcode.equals(dictcode);
			}
		}
	}

	public int hashCode() {
		if (dicttype == null || dictcode == null)
			return super.hashCode();
		return dicttype.hashCode() + dictcode.hashCode();
	}

	@Override
	public String toString(){
		return "dicttype: " + dicttype + ", dictcode: " + dictcode;
	}
}
