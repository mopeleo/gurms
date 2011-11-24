package org.gurms.common.pdmparse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PDMParser {

	private static final String ATTR_ID = "Id";
	private static final String ATTR_REF = "Ref";
    private static final String ELEMENT_NAME = "Name";
    private static final String ELEMENT_CODE = "Code";
    private static final String ELEMENT_COMMENT = "Comment";
    private static final String ELEMENT_DATATYPE = "DataType";
    private static final String ELEMENT_LENGTH = "Length";
    private static final String ELEMENT_MANDATORY = "Mandatory";
    private static final String ELEMENT_COLUMNS = "Columns";
    private static final String ELEMENT_KEY = "Key";
    private static final String ELEMENT_KEYS = "Keys";
    private static final String ELEMENT_KEY_COLUMN = "Key.Columns";
    private static final String ELEMENT_PRIMARYKEY = "PrimaryKey";
    private static final String NODE_ROOT = "//Model";
    private static final String NODE_TABLE = NODE_ROOT + "/o:RootObject/c:Children/o:Model/c:Tables/o:Table";
    
    private static String filepath = "D:\\kcrm\\doc\\02.设计\\2.3数据库设计";
	private static String filename = "gurms-test.pdm";
	
	public static Model parse(File pdm){
		Model model = new Model();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(pdm);
			//直接跳到o:Table节点解析
			List tables = document.selectNodes(NODE_TABLE);
			if(tables == null || tables.size() == 0){
				return model;
			}

			for(int i = 0; i < tables.size(); i++){
				Table table = new Table();
				Element elementTable=(Element)tables.get(i);
				
				Attribute attrId = elementTable.attribute(ATTR_ID);
				table.setId(attrId.getValue());
				
				Element elementName = elementTable.element(ELEMENT_NAME);
				table.setName(elementName.getTextTrim());

				Element elementCode = elementTable.element(ELEMENT_CODE);
				table.setCode(elementCode.getTextTrim().toLowerCase());

				Element elementCommont = elementTable.element(ELEMENT_COMMENT);
				table.setComment(elementCommont.getTextTrim());
				
				//解析c:PrimaryKey节点
				Element elementPK = elementTable.element(ELEMENT_PRIMARYKEY).element(ELEMENT_KEY);
				String keyRefId = elementPK.attribute(ATTR_REF).getValue();
				
				//解析c:Keys节点
				List keys = elementTable.element(ELEMENT_KEYS).elements();
				List<String> keyList = new ArrayList<String>();
				for(int j = 0; j < keys.size(); j++){
					Element elementKey = (Element)keys.get(j);					
					String keyId = elementKey.attribute(ATTR_ID).getValue();
					if(keyId.equals(keyRefId)){
						//解析c:Key.Columns节点
						List refCols = elementKey.element(ELEMENT_KEY_COLUMN).elements();
						for(int k = 0; k < refCols.size(); k++){
							Element elementRefCol = (Element)refCols.get(k);							
							String colRefId = elementRefCol.attribute(ATTR_REF).getValue();
							keyList.add(colRefId);
						}						
						break;
					}else{
						continue;
					}					
				}
				
				//解析c:Columns节点
				List columns = elementTable.element(ELEMENT_COLUMNS).elements();
				for(int j = 0; j < columns.size(); j++){
					Column column = new Column();
					Element elementColumn = (Element)columns.get(j);
					
					attrId = elementColumn.attribute(ATTR_ID);
					column.setId(attrId.getValue());
					
					elementName = elementColumn.element(ELEMENT_NAME);
					column.setName(elementName.getTextTrim());

					elementCode = elementColumn.element(ELEMENT_CODE);
					column.setCode(elementCode.getTextTrim().toLowerCase());

					elementCommont = elementColumn.element(ELEMENT_COMMENT);
					column.setComment(elementCommont.getTextTrim());

					Element elementDataType = elementColumn.element(ELEMENT_DATATYPE);
					column.setDatatype(elementDataType.getTextTrim());

					Element elementLength = elementColumn.element(ELEMENT_LENGTH);
					column.setLength(elementLength.getTextTrim());

					Element elementMandatory = elementColumn.element(ELEMENT_MANDATORY);
					if(elementMandatory == null){
						column.setMandatory(null);
					}else{
						column.setMandatory(elementMandatory.getTextTrim());
					}
					
					table.addColumn(column);
					if(keyList.contains(column.getId())){
						table.addKey(column);
					}
				}

				model.addTable(table);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public static void main(String[] args) {
		File input = new File(filepath + File.separator + filename);
		List<Table> tables = parse(input).getTables();
		for(int i = 0; i < tables.size(); i++){
			Table table = tables.get(i);
			System.out.println(table.getId() + " " +table.getCode() + " " + table.getComment());
			System.out.println("--------------------");
			List<Column> cols = table.getColumns();
			for(Column col : cols){
				System.out.println(col.getId() + " " + col.getCode() + " " + col.getComment() + " " + col.getDatatype() + " " + col.getLength());
			}
			
			List<Column> keys = table.getKeys();
			for(Column key : keys){
				System.out.println(key.getId() + " " + key.getCode());
			}
		}
	}
}
