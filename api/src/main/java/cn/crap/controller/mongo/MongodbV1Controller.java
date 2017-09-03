//package cn.crap.controller.mongo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//
//import cn.crap.framework.JsonResult;
//import cn.crap.framework.MyException;
//import cn.crap.framework.auth.AuthPassport;
//import cn.crap.framework.base.BaseController;
//import cn.crap.model.Article;
//import cn.crap.model.mongo.CrapMongo;
//import cn.crap.service.imp.mongo.MongoService;
//
//@Controller
//@RequestMapping("/mongodb/v1")
//public class MongodbV1Controller extends BaseController<Article>{
//
//	@Autowired(required=false)
//    private MongoService mongoService;
//
//	@RequestMapping("/add.do")
//	@ResponseBody
//	@AuthPassport
//	public JsonResult add() throws MyException{
//		CrapMongo crapMongo = new CrapMongo();
//		JSONObject json = JSONObject.parseObject("{\"name\":\"TT\"}");
//		crapMongo.setData(json);
//		crapMongo.setUserId("111111");
//		crapMongo.setId("11111");
//		mongoService.insert(crapMongo);
//		return new JsonResult(1,null);
//	}
//
//	@RequestMapping("/update.do")
//	@ResponseBody
//	@AuthPassport
//	public JsonResult update() throws MyException{
//		mongoService.update();
//		return new JsonResult(1,null);
//	}
//
//}
