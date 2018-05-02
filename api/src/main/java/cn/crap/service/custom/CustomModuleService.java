package cn.crap.service.custom;

import cn.crap.dao.custom.CustomArticleDao;
import cn.crap.dao.mybatis.ModuleDao;
import cn.crap.model.mybatis.*;
import cn.crap.service.mybatis.ProjectService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomModuleService {
    @Autowired
    private ModuleDao dao;
    @Autowired
    private ProjectService projectService;

    /**
     * 根据模块id查询分类
     * @param moduleId
     * @return
     */
    public List<String> queryCategoryByModuleId(String moduleId){
        Assert.notNull(moduleId);
        Module module = dao.selectByPrimaryKey(moduleId);
        if (module == null || MyString.isEmpty(module.getCategory())){
            return new ArrayList<>();
        }
        List<String> categories = Arrays.asList(module.getCategory().split(","));
        categories.remove("");
        return categories;
    }
    public List<Module> queryByProjectId(String projectId){
        Assert.notNull(projectId, "projectId can't be null");
        ModuleCriteria example = new ModuleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
        return dao.selectByExample(example);
    }

    public int countByProjectId(String projectId){
        Assert.notNull(projectId, "projectId can't be null");
        ModuleCriteria example = new ModuleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        return dao.countByExample(example);
    }

    public Page<Module> queryByProjectId(String projectId, String name, Page<Module> page){
        Assert.notNull(projectId, "projectId can't be null");
        Assert.notNull(page, "page can't be null");
        ModuleCriteria example = new ModuleCriteria();
        ModuleCriteria.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        if (MyString.isNotEmpty(name)){
            criteria.andNameLike("%" + name +"%");
        }

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

        page.setAllRow(dao.countByExample(example));
        page.setList(dao.selectByExample(example));
        return page;
    }

    public List<String> getList(Byte status, String userId) {
        List<Byte> statuses = null;
        if(status != null){
            statuses= new ArrayList<Byte>();
            statuses.add(status);
        }
        return getListByStatuses(statuses, userId);
    }

    public List<String> getListByStatuses(List<Byte> statuses, String userId) {
        Assert.notNull(statuses);
        Assert.notNull(userId);

        Page page = new Page(1, 2000);// 最多显示钱2000条
        List<String> ids = new ArrayList<>();

        ModuleCriteria example = new ModuleCriteria();
        example.createCriteria().andStatusIn(statuses).andUserIdEqualTo(userId);

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

        List<Module> dcs = dao.selectByExample(example);
        for(Module dc:dcs){
            ids.add(dc.getId());
        }
        return ids;
    }
}
