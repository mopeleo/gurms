package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@IdClass(SysDictPK.class)
@Table(name = "sys_dict_value")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysDictValue implements Serializable {
	
	private String dicttype;
	private String dictcode;
	private String dictvalue;
	private int dictorder;

	public SysDictValue(){}
	
	public SysDictValue(SysDictPK pk){
		this.dicttype = pk.getDicttype();
		this.dictcode = pk.getDictcode();
	}
	
	public SysDictValue(String dicttype, String dictcode){
		this.dicttype = dicttype;
		this.dictcode = dictcode;
	}
	
	@Id
	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	@Id
	public String getDictcode() {
		return dictcode;
	}

	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}

	public String getDictvalue() {
		return dictvalue;
	}

	public void setDictvalue(String dictvalue) {
		this.dictvalue = dictvalue;
	}

	public int getDictorder() {
		return dictorder;
	}

	public void setDictorder(int dictorder) {
		this.dictorder = dictorder;
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysDictValue)) {
			return false;
		} else {
			SysDictValue pk = (SysDictValue) o;
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

}
