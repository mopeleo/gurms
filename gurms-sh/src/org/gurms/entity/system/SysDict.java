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
@Table(name = "sys_dict")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysDict implements Serializable {
	
	private String dicttype;
	private String dictcode;
	private String dictvalue;
	private int dictorder;
	private String remark;

	public SysDict(){}
	
	public SysDict(SysDictPK pk){
		this.dicttype = pk.getDicttype();
		this.dictcode = pk.getDictcode();
	}
	
	public SysDict(String dicttype, String dictcode){
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDictorder() {
		return dictorder;
	}

	public void setDictorder(int dictorder) {
		this.dictorder = dictorder;
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysDict)) {
			return false;
		} else {
			SysDict pk = (SysDict) o;
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
