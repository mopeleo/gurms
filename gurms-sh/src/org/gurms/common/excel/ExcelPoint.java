package org.gurms.common.excel;

public class ExcelPoint {

	private int realRow;
	private int displayRow;
	private int realCol;
	private String displayCol;

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

	public int getRealCol() {
		return realCol;
	}

	public void setRealCol(int realCol) {
		this.realCol = realCol;
	}

	public String getDisplayCol() {
		return displayCol;
	}

	public void setDisplayCol(String displayCol) {
		this.displayCol = displayCol;
	}
}
