package cn.crap.controller.user;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dto.ArticleDto;
import cn.crap.dto.SearchDto;
import cn.crap.enu.*;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Article;
import cn.crap.model.ArticleWithBLOBs;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.query.ArticleQuery;
import cn.crap.query.CommentQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.CommentService;
import cn.crap.service.ISearchService;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.ServiceLoader;

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
    private BaseGenerateUtil baseGenerateUtils[];


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
            if (baseGenerateUtil.canHanle(type)) {
                return new JsonResult(1, baseGenerateUtil.hanle());
            }
        }
        return new JsonResult(1, "抱歉，暂不支持该类型的代码自动生成");
	}

}
