package org.gurms.web.controller.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.gurms.common.config.GlobalMessage;
import org.gurms.common.config.GlobalParam;
import org.gurms.common.excel.ExcelCreate;
import org.gurms.common.excel.ExcelUtil;
import org.gurms.common.pdf.PDFUtil;
import org.gurms.entity.PageRequest;
import org.gurms.entity.PageResult;
import org.gurms.entity.system.SysLogLogin;
import org.gurms.entity.system.SysLogOperate;
import org.gurms.service.system.SysLogService;
import org.gurms.web.MediaTypes;
import org.gurms.web.ServletUtil;
import org.gurms.web.WebConstants;
import org.gurms.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

@Controller
public class SysLogController extends BaseController {

	@Autowired
	private SysLogService sysLogService;

	@RequestMapping
	public void loginlist(HttpServletRequest request, PageRequest page, Model model) {
		setIndividuation(request, page);
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<SysLogLogin> result = sysLogService.queryLogin(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}

	@RequestMapping
	public void operatelist(HttpServletRequest request, PageRequest page, Model model) {
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		PageResult<SysLogOperate> result = sysLogService.queryOperate(requestMap, page);
		model.addAttribute(WebConstants.KEY_RESULT, result);
		model.addAllAttributes(requestMap);
	}

	@RequestMapping
	public void pdf(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		final List<SysLogLogin> result = sysLogService.queryLogin(requestMap);
		response.setContentType(MediaTypes.TYPE_PDF);
		ServletUtil.setFileDownloadHeader(response, "SysLogLogin.pdf");
		
		try {
			new PDFUtil(){
				public void buildContent(Document document) throws DocumentException {
					String title = GlobalMessage.getMessage("5000");
					String[] titles = title.split(GlobalParam.STRING_SEPARATOR);
					
					//title
					PdfPTable aTable = new PdfPTable(titles.length + 1);
					aTable.setWidthPercentage(100);
					aTable.setHorizontalAlignment(Element.ALIGN_CENTER);
					float[] widths = {0.05f, 0.1f, 0.15f, 0.1f, 0.3f, 0.2f, 0.1f};
					aTable.setWidths(widths);

					PdfPCell aCell = new PdfPCell(new Phrase("序号", getChineseFont(12)));
					aCell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示  
					aCell.setVerticalAlignment(Element.ALIGN_MIDDLE);  // 设置垂直居中 
					aTable.addCell(aCell);
					for(int i = 0; i < titles.length; i++){
						aTable.addCell(new Phrase(titles[i], getChineseFont(12)));
					}
					
					//content
					for(int i = 0; i < result.size(); i++){
						SysLogLogin log = result.get(i);
						aTable.addCell(String.valueOf(i+1));
						aTable.addCell(log.getUserid());
						aTable.addCell(log.getLogindate());
						aTable.addCell(log.getLogintime());
						aTable.addCell(log.getLoginpassword());
						aTable.addCell(log.getLoginip());
						aTable.addCell(log.getSuccess());
					}
					document.add(aTable);
				}}.createPdf(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping
	public void excel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> requestMap = ServletUtil.getParametersStartingWith(request);
		final List<SysLogLogin> result = sysLogService.queryLogin(requestMap);
		response.setContentType(MediaTypes.TYPE_EXCEL);
		ServletUtil.setFileDownloadHeader(response, "SysLogLogin.xls");
		
		try {
			new ExcelCreate(){
				public void buildContent(Sheet sheet){
					String title = GlobalMessage.getMessage("5000");
					String[] titles = title.split(GlobalParam.STRING_SEPARATOR);
					
					//title
					ExcelUtil.getCell(sheet, 0, 0).setCellValue("序号");
					for(int i = 0; i < titles.length; i++){
						ExcelUtil.getCell(sheet, 0, i + 1).setCellValue(titles[i]);
					}
					//content
					for(int i = 0; i < result.size(); i++){
						SysLogLogin log = result.get(i);
						int row = i + 1;
						ExcelUtil.getCell(sheet, row, 0).setCellValue(row);
						ExcelUtil.getCell(sheet, row, 1).setCellValue(log.getUserid());
						ExcelUtil.getCell(sheet, row, 2).setCellValue(log.getLogindate());
						ExcelUtil.getCell(sheet, row, 3).setCellValue(log.getLogintime());
						ExcelUtil.getCell(sheet, row, 4).setCellValue(log.getLoginpassword());
						ExcelUtil.getCell(sheet, row, 5).setCellValue(log.getLoginip());
						ExcelUtil.getCell(sheet, row, 6).setCellValue(log.getSuccess());
					}
				}}.createExcel(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
