package org.gurms.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gurms.common.config.GlobalConfig;

/**
 * 分页参数封装类.
 */
public class PageRequest {

	public static final int MIN_PAGESIZE = GlobalConfig.MIN_PAGESIZE;
	public static final int MAX_PAGESIZE = GlobalConfig.MAX_PAGESIZE;

	protected int pageNo = 1;
	protected int pageSize = MIN_PAGESIZE;

	protected String orderBy = null;
	protected String orderDir = null;

	protected boolean countTotal = true;

	public PageRequest() {}

	public PageRequest(int pageNo, int pageSize) {
		setPageNo(pageNo);
		setPageSize(pageSize);
	}

	/**
	 * 获得当前页的页号, 序号从1开始, 默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号, 序号从1开始, 低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量, 默认为10.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,超出MIN_PAGESIZE与MAX_PAGESIZE范围时会自动调整.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < MIN_PAGESIZE) {
			this.pageSize = MIN_PAGESIZE;
		}
		if (pageSize > MAX_PAGESIZE) {
			this.pageSize = MAX_PAGESIZE;
		}
	}

	/**
	 * 获得排序字段, 无默认值. 多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段, 多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}
	
	/**
	 * 增加排序字段, 只能单个增加.
	 */
	public void addOrderBy(String orderBy, String orderDir){
		if(isOrderBySetted()){
			String dir = StringUtils.lowerCase(orderDir);
			if (!StringUtils.equals(Sort.DESC, dir) && !StringUtils.equals(Sort.ASC, dir)) {
				throw new IllegalArgumentException("排序方向" + orderDir + "不是合法值");
			}
			if(this.orderBy.indexOf(orderBy) != -1){
				this.orderBy += "," + orderBy;
				this.orderDir += "," + dir;
			}
		}else{
			setOrderBy(orderBy);
			setOrderDir(orderDir);
		}
	}

	/**
	 * 获得排序方向, 无默认值.
	 */
	public String getOrderDir() {
		return orderDir;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param orderDir 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrderDir(final String orderDir) {
		String lowcaseOrderDir = StringUtils.lowerCase(orderDir);

		//检查order字符串的合法值
		String[] orderDirs = StringUtils.split(lowcaseOrderDir, ',');
		for (String orderDirStr : orderDirs) {
			if (!StringUtils.equals(Sort.DESC, orderDirStr) && !StringUtils.equals(Sort.ASC, orderDirStr)) {
				throw new IllegalArgumentException("排序方向" + orderDirStr + "不是合法值");
			}
		}

		this.orderDir = lowcaseOrderDir;
	}

	/**
	 * 获得排序参数.
	 */
	public List<Sort> findSort() {
		if(StringUtils.isBlank(orderBy)){
			return new ArrayList<Sort>();
		}

		String[] orderBys = StringUtils.split(orderBy, ',');
		String[] orderDirs = null;
		
		//默认排序字段全部升序
		if(StringUtils.isBlank(orderDir)){
			orderDirs = new String[orderBys.length];
			for(int i = 0; i < orderBys.length; i++){
				orderDirs[i] = Sort.ASC;
			}
		}else{
			orderDirs = StringUtils.split(orderDir, ',');
			if (orderBys.length != orderDirs.length) {
				throw new IllegalArgumentException("分页多重排序参数中,排序字段与排序方向的个数不相等");
			}
		}

		List<Sort> orders = new ArrayList<Sort>();
		for (int i = 0; i < orderBys.length; i++) {
			orders.add(new Sort(orderBys[i], orderDirs[i]));
		}

		return orders;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy);
	}

	/**
	 * 是否默认计算总记录数.
	 */
	public boolean isCountTotal() {
		return countTotal;
	}

	/**
	 * 设置是否默认计算总记录数.
	 */
	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置, 序号从0开始.
	 */
	public int getOffset() {
		return ((pageNo - 1) * pageSize);
	}

	public static class Sort {
		public static final String ASC = "asc";
		public static final String DESC = "desc";

		private final String property;
		private final String dir;

		public Sort(String property, String dir) {
			this.property = property;
			this.dir = dir;
		}

		public String getProperty() {
			return property;
		}

		public String getDir() {
			return dir;
		}
		
		public boolean equals(Object o){
			if (o == null || !(o instanceof Sort)) {
				return false;
			} else {
				Sort s = (Sort) o;
				if (s.getProperty() == null) {
					return false;
				} else {
					return s.getProperty().equals(property);
				}
			}
		}
		
		public int hashCode(){
			if (property == null)
				return super.hashCode();
			return property.hashCode();
		}
	}
}
