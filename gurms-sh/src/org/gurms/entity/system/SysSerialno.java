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
	private int prevalue;

	@Id
	public String getSerialtype() {
		return serialtype;
	}

	public void setSerialtype(String serialtype) {
		this.serialtype = serialtype;
	}

	public int getPrevalue() {
		return prevalue;
	}

	public void setPrevalue(int prevalue) {
		this.prevalue = prevalue;
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
