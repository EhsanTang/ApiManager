package cn.crap.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crap.dto.DictionaryDto;
import cn.crap.enu.ArticleType;
import cn.crap.enu.DictionaryPropertyType;
import cn.crap.enu.MyError;
import cn.crap.framework.MyException;
import cn.crap.model.ArticleWithBLOBs;
import net.sf.json.JSONArray;

public class SqlToDictionaryUtil {
	
		
	public static ArticleWithBLOBs mysqlToDictionary(String sql, String brief, String moduleId, String name) throws MyException{
		if(!sql.toLowerCase().replaceAll(" ", "").startsWith("createtable")){
			throw new MyException(MyError.E000046);
		}
		// 联合主键等被切分
		sql = sql.replace("`,`", "");
		ArticleWithBLOBs article = new ArticleWithBLOBs();
		article.setType(ArticleType.DICTIONARY.name());
		article.setBrief(brief);
		article.setModuleId(moduleId);
		article.setMarkdown("");
		article.setName(name);
		article.setCanDelete(Byte.valueOf("1"));
		
		
		sql = sql.replace("\n", " ");
		
		if(MyString.isEmpty(name)){
			String tableName = sql.substring(sql.toLowerCase().indexOf("table") + 5, sql.indexOf("(")).replaceAll("`", "").replaceAll("'", "").trim();
			article.setName(tableName);
		}
		String fields = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf(")") + 1);
		Map<String, DictionaryDto> propertys = new HashMap<String, DictionaryDto>();
		String[] fieldsArray =  fields.split(",");
		for(int i=0; i<fieldsArray.length; i++){
			String field = fieldsArray[i];
			// decimal(18,2) 被分隔
			try{
				Integer.parseInt(fieldsArray[i+1].substring(0,1));
				fieldsArray[i+1] = fieldsArray[i] +","+ fieldsArray[i+1];
				continue;
			}catch(Exception e){
			}
			try{
				// 字段
				DictionaryDto dto = null;
				if(field.trim().startsWith("`")){
					
					String property = field.trim().split(" ")[0].replaceAll("`", "").replaceAll("'", "");
					dto = propertys.get(property);
					if(dto == null) {
						dto = new DictionaryDto();
						dto.setName(property);
					}
					
					dto.setType(field.trim().split(" ")[1]);
					dto.setNotNull(field.trim().replace(" ", "").toLowerCase().indexOf("notnull") > 0?"false" : "true");
					
					if(field.toLowerCase().indexOf(" default ") > 0){
						String def = field.substring(field.toLowerCase().indexOf(" default "));
						// 默认值
						if(def.trim().startsWith("'")){
							dto.setDef(def.split("'")[1].replace("'", ""));
						}else{
							dto.setDef(def.trim().split(" ")[1].replace("'", ""));
						}
					}
					if(field.toLowerCase().indexOf(" comment ") > 0){
						String remark = field.substring(field.toLowerCase().indexOf(" comment "));
						// 默认值
						if(remark.trim().startsWith("'")){
							remark = remark.split("'")[1].replace("'", "");
							dto.setRemark(remark);
						}else{
							remark = remark.trim().split(" ")[1].replace("'", "");
							dto.setRemark(remark);
						}
					}
					propertys.put(dto.getName(), dto);
				}else{
					if(field.replaceAll(" ", "").toLowerCase().indexOf("primarykey") >= 0){
						String primaryKeys = field.substring( field.indexOf("(") + 1, field.indexOf(")")).replaceAll("`", "").replaceAll("'", "");
						for(String primaryKey : primaryKeys.split(",")){
							dto = propertys.get(primaryKey);
							if(dto == null) {
								dto = new DictionaryDto();
							}
							dto.setName(primaryKey);
							dto.setFlag(DictionaryPropertyType.primary.getName());
							propertys.put(dto.getName(), dto);
	
						}
					}
					if(field.replaceAll(" ", "").toLowerCase().indexOf("foreignkey") >= 0){
						String foreignKey = field.substring( field.indexOf("(") + 1, field.indexOf(")")).replaceAll("`", "").replaceAll("'", "");
						dto = propertys.get(foreignKey);
						if(dto == null) {
							dto = new DictionaryDto();
						}
						dto.setName(foreignKey);
						dto.setFlag(DictionaryPropertyType.foreign.getName());
						propertys.put(dto.getName(), dto);
					}
				}
			}catch(Exception e){
				if(i+1 < fieldsArray.length )
					fieldsArray[i+1] = fieldsArray[i] +"," + fieldsArray[i+1];
				continue;
			}
		}
		List<DictionaryDto> fieldList = new ArrayList<DictionaryDto>();
		for(String key: propertys.keySet()){
			if( propertys.get(key).getFlag().equals( DictionaryPropertyType.primary.getName() )
					|| propertys.get(key).getFlag().equals( DictionaryPropertyType.foreign.getName() )
					|| propertys.get(key).getFlag().equals( DictionaryPropertyType.associate.getName() ) ){
				fieldList.add(0,propertys.get(key));
			}else{
				fieldList.add(propertys.get(key));
			}
		}
		article.setContent(JSONArray.fromObject(fieldList).toString());
		return article;
	}
	
	public static ArticleWithBLOBs sqlserviceToDictionary(String sql, String brief, String moduleId, String name) throws MyException{
		if(!sql.toLowerCase().replaceAll(" ", "").startsWith("createtable")){
			throw new MyException(MyError.E000046);
		}
		String[]  descriptions = null;
		sql = sql.replace("\n", " ");
		if(sql.indexOf(" GO ")>0){
			descriptions = sql.split("@value = N");
			sql = sql.split(" GO ")[0];
		}

		ArticleWithBLOBs article = new ArticleWithBLOBs();
		article.setType(ArticleType.DICTIONARY.name());
		article.setBrief(brief);
		article.setModuleId(moduleId);
		article.setMarkdown("");
		article.setName(name);
		article.setCanDelete(Byte.valueOf("1"));
		
		
		if(MyString.isEmpty(name)){
			String tableName = sql.substring(sql.toLowerCase().indexOf("table") + 5, sql.indexOf("(")).replaceAll("\\[", "").replaceAll("\\]", "").trim();
			article.setName(tableName);
		}
		String fields = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf(")") + 1);
		Map<String, DictionaryDto> propertys = new HashMap<String, DictionaryDto>();
		String[] fieldsArray =  fields.split(",");
		for(int i=0; i<fieldsArray.length; i++){
			String field = fieldsArray[i];
			// decimal(18,2) 被分隔
			try{
				Integer.parseInt(fieldsArray[i+1].substring(0,1));
				fieldsArray[i+1] = fieldsArray[i] +","+ fieldsArray[i+1];
				continue;
			}catch(Exception e){
			}
			try{
				// 字段
				DictionaryDto dto = null;
				if(field.trim().startsWith("[")){
					
					String property = field.trim().split(" ")[0].replaceAll("\\[", "").replaceAll("\\]", "");
					dto = propertys.get(property);
					if(dto == null) {
						dto = new DictionaryDto();
						dto.setName(property);
					}
					
					dto.setType(field.trim().split(" ")[1]);
					dto.setNotNull(field.trim().replace(" ", "").toLowerCase().indexOf("notnull") > 0?"false" : "true");
					
					if(field.toLowerCase().indexOf(" default ") > 0){
						String def = field.substring(field.toLowerCase().indexOf(" default ") + 9);
						// 默认值
						dto.setDef(def);
					}
					// 备注
					if(descriptions != null){
						for(String des:descriptions){
							if(des.indexOf("@level2type = 'COLUMN', @level2name = N'"+dto.getName()+"'") > 0){
								dto.setRemark(des.split(",")[0].replaceAll("'", ""));
							}
						}
						
					}
					propertys.put(dto.getName(), dto);
				}else{
					if(field.replaceAll(" ", "").toLowerCase().indexOf("primarykey") >= 0){
						String primaryKeys = field.substring( field.indexOf("(") + 1, field.indexOf(")")).replaceAll("\\[", "").replaceAll("\\]", "");
						for(String primaryKey : primaryKeys.split(",")){
							dto = propertys.get(primaryKey);
							if(dto == null) {
								dto = new DictionaryDto();
							}
							dto.setName(primaryKey);
							dto.setFlag(DictionaryPropertyType.primary.getName());
							propertys.put(dto.getName(), dto);
	
						}
					}
					if(field.replaceAll(" ", "").toLowerCase().indexOf("foreignkey") >= 0){
						String foreignKey = field.substring( field.indexOf("(") + 1, field.indexOf(")")).replaceAll("\\[", "").replaceAll("\\]", "");
						dto = propertys.get(foreignKey);
						if(dto == null) {
							dto = new DictionaryDto();
						}
						dto.setName(foreignKey);
						dto.setFlag(DictionaryPropertyType.foreign.getName());
						propertys.put(dto.getName(), dto);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		List<DictionaryDto> fieldList = new ArrayList<DictionaryDto>();
		for(String key: propertys.keySet()){
			if( propertys.get(key).getFlag().equals( DictionaryPropertyType.primary.getName() )
					|| propertys.get(key).getFlag().equals( DictionaryPropertyType.foreign.getName() )
					|| propertys.get(key).getFlag().equals( DictionaryPropertyType.associate.getName() ) ){
				fieldList.add(0,propertys.get(key));
			}else{
				fieldList.add(propertys.get(key));
			}
		}
		article.setContent(JSONArray.fromObject(fieldList).toString());
		return article;
	}
	
	
//  sqlservice test
//	public static void main(String args[]) throws MyException{
//		String sql = "CREATE DICTIONARY [dbo].[addr_city] ("+
//				"[ADDR_ID] varchar(12) NOT NULL ,"+
//				"[ADDR_NAME] varchar(48) NOT NULL ,"+
//				"[FATHER_ID] varchar(12) NOT NULL ,"+
//				"[IS_ENABLED] int NULL ,"+
//				"[estimated_delivery_time] datetime NULL,"+
//				"[create_by] varchar(50) NOT NULL ,"+
//				"[create_time] datetime NOT NULL ,"+
//				"[update_by] varchar(50) NOT NULL ,"+
//				"[update_time] datetime NOT NULL ,"+
//				"[orderType] int NULL ,"+
//				"[orderSource] int NULL ,"+
//				"[freeExpress] bit NULL ,"+
//				"[promotionId] int NULL ,"+
//				"[platform] nvarchar(32) NULL ,"+
//				"[source] nvarchar(64) NULL ,"+
//				"[test_order] bit NULL ,"+
//				"[createtime] datetime NOT NULL, "+
//				"[bbb] varchar NULL DEFAULT 默认 ,"+
//				"[ccc] bigint NULL DEFAULT 10 ,"+
//				"PRIMARY KEY ([aaa]),"+
//				"CONSTRAINT [dd] FOREIGN KEY ([ccc]) REFERENCES [dbo].[customer_order_base] ([order_id])"+
//				")"+
//				"\nGO\n"+
//				"IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', "+
//				"'SCHEMA', N'dbo', "+
//				"'DICTIONARY', N'customer_order_base', "+
//				"'COLUMN', N'order_id')) > 0) "+
//				"EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'订单编号'"+
//				", @level0type = 'SCHEMA', @level0name = N'dbo'"+
//				", @level1type = 'DICTIONARY', @level1name = N'customer_order_base'"+
//				", @level2type = 'COLUMN', @level2name = N'order_id'"+
//				"ELSE"+
//				"EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'订单编号'"+
//				", @level0type = 'SCHEMA', @level0name = N'dbo'"+
//				", @level1type = 'DICTIONARY', @level1name = N'customer_order_base'"+
//				", @level2type = 'COLUMN', @level2name = N'test_order'"+
//				"GO;";
//		Article table  = sqlserviceToDictionary(sql,"","","");
//		System.out.println("-----------"+table.getContent());
//	}
}
