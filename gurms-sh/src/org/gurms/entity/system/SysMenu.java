package org.gurms.entity.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "sys_menu")
@org.hibernate.annotations.Entity(mutable = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysMenu implements Serializable {
	private String menuid;
	private String menuname;
	private String menuurl;
	private String menutype;
	private String remark;
	private String ajaxmode;
	private String checked;
	private String confirmed;
	private int    menuorder;

	private SysMenu parentmenu;

	private List<SysMenu> submenus = new ArrayList<SysMenu>();
	private List<SysRole> sysroles = new ArrayList<SysRole>();

	@Id
	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	@ManyToOne
	@JoinColumn(name = "parentmenuid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public SysMenu getParentmenu() {
		return parentmenu;
	}

	public void setParentmenu(SysMenu parentmenu) {
		this.parentmenu = parentmenu;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentmenu")
	@OrderBy(value = "menuorder")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysMenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<SysMenu> submenus) {
		this.submenus = submenus;
	}

	@ManyToMany(
		targetEntity = org.gurms.entity.system.SysRole.class,
		mappedBy = "sysmenus"
	)
	@Fetch(FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SysRole> getSysroles() {
		return sysroles;
	}

	public void setSysroles(List<SysRole> sysroles) {
		this.sysroles = sysroles;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMenuurl() {
		return menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}

	public String getAjaxmode() {
		return ajaxmode;
	}

	public void setAjaxmode(String ajaxmode) {
		this.ajaxmode = ajaxmode;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public String getMenutype() {
		return menutype;
	}

	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}

	public int getMenuorder() {
		return menuorder;
	}

	public void setMenuorder(int menuorder) {
		this.menuorder = menuorder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysMenu)) {
			return false;
		} else {
			SysMenu menu = (SysMenu) o;
			if (menu.getMenuid() == null) {
				return false;
			} else {
				return menu.getMenuid().equals(menuid);
			}
		}
	}

	public int hashCode() {
		if (menuid == null)
			return super.hashCode();
		return menuid.hashCode();
	}
}
