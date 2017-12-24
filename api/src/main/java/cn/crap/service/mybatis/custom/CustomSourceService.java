package cn.crap.service.mybatis.custom;

import cn.crap.adapter.SourceAdapter;
import cn.crap.dao.mybatis.SourceMapper;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.LogType;
import cn.crap.model.mybatis.Log;
import cn.crap.model.mybatis.Source;
import cn.crap.model.mybatis.SourceCriteria;
import cn.crap.service.ILuceneService;
import cn.crap.service.mybatis.imp.MybatisLogService;
import cn.crap.service.mybatis.imp.MybatisProjectService;
import cn.crap.springbeans.Config;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

// TODO 重新生成所有的ID，保证ID有序
@Service
public class CustomSourceService implements ILuceneService{
    @Autowired
    private SourceMapper sourceMapper;
    @Autowired
    private Config config;
    @Autowired
    private MybatisProjectService projectService;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private MybatisLogService logService;

    public Page<Source> queryByModuleId(String moduleId, String name, Page<Source> page){
        Assert.notNull(moduleId, "moduleId can't be null");
        Assert.notNull(page, "page can't be null");
        SourceCriteria example = new SourceCriteria();
        SourceCriteria.Criteria criteria = example.createCriteria().andModuleIdEqualTo(moduleId);

        if (MyString.isNotEmpty(name)){
            criteria.andNameLike('%' + name + '%');
        }

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

        page.setAllRow(sourceMapper.countByExample(example));

        page.setList(sourceMapper.selectByExample(example));
        return page;
    }

    public Source get(String id){
            Source model = sourceMapper.selectByPrimaryKey(id);
            if(model == null)
                return new Source();
            return model;
        }

        public List<SearchDto> getAll() {
            SourceCriteria example = new SourceCriteria();
            return SourceAdapter.getSearchDto(sourceMapper.selectByExample(example));
        }

        public List<SearchDto> getAllByProjectId(String projectId) {
            SourceCriteria example = new SourceCriteria();
            example.createCriteria().andProjectIdEqualTo(projectId);
            return SourceAdapter.getSearchDto(sourceMapper.selectByExample(example));
        }

        public String getLuceneType() {
            return "资源";
        }

    public void update(Source model, String modelName, String remark) {
        Source dbModel = sourceMapper.selectByPrimaryKey(model.getId());
        if(MyString.isEmpty(remark)) {
            remark = model.getName();
        }
    // TODO 提取代码
        Log log = new Log();
        log.setModelName(modelName);
        log.setRemark(remark);
        log.setType(LogType.UPDATE.name());
        log.setContent(JSONObject.fromObject(dbModel).toString());
        log.setModelClass(dbModel.getClass().getSimpleName());

        logService.insert(log);
        sourceMapper.updateByPrimaryKey(model);
    }

    public void delete(String id, String modelName, String remark){
        Assert.notNull(id);
        Source dbModel = sourceMapper.selectByPrimaryKey(id);
        if(MyString.isEmpty(remark)) {
            remark = dbModel.getName();
        }
        Log log = new Log();
        log.setModelName(modelName);
        log.setRemark(remark);
        log.setType(LogType.DELTET.name());
        log.setContent(JSONObject.fromObject(dbModel).toString());
        log.setModelClass(dbModel.getClass().getSimpleName());

        logService.insert(log);
        sourceMapper.deleteByPrimaryKey(dbModel.getId());
    }

    public int countByModuleId(String moduleId){
        Assert.notNull(moduleId);
        SourceCriteria example = new SourceCriteria();
        example.createCriteria().andModuleIdEqualTo(moduleId);

        return sourceMapper.countByExample(example);
    }

}
