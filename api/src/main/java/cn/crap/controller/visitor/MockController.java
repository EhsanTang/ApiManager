package cn.crap.controller.visitor;

import cn.crap.enu.InterfaceContentType;
import cn.crap.framework.base.BaseController;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.service.InterfaceService;
import cn.crap.service.tool.StringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mock")
public class MockController extends BaseController{

	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private StringCache stringCache;

	private static final String MOCK_KEY_PRE = "inter:mock:";

	@RequestMapping("/trueExam.do")
	@ResponseBody
	public void trueExam(HttpServletResponse response,  @RequestParam String id, @RequestParam(defaultValue = "false") Boolean cache){
        getExam(response, id, true, cache);
    }

    @RequestMapping("/falseExam.do")
	@ResponseBody
	public void falseExam(HttpServletResponse response, @RequestParam String id, @RequestParam(defaultValue = "false") Boolean cache){
        getExam(response, id, false, cache);
	}


    private void getExam(HttpServletResponse response, String id, boolean isTrueExam, boolean cache) {
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers",
                "Authorization,Content-Type,Accept,Origin,User-Agent,DNT,Cache-Control,X-Mx-ReqToken,X-Requested-With");
        response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE");
        response.addHeader("Access-Control-Allow-Origin", "*");
        String mockKey = getMockKey(id, isTrueExam);
        String contentTypeKey = getMockKey(id, null);

        if (cache){
            String contentType = stringCache.get(contentTypeKey);
            String mockResult = stringCache.get(mockKey);
            if (contentType != null && mockResult != null){
                printMsg(mockResult, InterfaceContentType.getByType(contentType));
                return;
            }
        }

        InterfaceWithBLOBs interfaceWithBLOBs = interfaceService.getById(id);
        String mockResult = isTrueExam ? interfaceWithBLOBs.getTrueExam() : interfaceWithBLOBs.getFalseExam();
        if (cache){
            stringCache.add(mockKey, mockResult);
            stringCache.add(contentTypeKey, interfaceWithBLOBs.getContentType());
        }
        printMsg(mockResult, InterfaceContentType.getByType(interfaceWithBLOBs.getContentType()));
    }

	public static String getMockKey(String id, Boolean isTrueExam){
	    if (isTrueExam == null){
            return MOCK_KEY_PRE + "contentType:" + id;
        }
		return MOCK_KEY_PRE + (isTrueExam ? "true:" : "false:")+ id;
	}
}
