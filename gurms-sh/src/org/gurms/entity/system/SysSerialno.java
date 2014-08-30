package org.gurms.entity.system;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "sys_serialno")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysSerialno implements Serializable {

	private String serialtype;
	private long prevalue;
	private String prefix;
	private String suffix;
	private String fixflag;
	private int fixlength;

	@Id
	public String getSerialtype() {
		return serialtype;
	}

	public void setSerialtype(String serialtype) {
		this.serialtype = serialtype;
	}

	public long getPrevalue() {
		return prevalue;
	}

	public void setPrevalue(long prevalue) {
		this.prevalue = prevalue;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFixflag() {
		return fixflag;
	}

	public void setFixflag(String fixflag) {
		this.fixflag = fixflag;
	}

	public int getFixlength() {
		return fixlength;
	}

	public void setFixlength(int fixlength) {
		this.fixlength = fixlength;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof SysSerialno)) {
			return false;
		} else {
			SysSerialno serial = (SysSerialno) o;
			if (serial.getSerialtype() == null) {
				return false;
			} else {
				return serial.getSerialtype().equals(serialtype);
			}
		}
	}

	public int hashCode() {
		if (serialtype == null)
			return super.hashCode();
		return serialtype.hashCode();
	}

}
