package org.gurms.common.pdf;

import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class PDFUtil {
	public void createDocument(OutputStream os){
		Document document = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(document, os);
			document.open();
			buildContent(document);
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void buildContent(Document document) throws DocumentException;
	
    public Font getChineseFont(float size){
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Font(bfChinese, size, Font.NORMAL);
	}
}
