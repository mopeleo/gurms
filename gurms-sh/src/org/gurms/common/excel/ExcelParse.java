package org.gurms.common.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.gurms.common.exception.GurmsException;
import org.gurms.common.util.ReflectionUtil;
import org.gurms.entity.system.SysLogLogin;

public class ExcelParse {

	public Workbook getExcelWorkbook(String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		return getExcelWorkbook(is);
	}

	public Workbook getExcelWorkbook(InputStream is) throws IOException {
		return new HSSFWorkbook(is);
	}
	
	public Sheet getSheet(Workbook workbook, int sheetIndex){
		// 获取sheet页数
		int sheetNum = workbook.getNumberOfSheets();
		if (sheetIndex > sheetNum) {
			throw new GurmsException("错误的Sheet序号");
		}

		// 获取sheet
		return workbook.getSheetAt(sheetIndex);
	}

	public List<Map<String, Object>> parseSheet(Sheet sheet){
		return parseSheet(sheet, 0, 0);
	}
	
	/**
	 * 
	 * @param sheet
	 * @param startRow  excel中从0开始计数，例如：startRow=1 表示从第二行开始
	 * @param startCol  excel中从0开始计数，例如：startCol=1 表示从第二列开始
	 * @return
	 */
	public List<Map<String, Object>> parseSheet(Sheet sheet, int startRow, int startCol) {
		List<Map<String, Object>> sheetData = new ArrayList<Map<String, Object>>();
		// 获取开始行与结束行
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		if (lastRow > firstRow && startRow < lastRow && startRow >= firstRow) {
			for (int rowIndex = startRow; rowIndex < lastRow; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}

				int firstCol = row.getFirstCellNum();
				int lastCol = row.getLastCellNum();
				if(startCol < lastCol && startCol >= firstCol){
					Map<String, Object> cellMap = new HashMap<String, Object>();
					for (int colIndex = startCol; colIndex < lastCol; colIndex++) {
						Cell cell = row.getCell(colIndex);
						if (cell == null) {
							continue;
						}
						
						String cellKey = colNumber2String(colIndex) + (rowIndex + 1);
						Object cellValue = getCellValue(cell);
						cellMap.put(cellKey, cellValue);
					}
					sheetData.add(cellMap);
				}
			}
		}
		
		return sheetData;
	}


	public <T> List<T> parseSheet(Sheet sheet, Class<T> clazz, String[] props) {
		return parseSheet(sheet, 0, 0, clazz, props);
	}

	/**
	 * 
	 * @param <T>
	 * @param sheet
	 * @param startRow  excel中从0开始计数，例如：startRow=1 表示从第二行开始
	 * @param startCol  excel中从0开始计数，例如：startCol=1 表示从第二列开始
	 * @param clazz
	 * @param props
	 * @return
	 */
	public <T> List<T> parseSheet(Sheet sheet, int startRow, int startCol, Class<T> clazz, String[] props) {
		List<T> sheetData = new ArrayList<T>();
		// 获取开始行与结束行
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		if (lastRow > firstRow && startRow < lastRow && startRow >= firstRow) {
			for (int rowIndex = startRow; rowIndex < lastRow; rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}

				int firstCol = row.getFirstCellNum();
				int lastCol = row.getLastCellNum();
				try {
					if(startCol < lastCol && startCol >= firstCol){
						T bean = clazz.newInstance();
						for (int colIndex = startCol; colIndex < lastCol; colIndex++) {
							Cell cell = row.getCell(colIndex);
							if (cell == null) {
								continue;
							}
							
							String cellKey = props[colIndex - startCol];
							Object cellValue = getCellValue(cell);
							ReflectionUtil.setFieldValue(bean, cellKey, cellValue);
						}
						sheetData.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return sheetData;
	}

	public Object getCellValue(Cell cell) {
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

	public static void main(String[] args) throws IOException {
		ExcelParse parse = new ExcelParse();
		Workbook book = parse.getExcelWorkbook("d:/020014403038429J20113Z000.xls");
		Sheet sheet = parse.getSheet(book, 0);
		List<Map<String, Object>> list = parse.parseSheet(sheet);
		for(int i = 0; i < list.size(); i++){
			Map<String, Object> map = list.get(i);
			Iterator it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
				System.out.println(entry.getKey() + ":[" + entry.getValue()+"]");
			}
		}
		
		Workbook book2 = parse.getExcelWorkbook("d:/SysLogLogin.xls");
		String[] props = {"userid","logindate","logintime","loginpassword","loginip","success"};
		Sheet sheet2 = parse.getSheet(book2, 0);
		List<SysLogLogin> list2 = parse.parseSheet(sheet2,0,1, SysLogLogin.class, props);
		for(int i = 0; i < list2.size(); i++){
			SysLogLogin log = list2.get(i);
			System.out.print(log.getLogindate() + " | ");
			System.out.print(log.getLoginip() + " | ");
			System.out.print(log.getLoginpassword() + " | ");
			System.out.print(log.getLogintime() + " | ");
			System.out.print(log.getSuccess() + " | ");
			System.out.println(log.getUserid());
		}
	}

}
