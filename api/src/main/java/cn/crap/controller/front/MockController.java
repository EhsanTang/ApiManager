package cn.crap.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IInterfaceService;
import cn.crap.model.Interface;

@Controller
@RequestMapping("/mock")
public class MockController extends BaseController<Interface>{

	@Autowired
	private IInterfaceService interfaceService;
	
	@RequestMapping("/trueExam.do")
	@ResponseBody
	public void trueExam(@RequestParam String id) throws Exception {
		printMsg(interfaceService.get(id).getTrueExam());
	}
	
	@RequestMapping("/falseExam.do")
	@ResponseBody
	public void falseExam(@RequestParam String id) throws Exception {
		printMsg(interfaceService.get(id).getFalseExam());
	}
}
