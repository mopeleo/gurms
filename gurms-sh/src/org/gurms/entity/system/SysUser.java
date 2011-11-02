package org.gurms.entity.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "sys_user")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysUser implements Serializable {

	//持久层属性
	private String userid;
	private String username;
	private String logintime;
	private String logindate;
	private String errortime;
	private String errordate;
	private String onlineflag;
	private String userstatus;
	private String loginpassword;
	private String uptpwdate;
	
	private int logincount;
	private int errorcount;

	private SysOrg sysorg;
	
	private List<SysRole> sysroles = new ArrayList<SysRole>();

	/////扩展属性，非持久层属性
	private String sysroleids;
	private String oldpassword;
	private String loginip;

	@Id
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@ManyToOne
	@JoinColumn(name = "orgid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public SysOrg getSysorg() {
		return sysorg;
	}

	public void setSysorg(SysOrg sysorg) {
		this.sysorg = sysorg;
	}

	@ManyToMany(targetEntity = org.gurms.entity.system.SysRole.class)
	@JoinTable(
		name = "sys_user_role",
		joinColumns = {@JoinColumn(name = "userid")},
		inverseJoinColumns = {@JoinColumn(name = "roleid")}
	)
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysRole> getSysroles() {
		return sysroles;
	}

	public void setSysroles(List<SysRole> sysroles) {
		this.sysroles = sysroles;
	}

	public String getOnlineflag() {
		return onlineflag;
	}

	public void setOnlineflag(String onlineflag) {
		this.onlineflag = onlineflag;
	}

	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	public int getErrorcount() {
		return errorcount;
	}

	public void setErrorcount(int errorcount) {
		this.errorcount = errorcount;
	}

	public int getLogincount() {
		return logincount;
	}

	public void setLogincount(int logincount) {
		this.logincount = logincount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginpassword() {
		return loginpassword;
	}

	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getLogindate() {
		return logindate;
	}

	public void setLogindate(String logindate) {
		this.logindate = logindate;
	}

	public String getUptpwdate() {
		return uptpwdate;
	}

	public void setUptpwdate(String uptpwdate) {
		this.uptpwdate = uptpwdate;
	}

	public void addRole(SysRole role){
		this.getSysroles().add(role);
	}
	
	public String getErrortime() {
		return errortime;
	}

	public void setErrortime(String errortime) {
		this.errortime = errortime;
	}

	public String getErrordate() {
		return errordate;
	}

	public void setErrordate(String errordate) {
		this.errordate = errordate;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysUser)) {
			return false;
		} else {
			SysUser user = (SysUser) o;
			if (user.getUserid() == null) {
				return false;
			} else {
				return user.getUserid().equals(userid);
			}
		}
	}

	public int hashCode() {
		if (userid == null)
			return super.hashCode();
		return userid.hashCode();
	}

	/////扩展属性，非持久层属性
	@Transient
	public String getSysroleids() {
		return sysroleids;
	}

	public void setSysroleids(String sysroleids) {
		this.sysroleids = sysroleids;
	}

	@Transient
	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	@Transient
	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}
}
