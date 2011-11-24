package org.gurms.common.pdmparse;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String id;
	private String name;
	private String code;
	private String comment;
	
	private List<Column> columns = new ArrayList<Column>();
	private List<Column> keys = new ArrayList<Column>();
	
	public void addColumn(Column col){
		columns.add(col);
	}
	
	public void addKey(Column key){
		keys.add(key);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<Column> getKeys() {
		return keys;
	}
	public void setKeys(List<Column> keys) {
		this.keys = keys;
	}
	
}
