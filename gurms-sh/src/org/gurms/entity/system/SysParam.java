package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_param")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysParam implements Serializable{

	private String paramid;
	private String paramvalue;
	private SysParamExt paramext;

	public SysParam(){};
	
	public SysParam(String id, String value){
		this.paramid = id;
		this.paramvalue = value;
	}
	
	@Id
	public String getParamid() {
		return paramid;
	}

	public void setParamid(String paramid) {
		this.paramid = paramid;
	}

	public String getParamvalue() {
		return paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public SysParamExt getParamext() {
		return paramext;
	}

	public void setParamext(SysParamExt paramext) {
		this.paramext = paramext;
	}
	
	public boolean equals(Object o){
		if(o == null || !(o instanceof SysParam)){
			return false;
		}else{
			SysParam param = (SysParam)o;
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
