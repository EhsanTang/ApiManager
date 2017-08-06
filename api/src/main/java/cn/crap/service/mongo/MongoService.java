package cn.crap.service.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.crap.model.mongo.CrapMongo;

@Service
public class MongoService {
    private static Logger logger  = LoggerFactory.getLogger(MongoService.class);
    @Autowired(required=false)
    private MongoTemplate mongoTemplate;

    public void insert(CrapMongo object){
    	try{
	        if(object == null){
	            return;
	        }
	        this.mongoTemplate.insert(object);
    	}catch(Exception e){
    		logger.debug("[插入数据异常]", e);
    	}
    }
    
    public void update(){
    	try{
	        mongoTemplate.updateFirst(new Query(Criteria.where("id").is("11111")), new Update().set("data.name","唐韬"), CrapMongo.class);
    	}catch(Exception e){
    		logger.debug("[插入数据异常]", e);
    	}
    }
}
