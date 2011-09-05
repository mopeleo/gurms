package org.gurms.entity;

import java.io.Serializable;

import javax.persistence.Transient;

public abstract class Logable implements Serializable {
	private String operator;

	@Transient
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
