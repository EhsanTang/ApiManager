package cn.crap.controller.visitor;

import cn.crap.dto.XmlParamsDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.model.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 测试接口
 * @author Ehsan
 *
 */
@Controller("exampleController")
public class ExampleController extends BaseController{
	@RequestMapping("/visitor/example/json.do")
	@ResponseBody
	public JsonResult json(@RequestBody Article article){
		return new JsonResult(1, article);
	}
	@RequestMapping("/visitor/example/xml.do")
	@ResponseBody
	public JsonResult xml(@RequestBody XmlParamsDto users){
		return new JsonResult(1, users);
	}
	
	@RequestMapping("/visitor/example/body.do")
	@ResponseBody
	public JsonResult body(@RequestBody String body){
		log.warn("--visitor/example/body.do:" + body);
		return new JsonResult(1, body);
	}
	
	@RequestMapping("/visitor/example/post.do")
	@ResponseBody
	public JsonResult post(String param, Integer sleepTimes) throws Exception{
		if (sleepTimes != null && sleepTimes > 0 && sleepTimes < 5){
			Thread.sleep(sleepTimes * 1000);
		}
		return new JsonResult(1, param);
	}

	@RequestMapping("/visitor/example/error.do")
	@ResponseBody
	public JsonResult error(@RequestParam(defaultValue = "200") Integer errorCode, HttpServletResponse response){
		response.setStatus(errorCode);
		return new JsonResult(1, errorCode);
	}

	
}
