package cn.crap.controller.front;

import cn.crap.model.mybatis.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.XmlParamsDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.Article;

/**
 * 测试接口
 * @author Ehsan
 *
 */
@Controller("exampleController")
public class ExampleController extends BaseController{
	@RequestMapping("/front/example/json.do")
	@ResponseBody
	public JsonResult json(@RequestBody Article article) throws MyException{
		return new JsonResult(1, article);
	}
	@RequestMapping("/front/example/xml.do")
	@ResponseBody
	public JsonResult xml(@RequestBody XmlParamsDto users) throws MyException{
		return new JsonResult(1, users);
	}
	
	@RequestMapping("/front/example/body.do")
	@ResponseBody
	public JsonResult body(@RequestBody String body) throws MyException{
		return new JsonResult(1, body);
	}
	
	@RequestMapping("/front/example/post.do")
	@ResponseBody
	public JsonResult post(@RequestBody String json) throws MyException{
		System.out.println(json);
		return new JsonResult(1, json);
	}

	
}
