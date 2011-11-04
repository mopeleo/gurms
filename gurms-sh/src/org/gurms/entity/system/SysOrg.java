package org.gurms.entity.system;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.gurms.entity.Logable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_org")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true) 
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysOrg extends Logable {
	private String orgid;
	private String shortname;
	private String fullname;
	private String linkman;
	private String linktel;
	private String faxno;
	private String address;
	private String postcode;
	private String email;
	private String orgtype;
	private String remark;
	private int    orgorder;

	private SysOrg parentorg;
	
	private List<SysUser> sysusers = new ArrayList<SysUser>();
	private List<SysOrg>  suborgs  = new ArrayList<SysOrg>();
	
	@Id
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	@ManyToOne
	@JoinColumn(name = "parentorgid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public SysOrg getParentorg() {
		return parentorg;
	}

	public void setParentorg(SysOrg parentorg) {
		this.parentorg = parentorg;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentorg")
	@OrderBy(value = "orgorder")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysOrg> getSuborgs() {
		return suborgs;
	}

	public void setSuborgs(List<SysOrg> suborgs) {
		this.suborgs = suborgs;
	}

	@OneToMany(
		cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
		fetch = FetchType.LAZY, 
		mappedBy = "sysorg"
	)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysUser> getSysusers() {
		return sysusers;
	}

	public void setSysusers(List<SysUser> sysusers) {
		this.sysusers = sysusers;
	}

	public int getOrgorder() {
		return orgorder;
	}

	public void setOrgorder(int orgorder) {
		this.orgorder = orgorder;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinktel() {
		return linktel;
	}

	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}

	public String getFaxno() {
		return faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public void addOrg(SysOrg org){
		this.getSuborgs().add(org);
	}
	
	public boolean equals(Object o){
		if(o == null || !(o instanceof SysOrg)){
			return false;
		}else{
			SysOrg org = (SysOrg)o;
			if(org.getOrgid() == null){
				return false;
			}else{
				return org.getOrgid().equals(orgid);
			}
		}
	}
	
	public int hashCode(){
		if(orgid == null)
			return super.hashCode();
		return orgid.hashCode();
	}
}
