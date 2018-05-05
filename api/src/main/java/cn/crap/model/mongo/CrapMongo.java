//package cn.crap.model.mongo;
//
//import org.springframework.data.mongodb.core.index.CompoundIndex;
//import org.springframework.data.mongodb.core.index.CompoundIndexes;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import com.alibaba.fastjson.JSONObject;
//
//@Document(collection = "crap_api_v1")
//@CompoundIndexes({ @CompoundIndex(def = "{user_id:1}")})
//public class CrapMongo{
//
//    @Indexed
//    @Field(value = "user_id")
//    private String userId;
//
//    @Field(value = "id")
//    private String id;
//
//    @Field(value = "table")
//    private String table;
//
//    @Field(value = "data")
//	private JSONObject data;
//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//	public JSONObject getData() {
//		return data;
//	}
//	public void setData(JSONObject data) {
//		this.data = data;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getTable() {
//		return table;
//	}
//
//	public void setTable(String table) {
//		this.table = table;
//	}
//}
