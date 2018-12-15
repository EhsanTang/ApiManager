package cn.crap.controller;

import cn.crap.enu.SettingEnum;
import cn.crap.framework.ThreadContext;
import cn.crap.framework.base.BaseController;
import cn.crap.service.MenuService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前后台共用的Controller
 * @author Ehsan
 *
 */
@Controller
public class IndexController extends BaseController {
	@Autowired
	MenuService customMenuService;
	
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
		String pickContent = customMenuService.pick(radio, code, key, def, notNull);
		HttpServletRequest request = ThreadContext.request();
		request.setAttribute("radio", radio);
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
		HttpServletResponse response = ThreadContext.response();
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();

		String uuid = MyCookie.getCookie(IConst.COOKIE_UUID);
			ImageCode imageCode = new ImageCode(settingCache.get(SettingEnum.IMAGE_CODE.getKey()).getValue());
			stringCache.add(IConst.CACHE_IMGCODE + uuid, imageCode.getCode());
			stringCache.add(IConst.CACHE_IMGCODE_TIMES + uuid, "0");
			try {
				imageCode.write(out);
				out.flush();
			} catch (Exception e){
			    e.printStackTrace();
			} finally {
			    if (out != null){
			        try {
                        out.close();
                    }catch (Exception e) {}
                }
			}
		}


	/**
	 * 
	 * @param p 跳转至指定页面
	 * @return
	 */
	@RequestMapping("go.do")
	public String go(@RequestParam String p) {
		return p;
	}
}
