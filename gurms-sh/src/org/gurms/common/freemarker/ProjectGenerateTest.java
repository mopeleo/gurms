package org.gurms.common.freemarker;


public class ProjectGenerateTest {
	
	public static void main(String[] args) {
		
	    String filepath = "E:\\pdmworkspace\\gurms.pdm";
	    GurmsProjectGenerator.projectGenerate(filepath, "org.gurms", "e:\\test\\", "e:\\test\\web\\");
	
	}

}
