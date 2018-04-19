package cn.crap.controller.front;

import cn.crap.enumer.InterfaceContentType;
import cn.crap.model.mybatis.InterfaceWithBLOBs;
import cn.crap.service.mybatis.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.base.BaseController;

@Controller
@RequestMapping("/mock")
public class MockController extends BaseController{

	@Autowired
	private InterfaceService interfaceService;
	
	@RequestMapping("/trueExam.do")
	@ResponseBody
	public void trueExam(@RequestParam String id) throws Exception {
		InterfaceWithBLOBs interfaceWithBLOBs = interfaceService.getById(id);
		printMsg(interfaceWithBLOBs.getTrueExam(), InterfaceContentType.getByType(interfaceWithBLOBs.getContentType()));
	}
	
	@RequestMapping("/falseExam.do")
	@ResponseBody
	public void falseExam(@RequestParam String id) throws Exception {
		InterfaceWithBLOBs interfaceWithBLOBs = interfaceService.getById(id);
		printMsg(interfaceWithBLOBs.getFalseExam(), InterfaceContentType.getByType(interfaceWithBLOBs.getContentType()));
	}
}
