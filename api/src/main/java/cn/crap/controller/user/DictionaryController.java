package cn.crap.controller.user;

import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.service.ArticleService;
import cn.crap.service.CommentService;
import cn.crap.service.ISearchService;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.generate.BaseGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user/dictionary")
public class DictionaryController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ProjectCache projectCache;
	@Autowired
	private ModuleCache moduleCache;
	@Autowired
    private List<BaseGenerateUtil> baseGenerateUtils;


	/**
	 * 代码生成
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/generateCode.do")
	@ResponseBody
	public JsonResult addOrUpdate(String fieldNames, String type) throws Exception {
        for(BaseGenerateUtil baseGenerateUtil: baseGenerateUtils){
            if (baseGenerateUtil.canExecute(type)) {
                return new JsonResult(1, baseGenerateUtil.execute(fieldNames));
            }
        }
        return new JsonResult(1, "抱歉，暂不支持该类型的代码自动生成");
	}

}
