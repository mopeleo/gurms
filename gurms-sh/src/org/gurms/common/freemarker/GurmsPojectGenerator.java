package org.gurms.common.freemarker;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gurms.common.config.GlobalConfig;
import org.gurms.common.pdmparse.Model;
import org.gurms.common.pdmparse.PDMParser;
import org.gurms.common.pdmparse.Table;

public class GurmsPojectGenerator {

	public static final String TABLE_SEPATATOR = "_";
	public static final String OUTPUT_DIR = "d:\\";

	public static final String PROJECT_PREFIX = GlobalConfig.getConfig("project_prefix");
	public static final String TEMPLATE_DIR = GlobalConfig.getConfig("template_dir");

	public static final String TEMPLATE_DAO = GlobalConfig.getConfig("template_dao");
	public static final String TEMPLATE_ENTITY = GlobalConfig.getConfig("template_entity");
	public static final String TEMPLATE_SERVICE = GlobalConfig.getConfig("template_service");
	public static final String TEMPLATE_CONTROLLER = GlobalConfig.getConfig("template_controller");
	
	public static final String FILETYPE_DAO = "Dao.java";
	public static final String FILETYPE_ENTITY = ".java";
	public static final String FILETYPE_SERVICE = "Service.java";
	public static final String FILETYPE_CONTROLLER = "Controller.java";

	public static final String PACKAGE_DAO = "dao.hibernate";
	public static final String PACKAGE_ENTITY = "entity";
	public static final String PACKAGE_SERVICE = "service";
	public static final String PACKAGE_CONTROLLER = "web.controller";

	public static void generate(Map params, String templateFile, String outFile){
		CodeGenerator.generate(TEMPLATE_DIR, templateFile, params, outFile);
	}
	
	public static void entityGenerate(Map params, String outFile){
		generate(params, TEMPLATE_ENTITY, outFile);
	}	
	
	public static void daoGenerate(Map params, String outFile){
		generate(params, TEMPLATE_DAO, outFile);
	}	
	
	public static void serviceGenerate(Map params, String outFile){
		generate(params, TEMPLATE_SERVICE, outFile);
	}	
	
	public static void controllerGenerate(Map params, String outFile){
		generate(params, TEMPLATE_CONTROLLER, outFile);
	}	
	
	private static String getOutFile(Map params, String pkgName, String fileSuffix){
		String outFileName = OUTPUT_DIR + (PROJECT_PREFIX + "." + pkgName).replace('.', File.separatorChar);
		Object model = params.get("model");
		if(model != null){
			outFileName += File.separator + model.toString() ;
		}
		
		File file = new File(outFileName);
		if(!file.exists()){
			file.mkdirs();
		}
		outFileName += File.separator + params.get("entity").toString() + fileSuffix;

		return outFileName;
	}
	
	public static void projectGenerate(String pdmFile){
		Model model = PDMParser.parse(new File(pdmFile));
		Map params = new HashMap();
		params.put("project", PROJECT_PREFIX);
		List<Table> tables = model.getTables();
		for(Table table : tables){
			String tableCode = table.getCode();
			String entity = "";
			if(tableCode.indexOf(TABLE_SEPATATOR) > 0){
				String[] parts = tableCode.split(TABLE_SEPATATOR);
				params.put("model", parts[0]);
				for(int i = 0; i< parts.length; i++){
					entity += parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
				}
			}else{
				entity = tableCode.substring(0, 1).toUpperCase() + tableCode.substring(1);
			}
			params.put("entity", entity);
			
			String outFile = getOutFile(params, PACKAGE_DAO, FILETYPE_DAO);
			daoGenerate(params, outFile);
			outFile = getOutFile(params, PACKAGE_SERVICE, FILETYPE_SERVICE);
			serviceGenerate(params, outFile);
			outFile = getOutFile(params, PACKAGE_CONTROLLER, FILETYPE_CONTROLLER);
			controllerGenerate(params, outFile);
		}
	}
	
	public static void main(String[] args) {
//		String fullpath = TEMPLATE_DIR + File.separator + TEMPLATE_SERVICE;
//		InputStream p = JavaCodeGenerator.class.getClassLoader().getResourceAsStream(fullpath);
//		try{
//			byte[] readed = new byte[1024];
//			while(p.read(readed, 0, readed.length) != -1){
//				System.out.println(new String(readed));
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		Map m = new HashMap();
		m.put("model", "Test");
//		daoGenerate(m,"d:\\test.java");
		
	    String filepath = "D:\\kcrm\\doc\\02.设计\\2.3数据库设计\\gurms-test.pdm";
		projectGenerate(filepath);
	}
}
