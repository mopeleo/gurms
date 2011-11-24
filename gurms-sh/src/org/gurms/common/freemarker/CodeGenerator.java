package org.gurms.common.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CodeGenerator {

	public static final String ENCODING = "utf-8";
	public static final String CLASS_PATH = CodeGenerator.class.getClassLoader().getResource("").getPath();

	/** 
     * @param tmpPath  模版路径 
     * @param tmpFile  模版文件名 
     * @param params   参数 
     * @param outFile  生成的静态文件路径(包含文件名称) 
     */  
	public static void generate(String tmpPath, String tmpFile, Map params, String outFile){
		String fullPath = null;
		if(CLASS_PATH.endsWith(File.separator) && tmpPath.startsWith(File.separator)){
			fullPath = CLASS_PATH + tmpPath.substring(1);
		}else if(!CLASS_PATH.endsWith(File.separator) && !tmpPath.startsWith(File.separator)){
			fullPath = CLASS_PATH + File.separator + tmpPath;
		}else{
			fullPath = CLASS_PATH + tmpPath;
		}
		File templateDir = new File(fullPath);
		Writer out = null;
		try{
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(templateDir);
			cfg.setLocale(Locale.CHINA);
			cfg.setDefaultEncoding(ENCODING);
			
			Template t = cfg.getTemplate(tmpFile);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
			t.process(params, out);
		}catch(IOException e){
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}finally{
			if (out != null) {  
                try {  
                    out.flush();  
                    out.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            } 
		}
	}	
}
