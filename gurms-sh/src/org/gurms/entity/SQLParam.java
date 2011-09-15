package org.gurms.entity;

import oracle.jdbc.OracleTypes;

public class SQLParam {
	private int index;  //必须大于0，索引位置从1开始
	private Type type;
	private Object value;
	private Direct dir;
	public enum Direct{IN, OUT};
	
	public enum Type{
		INT(OracleTypes.INTEGER), 
		FLOAT(OracleTypes.FLOAT), 
		VARCHAR(OracleTypes.VARCHAR), 
		CURSOR(OracleTypes.CURSOR);
		
		private int value;
		
		Type(int i){
			this.value = i;
		}
		
		public int getValue(){
			return value;
		}
	};
	
	public SQLParam(int index, Type type, Object value) {
		this.index = index;
		this.type = type;
		this.value = value;
	}

	public SQLParam(int index, Type type, Object value, Direct dir) {
		this.index = index;
		this.type = type;
		this.value = value;
		this.dir = dir;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Direct getDir() {
		return dir;
	}

	public void setDir(Direct dir) {
		this.dir = dir;
	}
}
