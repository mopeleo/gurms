package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "sys_param_ext")
@org.hibernate.annotations.Entity(dynamicInsert=true,dynamicUpdate=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysParamExt implements Serializable{

	private int paramid;
	private String paramtitle;
	private String paramtail;
	private String paramgroup;
	private String disptype;
	private String valuelist;
	private int paramorder;
	private int dictcode;
	private int paramlength;
	
	private SysParam sysparam;

	@Id
	@GeneratedValue(generator="fk")  
    @GenericGenerator(name="fk", strategy="foreign", 
    		parameters = {@Parameter(name = "property", value = "sysparam")}  
    )
	public int getParamid() {
		return paramid;
	}

	public void setParamid(int paramid) {
		this.paramid = paramid;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public SysParam getSysparam() {
		return sysparam;
	}

	public void setSysparam(SysParam sysparam) {
		this.sysparam = sysparam;
	}

	public String getParamtitle() {
		return paramtitle;
	}

	public void setParamtitle(String paramtitle) {
		this.paramtitle = paramtitle;
	}

	public String getParamtail() {
		return paramtail;
	}

	public void setParamtail(String paramtail) {
		this.paramtail = paramtail;
	}

	public String getParamgroup() {
		return paramgroup;
	}

	public void setParamgroup(String paramgroup) {
		this.paramgroup = paramgroup;
	}

	public int getParamlength() {
		return paramlength;
	}

	public void setParamlength(int paramlength) {
		this.paramlength = paramlength;
	}

	public String getDisptype() {
		return disptype;
	}

	public void setDisptype(String disptype) {
		this.disptype = disptype;
	}

	public int getDictcode() {
		return dictcode;
	}

	public void setDictcode(int dictcode) {
		this.dictcode = dictcode;
	}

	public String getValuelist() {
		return valuelist;
	}

	public void setValuelist(String valuelist) {
		this.valuelist = valuelist;
	}

	public int getParamorder() {
		return paramorder;
	}

	public void setParamorder(int paramorder) {
		this.paramorder = paramorder;
	}

	public boolean equals(Object o){
		if(o == null || !(o instanceof SysParamExt)){
			return false;
		}else{
			SysParamExt param = (SysParamExt)o;
			return param.getParamid() == paramid;
		}
	}
	
	public int hashCode(){
		return paramid;
	}
}
