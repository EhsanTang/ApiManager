package cn.crap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.dto.PickDto;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IMenuService;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.ValidateCodeService;

/**
 * 前后台共用的Controller
 * @author Ehsan
 *
 */
@Scope("prototype")
@Controller
public class IndexController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	
	
	/**
	 * 
	 * @param code
	 *            需要显示的pick code
	 * @param key
	 *            可选参数：根据具体情况定义，如当为模块是，key代表父id
	 * @param radio
	 *            是否为单选
	 * @param def
	 *            默认值
	 * @param tag
	 *            保存选中结果的id
	 * @param tagName
	 *            显示名称的输入框id
	 * @param notNull
	 *            是否可以为空：当为单选，且notNull=false是，则可以选着为空
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pick.do")
	public String pickOut(String code,@RequestParam(defaultValue = "")  String key, @RequestParam(defaultValue = "true") String radio, String def,
			String tag, String tagName, String notNull) throws Exception {
		if (MyString.isEmpty(radio)) {
			radio = "true";
		}
		List<PickDto> picks = new ArrayList<PickDto>();
		String pickContent = menuService.pick(picks, radio, code, key, def, notNull);
		request.setAttribute("radio", radio);
		request.setAttribute("picks", picks);
		request.setAttribute("tag", tag);
		request.setAttribute("def", def);
		request.setAttribute("iCallBack", getParam("iCallBack", "voidFunction"));
		request.setAttribute("iCallBackParam", getParam("iCallBackParam", ""));
		request.setAttribute("tagName", tagName);
		request.setAttribute("pickContent", pickContent);
		return "WEB-INF/views/pick.jsp";
	}

	@RequestMapping("getImgCode.do")
	@ResponseBody
	public void getImgvcode() throws IOException {
		// 设置response，输出图片客户端不缓存
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		ValidateCodeService vservice = new ValidateCodeService();
		String uuid = MyCookie.getCookie(Const.COOKIE_UUID, false, request);
		cacheService.setStr(Const.CACHE_IMGCODE + uuid, vservice.getCode() , 10 * 60);
		cacheService.setStr(Const.CACHE_IMGCODE_TIMES + uuid, "0" , 10 * 60);
		try {
			vservice.write(out);
			out.flush();
		} finally {
			out.close();
		}
	}

	/**
	 * 
	 * @param 跳转至指定页面
	 * @return
	 */
	@RequestMapping("go.do")
	public String go(@RequestParam String p) {
		return p;
	}
}
