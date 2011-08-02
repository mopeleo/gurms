package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_param_ext")
@org.hibernate.annotations.Entity(mutable = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysParamExt implements Serializable{

	private String paramid;
	private String paramtitle;
	private String distype;
	private String dicttype;
	private String valuelist;
	private String paramorder;
	private String remark;

	@Id
	public String getParamid() {
		return paramid;
	}

	public void setParamid(String paramid) {
		this.paramid = paramid;
	}

	public String getParamtitle() {
		return paramtitle;
	}

	public void setParamtitle(String paramtitle) {
		this.paramtitle = paramtitle;
	}

	public String getDistype() {
		return distype;
	}

	public void setDistype(String distype) {
		this.distype = distype;
	}

	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getValuelist() {
		return valuelist;
	}

	public void setValuelist(String valuelist) {
		this.valuelist = valuelist;
	}

	public String getParamorder() {
		return paramorder;
	}

	public void setParamorder(String paramorder) {
		this.paramorder = paramorder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean equals(Object o){
		if(o == null || !(o instanceof SysParamExt)){
			return false;
		}else{
			SysParamExt param = (SysParamExt)o;
			if(param.getParamid() == null){
				return false;
			}else{
				return param.getParamid().equals(paramid);
			}
		}
	}
	
	public int hashCode(){
		if(paramid == null)
			return super.hashCode();
		return paramid.hashCode();
	}
}
