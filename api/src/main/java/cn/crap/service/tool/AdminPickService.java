package cn.crap.service.tool;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.model.Article;
import cn.crap.model.ProjectPO;
import cn.crap.query.ProjectQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.IPickService;
import cn.crap.service.ModuleService;
import cn.crap.service.ProjectService;
import cn.crap.utils.IConst;
import cn.crap.utils.LoginUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 采用责任链模式
 * 下拉选择框
 *
 * @author Ehsan
 */
@Service("adminPickService")
public class AdminPickService implements IPickService{
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ModuleService moduleService;

    @Override
    public List<PickDto> getPickList(String code, String key) throws MyException {
        PickCode pickCode = PickCode.getByCode(code);
        if (pickCode == null) {
            throw new MyException(MyError.E000065, "code 有误");
        }

        LoginInfoDto user = LoginUserHelper.getUser();
        if (user.getType() != UserType.ADMIN.getType()) {
            throw new MyException(MyError.E000065, "权限不足，非管理员");
        }

        List<PickDto> picks = new ArrayList<>();
        PickDto pick = null;
        String preUrl = "";
        ProjectQuery recommendProjectQuery = new ProjectQuery().setStatus(ProjectStatus.RECOMMEND.getStatus()).setCurrentPage(1).setPageSize(50);
        switch (pickCode) {

            case AUTH:
                pick = new PickDto(IConst.C_SEPARATOR, "用户、菜单、系统设置管理");
                picks.add(pick);

                for (AdminPermissionEnum dataType : AdminPermissionEnum.values()) {
                    pick = new PickDto(dataType.name(), dataType.getName());
                    picks.add(pick);
                }

                return picks;
            case MODEL_NAME:
                pick = new PickDto("modelName_1", "文档", "文档");
                picks.add(pick);
                pick = new PickDto("modelName_2", "项目", "项目");
                picks.add(pick);
                pick = new PickDto("modelName_3", "模块", "模块");
                picks.add(pick);
                pick = new PickDto("modelName_4", "接口", "接口");
                picks.add(pick);
                pick = new PickDto("modelName_5", "数据库表", "数据库表");
                picks.add(pick);
                return picks;
            case INDEX_PAGE:// 首页
                for (IndexPageUrl indexPage : IndexPageUrl.values()) {
                    pick = new PickDto(indexPage.name(), indexPage.getValue(), indexPage.getName());
                    picks.add(pick);
                }

                pick = new PickDto(IConst.C_SEPARATOR, "项目主页【推荐项目】");
                picks.add(pick);

                for (ProjectPO project : projectService.select(recommendProjectQuery)) {
                    pick = new PickDto(project.getId(), String.format(IConst.FRONT_PROJECT_URL, project.getId()), project.getName());
                    picks.add(pick);
                }

                pick = new PickDto(IConst.C_SEPARATOR, "推荐文档、站点页面");
                picks.add(pick);

                pick = new PickDto("recommend_article", "index.do#/article/list?type=ARTICLE&status=" + ArticleStatus.RECOMMEND.getStatus(), "推荐文档列表");
                picks.add(pick);

                preUrl = "index.do#/NULL/article/detail/NULL/PAGE/";
                for (Article w : articleService.queryTop100Page()) {
                    pick = new PickDto("wp_" + w.getMkey(), preUrl + w.getMkey(), w.getName()+" [页面]");
                    picks.add(pick);
                }
                return picks;
            case MENU_URL:
                pick = new PickDto(IConst.C_SEPARATOR, "项目列表");
                picks.add(pick);
                pick = new PickDto("m_myproject", "index.do#/project/list?projectShowType=3", "我的项目列表");
                picks.add(pick);
                pick = new PickDto("recommend_project", "index.do#/project/list?projectShowType=4", "推荐项目列表");
                picks.add(pick);

                pick = new PickDto(IConst.C_SEPARATOR, "项目主页【推荐项目】");
                picks.add(pick);

                for (ProjectPO project : projectService.select(recommendProjectQuery)) {
                    pick = new PickDto(project.getId(), String.format(IConst.FRONT_PROJECT_URL, project.getId()), project.getName());
                    picks.add(pick);
                }

                pick = new PickDto(IConst.C_SEPARATOR, "推荐文档、站点页面");
                picks.add(pick);

                pick = new PickDto("recommend_article", "index.do#article/list?type=ARTICLE&status=" + ArticleStatus.RECOMMEND.getStatus(), "推荐文档列表");
                picks.add(pick);

                preUrl = "index.do#/NULL/article/detail/NULL/PAGE/";

                for (Article w : articleService.queryTop100Page()) {
                    pick = new PickDto("wp_" + w.getMkey(), preUrl + w.getMkey(), w.getName()+" [页面]");
                    picks.add(pick);
                }
                return picks;

            case USER_TYPE:
                for (UserType type : UserType.values()) {
                    pick = new PickDto("user-type" + type.getType(), type.getType() + "", type.getName());
                    picks.add(pick);
                }
                return picks;
            case ARTICLE_STATUS:
                for (ArticleStatus status : ArticleStatus.values()) {
                    pick = new PickDto("article-status" + status.getStatus(), status.getStatus() + "", status.getName());
                    picks.add(pick);
                }
                return picks;

        }

        throw new MyException(MyError.E000065, "查询失败");

    }

}
