package org.gurms.common.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.gurms.common.util.ReflectionUtil;
import org.gurms.entity.system.SysLogLogin;

public class ExcelParse {

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
						
						String cellKey = ExcelUtil.colNumber2String(colIndex) + (rowIndex + 1);
						Object cellValue = ExcelUtil.getCellValue(cell);
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
							Object cellValue = ExcelUtil.getCellValue(cell);
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

	public static void main(String[] args) throws IOException {
		ExcelParse parse = new ExcelParse();
		Workbook book = ExcelUtil.getWorkbook("d:/020014403038429J20113Z000.xls");
		Sheet sheet = ExcelUtil.getSheet(book, 0);
		List<Map<String, Object>> list = parse.parseSheet(sheet);
		for(int i = 0; i < list.size(); i++){
			Map<String, Object> map = list.get(i);
			Iterator it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
				System.out.println(entry.getKey() + ":[" + entry.getValue()+"]");
			}
		}
		
		Workbook book2 = ExcelUtil.getWorkbook("d:/SysLogLogin.xls");
		String[] props = {"userid","logindate","logintime","loginpassword","loginip","success"};
		Sheet sheet2 = ExcelUtil.getSheet(book2, 0);
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
