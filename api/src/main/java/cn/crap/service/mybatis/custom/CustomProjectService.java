package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.ArticleMapper;
import cn.crap.dao.mybatis.ProjectMapper;
import cn.crap.dao.mybatis.custom.CustomArticleMapper;
import cn.crap.dao.mybatis.custom.CustomProjectMapper;
import cn.crap.enumeration.LogType;
import cn.crap.model.Module;
import cn.crap.model.mybatis.*;
import cn.crap.service.imp.tool.CacheService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import cn.crap.utils.Tools;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomProjectService {
    @Autowired
    private ProjectMapper mapper;
    @Autowired
    private CustomProjectMapper customMapper;
    public List<Project> queryMyProjectByUserId(String userId){
        Assert.notNull(userId, "userId can't be null");
        ProjectCriteria example = new ProjectCriteria();
        ProjectCriteria.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        return mapper.selectByExample(example);
    }

    public List<Project> pageProjectByStatusName(Byte status, String name, Page page){
        Assert.notNull(status, "status can't be null");
        ProjectCriteria example = new ProjectCriteria();
        ProjectCriteria.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        if (name != null){
            criteria.andNameLike('%' + name +"%");
        }

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
        return mapper.selectByExample(example);
    }

    public int countProjectByStatusName(Byte status, String name){
        Assert.notNull(status, "status can't be null");
        ProjectCriteria example = new ProjectCriteria();
        ProjectCriteria.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        if (name != null){
            criteria.andNameLike('%' + name +"%");
        }
        return mapper.countByExample(example);
    }

    /**
     * 根据用户ID查询所有该用户加入的项目、创建的项目
     */
    public List<String> queryProjectIdByUid(String userId) {
        Assert.notNull(userId, "userId can't be null");
        return customMapper.queryProjectIdByUserId(userId);
    }


    public List<String> queryProjectIdByType(int type) {
        Assert.notNull(type, "type can't be null");
       return customMapper.queryMyProjectIdByType(type);
    }

    public List<Project> pageProjectByUserIdName(String userId, String name, Page page){
        Assert.notNull(userId, "userId can't be null");
        return customMapper.queryProjectByUserId(userId, name, page);
    }

    public int countProjectByUserIdName(String userId, String name){
        Assert.notNull(userId, "userId can't be null");
        return customMapper.countProjectByUserId(userId, name);
    }

    /**
     * update project and add update log
     * @param model
     * @param modelName
     * @param remark
     */
    public void update(Project model, String modelName, String remark) {
        Project dbModel = mapper.selectByPrimaryKey(model.getId());
        if(MyString.isEmpty(remark)) {
            remark = model.getName();
        }
//        Log log = new Log(modelName, remark, LogType.UPDATE.name(), JSONObject.fromObject(dbModel).toString(),
//                model.getClass().getSimpleName(), model.getId());
//        // logDao.save(log); TODO
        mapper.updateByPrimaryKey(model);
    }

    public void delete(String id, String modelName, String remark){
        Assert.notNull(id);
        Project dbModel = mapper.selectByPrimaryKey(id);
        if(MyString.isEmpty(remark)) {
            remark = dbModel.getName();
        }
//        Log log = new Log(modelName, remark, LogType.DELTET.name(), JSONObject.fromObject(dbModel).toString(),
//                dbModel.getClass().getSimpleName(), dbModel.getId());
        // logDao.save(log);
        mapper.deleteByPrimaryKey(dbModel.getId());
    }
}
