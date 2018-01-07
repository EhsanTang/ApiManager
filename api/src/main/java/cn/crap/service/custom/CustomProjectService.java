package cn.crap.service.custom;

import cn.crap.adapter.Adapter;
import cn.crap.dao.mybatis.ProjectDao;
import cn.crap.dao.custom.CustomProjectDao;
import cn.crap.enumer.LogType;
import cn.crap.model.mybatis.*;
import cn.crap.service.mybatis.LogService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CustomProjectService {
    @Autowired
    private ProjectDao mapper;
    @Autowired
    private LogService logService;
    @Autowired
    private CustomProjectDao customMapper;
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
    public List<String> queryMyProjectIdByUserId(String userId) {
        Assert.notNull(userId, "userId can't be null");
        return customMapper.queryProjectIdByUserId(userId);
    }


//    public List<String> queryProjectIdByType(int type) {
//        Assert.notNull(type, "type can't be null");
//       return customMapper.queryMyProjectIdByType(type);
//    }

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

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.UPDATE, dbModel.getClass(), dbModel);
        logService.insert(log);

        mapper.updateByPrimaryKeySelective(model);
    }

    public void delete(String id, String modelName, String remark){
        Assert.notNull(id);
        Project dbModel = mapper.selectByPrimaryKey(id);
        if(MyString.isEmpty(remark)) {
            remark = dbModel.getName();
        }

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);

        mapper.deleteByPrimaryKey(dbModel.getId());
    }
}
