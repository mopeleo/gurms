package org.gurms.entity.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.gurms.common.config.GlobalParam;
import org.gurms.entity.Logable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Logable
@Entity
@Table(name = "sys_role")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysRole implements Serializable {
	private long roleid;
	private String remark;
	private String creator;
	private String enddate;
	private String roletype;
	private String rolename;
	private String startdate;
	private String rolestatus;
	private String enable;

	private List<SysMenu> sysmenus = new ArrayList<SysMenu>();
	private List<SysUser> sysusers = new ArrayList<SysUser>();

	// ///-------------------扩展属性，非持久层属性------------------
	private String[] sysmenuids;

	@Id
	@TableGenerator(
		name = "custom_id",
		table = "sys_serialno",
		pkColumnName = "serialtype",
		pkColumnValue = GlobalParam.SERIAL_SYS_ROLE,
		valueColumnName = "prevalue",
		initialValue = 1,
		allocationSize = 1
	)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="custom_id")

//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sys_seq")   
//	@SequenceGenerator(name="sys_seq", sequenceName="sys_seq", allocationSize=1) 
	public long getRoleid() {
		return roleid;
	}

	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}

	@ManyToMany(targetEntity = org.gurms.entity.system.SysMenu.class)
	@JoinTable(name = "sys_role_menu", joinColumns = { @JoinColumn(name = "roleid") }, inverseJoinColumns = { @JoinColumn(name = "menuid") })
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysMenu> getSysmenus() {
		return sysmenus;
	}

	public void setSysmenus(List<SysMenu> sysmenus) {
		this.sysmenus = sysmenus;
	}

	@ManyToMany(targetEntity = org.gurms.entity.system.SysUser.class, mappedBy = "sysroles")
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysUser> getSysusers() {
		return sysusers;
	}

	public void setSysusers(List<SysUser> sysusers) {
		this.sysusers = sysusers;
	}

	@Column(nullable = false, unique = true)
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRolestatus() {
		return rolestatus;
	}

	public void setRolestatus(String rolestatus) {
		this.rolestatus = rolestatus;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(updatable = false)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void addUser(SysUser user) {
		this.sysusers.add(user);
	}

	public void addMenu(SysMenu rm) {
		this.sysmenus.add(rm);
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysRole)) {
			return false;
		} else {
			SysRole role = (SysRole) o;
			return role.getRoleid() == roleid;
		}
	}

	public int hashCode() {
		return String.valueOf(roleid).hashCode();
	}

	// ///扩展属性，非持久层属性
	@Transient
	public String[] getSysmenuids() {
		return sysmenuids;
	}

	public void setSysmenuids(String[] sysmenuids) {
		this.sysmenuids = sysmenuids;
	}
}
