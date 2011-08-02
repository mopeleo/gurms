package org.gurms.web;

import java.util.ArrayList;
import java.util.List;

import org.gurms.entity.system.SysOrg;

public class TreeView {

	private String id;
	private String text;
	private String classes;
	private boolean expanded;
	private boolean hasChildren;
	private List children = new ArrayList();
	
	private TreeView(){}
	
	public static TreeView getSysOrgTree(SysOrg org){
		TreeView tree = new TreeView();
		tree.setId(org.getOrgid());
		tree.setText(org.getShortname());
		if(org.getSuborgs().size() > 0){
			tree.setClasses("folder");
			tree.setHasChildren(true);
		}else{
			tree.setClasses("file");
		}
		return tree;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}	
}
