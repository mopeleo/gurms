package org.gurms.common.excel;

public class ExcelPoint {

	private int displayRow;
	private int realRow;
	private int displayCol;
	private int realCol;
	private String colString;

	public int getDisplayRow() {
		return displayRow;
	}

	public void setDisplayRow(int displayRow) {
		this.displayRow = displayRow;
	}

	public int getRealRow() {
		return realRow;
	}

	public void setRealRow(int realRow) {
		this.realRow = realRow;
	}

	public int getDisplayCol() {
		return displayCol;
	}

	public void setDisplayCol(int displayCol) {
		this.displayCol = displayCol;
	}

	public int getRealCol() {
		return realCol;
	}

	public void setRealCol(int realCol) {
		this.realCol = realCol;
	}

	public String getColString() {
		return colString;
	}

	public void setColString(String colString) {
		this.colString = colString;
	}
}
