package org.gurms.common.excel;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class ExcelUtil {

	public void createExcel(OutputStream os) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		sheet.setDefaultColumnWidth(12);
		buildContent(sheet);
		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void buildContent(HSSFSheet sheet);

	protected HSSFCell getCell(HSSFSheet sheet, int row, int col) {
		HSSFRow sheetRow = sheet.getRow(row);
		if (sheetRow == null)
			sheetRow = sheet.createRow(row);
		HSSFCell cell = sheetRow.getCell(col);
		if (cell == null)
			cell = sheetRow.createCell(col);
		return cell;
	}
}
