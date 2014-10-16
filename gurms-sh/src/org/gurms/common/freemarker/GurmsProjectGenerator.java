package org.gurms.common.freemarker;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gurms.common.config.GlobalConfig;
import org.gurms.common.pdmparse.Model;
import org.gurms.common.pdmparse.PDMParser;
import org.gurms.common.pdmparse.Table;

public class GurmsProjectGenerator {

	public static final String TABLE_SEPATATOR = "_";

	public static final String TEMPLATE_DIR = GlobalConfig.getConfig("template_dir");

	public static final String TEMPLATE_DAO = GlobalConfig.getConfig("template_dao");
	public static final String TEMPLATE_ENTITY = GlobalConfig.getConfig("template_entity");
	public static final String TEMPLATE_ENTITYID = GlobalConfig.getConfig("template_entityid");
	public static final String TEMPLATE_ENTITYVALID = GlobalConfig.getConfig("template_entityvalid");
	public static final String TEMPLATE_SERVICE = GlobalConfig.getConfig("template_service");
	public static final String TEMPLATE_SERVICEIMPL = GlobalConfig.getConfig("template_serviceimpl");
	public static final String TEMPLATE_CONTROLLER = GlobalConfig.getConfig("template_controller");
	public static final String TEMPLATE_LIST = GlobalConfig.getConfig("template_list");
	public static final String TEMPLATE_DETAIL = GlobalConfig.getConfig("template_detail");
	public static final String TEMPLATE_SQLMENU = GlobalConfig.getConfig("template_sqlmenu");
	
	public static final String FILETYPE_DAO = "Dao.java";
	public static final String FILETYPE_ENTITY = ".java";
	public static final String FILETYPE_ENTITYID = "Id.java";
	public static final String FILETYPE_ENTITYVALID = "entityvalid.properties";
	public static final String FILETYPE_SERVICE = "Service.java";
	public static final String FILETYPE_SERVICEIMPL = "ServiceImpl.java";
	public static final String FILETYPE_CONTROLLER = "Controller.java";
	public static final String FILETYPE_LISTFTL = "list.ftl";
	public static final String FILETYPE_DETAILFTL = "detail.ftl";
	public static final String FILETYPE_SQLMENU = "menu.sql";

	public static final String PACKAGE_DAO = "dao.hibernate";
	public static final String PACKAGE_ENTITY = "entity";
	public static final String PACKAGE_SERVICE = "service";
	public static final String PACKAGE_SERVICEIMPL = "service.impl";
	public static final String PACKAGE_CONTROLLER = "web.controller";

	public static void generate(Map params, String templateFile, String outFile){
		CodeGenerator.generate(TEMPLATE_DIR, templateFile, params, outFile);
	}
	
	public static void entityGenerate(Map params, String outFile){
		generate(params, TEMPLATE_ENTITY, outFile);
	}	
	
	public static void entityIdGenerate(Map params, String outFile){
		generate(params, TEMPLATE_ENTITYID, outFile);
	}	
	
	public static void entityValidGenerate(Map params, String outFile){
		generate(params, TEMPLATE_ENTITYVALID, outFile);
	}	
	
	public static void daoGenerate(Map params, String outFile){
		generate(params, TEMPLATE_DAO, outFile);
	}	
	
	public static void serviceGenerate(Map params, String outFile){
		generate(params, TEMPLATE_SERVICE, outFile);
	}	
	
	public static void serviceImplGenerate(Map params, String outFile){
		generate(params, TEMPLATE_SERVICEIMPL, outFile);
	}	
	
	public static void controllerGenerate(Map params, String outFile){
		generate(params, TEMPLATE_CONTROLLER, outFile);
	}	
	
	public static void listGenerate(Map params, String outFile){
		generate(params, TEMPLATE_LIST, outFile);
	}	
	
	public static void detailGenerate(Map params, String outFile){
		generate(params, TEMPLATE_DETAIL, outFile);
	}	
	
	public static void sqlMenuGenerate(Map params, String outFile){
		generate(params, TEMPLATE_SQLMENU, outFile);
	}	
	
	private static String getJavaOutFile(Map params, String outDir, String projectPrefix, String pkgName, String fileSuffix){
		String outFileName = outDir + (projectPrefix + "." + pkgName).replace('.', File.separatorChar);
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
	
	private static String getWebOutFile(Map params, String outDir, String fileSuffix){
		String outFileName = outDir + params.get("entity").toString().toLowerCase();
		
		File file = new File(outFileName);
		if(!file.exists()){
			file.mkdirs();
		}
		outFileName += File.separator + fileSuffix;

		return outFileName;
	}
	
	public static void projectGenerate(String pdmFile, String projectPrefix, String javaOutDir, String webOutDir){
		Model model = PDMParser.parse(new File(pdmFile));
		String outFile = null;
		Map params = new HashMap();
		params.put("project", projectPrefix);

		List<Table> tables = model.getTables();
		params.put("tables", tables);
		params.put("entity", "");
		outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_ENTITY, FILETYPE_ENTITYVALID);
		entityValidGenerate(params, outFile);

		outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_ENTITY, FILETYPE_SQLMENU);
		sqlMenuGenerate(params, outFile);

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
			
			//一个table的非主键列至少存在一个
			if(table.getColumns().size() < 1){
				continue;
			}
			params.put("entity", entity);
			params.put("table", table);
			
			System.out.println("---------生成 ["+table.getName() + "(" + table.getCode() + ")] 代码开始----------");
			outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_DAO, FILETYPE_DAO);
			daoGenerate(params, outFile);
			
			outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_SERVICE, FILETYPE_SERVICE);
			serviceGenerate(params, outFile);
			
			outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_SERVICEIMPL, FILETYPE_SERVICEIMPL);
			serviceImplGenerate(params, outFile);
			
			outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_CONTROLLER, FILETYPE_CONTROLLER);
			controllerGenerate(params, outFile);
			
			outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_ENTITY, FILETYPE_ENTITY);
			entityGenerate(params, outFile);

			if(table.getKeys().size() > 1){
				outFile = getJavaOutFile(params, javaOutDir, projectPrefix, PACKAGE_ENTITY, FILETYPE_ENTITYID);
				entityIdGenerate(params, outFile);
			}

			outFile = getWebOutFile(params, webOutDir, FILETYPE_LISTFTL);
			listGenerate(params, outFile);

			outFile = getWebOutFile(params, webOutDir, FILETYPE_DETAILFTL);
			detailGenerate(params, outFile);

			System.out.println("---------生成 ["+table.getName() + "(" + table.getCode() + ")] 代码结束----------");
		}
	}
	
	public static void main(String[] args) {
	    String pdm = args[0];
	    System.out.println("pdm : " + pdm);
	    String pkgprefix = args[1];
	    System.out.println("package prefix : " + pkgprefix);
	    String javaoutpath = args[2];
	    System.out.println("java outpath : " + pkgprefix);
	    String weboutpath = args[3];
	    System.out.println("web outpath : " + pkgprefix);
		projectGenerate(pdm, pkgprefix, javaoutpath, weboutpath);
	    
	}
}
