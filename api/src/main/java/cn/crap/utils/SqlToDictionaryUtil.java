package cn.crap.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crap.dto.DictionaryDto;
import cn.crap.enumeration.ArticleType;
import cn.crap.enumeration.DictionaryPropertyType;
import cn.crap.model.Article;
import net.sf.json.JSONArray;

public class SqlToDictionaryUtil {
	public static Article mysqlToDictionary(String sql, String brief, String moduleId, String name){
		Article article = new Article();
		article.setType(ArticleType.DICTIONARY.name());
		article.setBrief(brief);
		article.setModuleId(moduleId);
		article.setMarkdown("");
		article.setName(name);
		article.setCanDelete(Byte.valueOf("1"));
		
		sql = sql.toLowerCase().replace("\n", "");
		
		if(MyString.isEmpty(name)){
			String tableName = sql.substring(sql.indexOf("table") + 5, sql.indexOf("(")).replaceAll("`", "").replace("'", "").trim();
			article.setName(tableName);
		}
		String fields = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf(")") + 1);
		Map<String, DictionaryDto> propertys = new HashMap<String, DictionaryDto>();
		String[] fieldsArray =  fields.split(",");
		for(int i=0; i<fieldsArray.length; i++){
			String field = fieldsArray[i];
			try{
				// 字段
				DictionaryDto dto = null;
				if(field.trim().startsWith("`")){
					
					String property = field.trim().split(" ")[0].replaceAll("`", "").replace("'", "");
					dto = propertys.get(property);
					if(dto == null) {
						dto = new DictionaryDto();
						dto.setName(property);
					}
					
					dto.setType(field.trim().split(" ")[1]);
					dto.setNotNull(field.trim().replace(" ", "").indexOf("notnull") > 0?"false" : "true");
					
					if(field.indexOf(" default ") > 0){
						String def = field.substring(field.indexOf(" default "));
						// 默认值
						if(def.trim().startsWith("'")){
							dto.setDef(def.split("'")[1].replace("'", ""));
						}else{
							dto.setDef(def.trim().split(" ")[1].replace("'", ""));
						}
					}
					if(field.indexOf(" comment ") > 0){
						String remark = field.substring(field.indexOf(" comment "));
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
					if(field.replaceAll(" ", "").indexOf("primarykey") >= 0){
						String primaryKeys = field.substring( field.indexOf("(") + 1, field.indexOf(")")).replaceAll("`", "").replace("'", "");
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
					if(field.replaceAll(" ", "").indexOf("foreignkey") >= 0){
						String foreignKey = field.substring( field.indexOf("(") + 1, field.indexOf(")")).replaceAll("`", "").replace("'", "");
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
			fieldList.add(propertys.get(key));
		}
		article.setContent(JSONArray.fromObject(fieldList).toString());
		return article;
	}
}
