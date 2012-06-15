package org.gurms.common.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.gurms.common.exception.GurmsException;

public abstract class ExcelUtil {

	public static Workbook getWorkbook(String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		return getWorkbook(is);
	}

	public static Workbook getWorkbook(InputStream is) throws IOException {
		return new HSSFWorkbook(is);
	}
	
	public static Sheet getSheet(Workbook workbook, int sheetIndex){
		// 获取sheet页数
		int sheetNum = workbook.getNumberOfSheets();
		if (sheetIndex > sheetNum) {
			throw new GurmsException("错误的Sheet序号");
		}

		// 获取sheet
		return workbook.getSheetAt(sheetIndex);
	}

	/**
	 * 
	 * @param sheet
	 * @param row  excel中从0开始计数，例如：row=1 表示第二行
	 * @param col  excel中从0开始计数，例如：col=1 表示第二列
	 * @return
	 */
	public static Cell getCell(Sheet sheet, int row, int col) {
		Row sheetRow = sheet.getRow(row);
		if (sheetRow == null)
			sheetRow = sheet.createRow(row);
		Cell cell = sheetRow.getCell(col);
		if (cell == null)
			cell = sheetRow.createCell(col);
		return cell;
	}
	
	public static Object getCellValue(Cell cell) {
		Object value = null;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是date类型则 ，获取该cell的date值
					value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
				} else { // 纯数字
					value = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN: // Boolean
				value = Boolean.toString(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				value = Byte.toString(cell.getErrorCellValue());
				break;
			default:
				value = "未知类型 ";
				break;
		}

		return value;
	}

	public static String[] splitCellString(String cellString) {
		char[] chars = cellString.toCharArray();
		int i = 0;
		for (; i < chars.length; i++) {
			if (Character.isDigit(chars[i])) {
				break;
			}
		}
		
		return new String[]{cellString.substring(i), cellString.substring(0, i)};
	}

	public static int colString2Number(String colString) {
		char[] chars = colString.toLowerCase().toCharArray();
		int cellNum = 0;
		int j = 0;
		for (int i = chars.length - 1; i >= 0; i--) {
			cellNum += (chars[i] - 'a' + 1) * Math.pow(26, j);
			j++;
		}

		return cellNum;
	}

	public static String colNumber2String(int colNum) {
		String colName = "";
		do {
			char c = (char) (colNum % 26 + 'A');
			colName = c + colName;
			colNum = colNum / 26 - 1;
		} while (colNum >= 0);
		return colName;
	}
}
