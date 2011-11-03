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

	private int notype;
	private int prevalue;

	@Id
	public int getNotype() {
		return notype;
	}

	public void setNotype(int notype) {
		this.notype = notype;
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
			SysSerialno no = (SysSerialno) o;
			if (no.getNotype() == 0) {
				return false;
			} else {
				return no.getNotype() == notype;
			}
		}
	}

	public int hashCode() {
		if (notype == 0)
			return super.hashCode();
		return String.valueOf(notype).hashCode();
	}

}
