package org.gurms.common.excel;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class ExcelCreate {
	
	public void createExcel(OutputStream os) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("sheet1");
		sheet.setDefaultColumnWidth(12);
		buildContent(sheet);
		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void buildContent(Sheet sheet);

}
