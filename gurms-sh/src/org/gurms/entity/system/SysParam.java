package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.gurms.entity.Logable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Logable
@Entity
@Table(name = "sys_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysParam implements Serializable{

	private Integer paramid;
	private String paramvalue;
	private String initvalue;
	private SysParamExt paramext;

	public SysParam(){};
	
	public SysParam(Integer id, String value){
		this.paramid = id;
		this.paramvalue = value;
	}
	
	@Id
	public Integer getParamid() {
		return paramid;
	}

	public void setParamid(Integer paramid) {
		this.paramid = paramid;
	}

	public String getParamvalue() {
		return paramvalue;
	}

	public String getInitvalue() {
		return initvalue;
	}

	public void setInitvalue(String initvalue) {
		this.initvalue = initvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
			return param.getParamid().intValue() == paramid.intValue();
		}
	}
	
	public int hashCode(){
		return paramid.hashCode();
	}
}
