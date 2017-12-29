package cn.crap.service.custom;

import cn.crap.dao.mybatis.ModuleDao;
import cn.crap.dto.PickDto;
import cn.crap.model.mybatis.*;
import cn.crap.service.imp.MybatisProjectService;
import cn.crap.springbeans.Config;
import cn.crap.springbeans.PickFactory;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomModuleService {
    @Autowired
    private ModuleDao mapper;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private MybatisProjectService projectService;
    @Autowired
    private PickFactory pickFactory;
    @Autowired
    private Config config;

    public List<Module> queryByProjectId(String projectId){
        Assert.notNull(projectId, "projectId can't be null");
        ModuleCriteria example = new ModuleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        return mapper.selectByExample(example);
    }

    public int countByProjectId(String projectId){
        Assert.notNull(projectId, "projectId can't be null");
        ModuleCriteria example = new ModuleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        return mapper.countByExample(example);
    }

    public Page<Module> queryByProjectId(String projectId, Page<Module> page){
        Assert.notNull(projectId, "projectId can't be null");
        Assert.notNull(page, "page can't be null");
        ModuleCriteria example = new ModuleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

        page.setAllRow(mapper.countByExample(example));
        page.setList(mapper.selectByExample(example));
        return page;
    }

    public void getDataCenterPick(List<PickDto> picks,List<String> projectIds, String idPre, String value,String suffix){
        if(MyString.isEmpty(suffix))
            suffix = "";
        PickDto pick = null;
        for (String projectId : projectIds) {
            List<Module> dcs = queryByProjectId(projectId);
            if(dcs.size()>0){
                Project project = projectService.selectByPrimaryKey(projectId);
                pick = new PickDto(Const.SEPARATOR, project == null ? "" : project.getName());
                picks.add(pick);
            }

            for(Module dc : dcs){
                if(MyString.isEmpty(value))
                    pick = new PickDto(idPre+dc.getId(), Const.LEVEL_PRE+dc.getName()+suffix);
                else
                    pick = new PickDto(idPre+dc.getId(), value.replace("moduleId", dc.getId()).replace("moduleName", dc.getName()).replace("projectId", projectId), Const.LEVEL_PRE+dc.getName()+suffix);
                picks.add(pick);
            }
        }
    }

    public List<String> getList(Byte status, String userId) {
        List<Byte> statuss = null;
        if(status != null){
            statuss= new ArrayList<Byte>();
            statuss.add(status);
        }
        return getListByStatuss(statuss, userId);
    }

    public List<String> getListByStatuss(List<Byte> statuss, String userId) {
        Assert.notNull(statuss);
        Assert.notNull(userId);

        Page page = new Page(1, 2000);// 最多显示钱2000条
        List<String> ids = new ArrayList<>();

        ModuleCriteria example = new ModuleCriteria();
        example.createCriteria().andStatusIn(statuss).andUserIdEqualTo(userId);

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

        List<Module> dcs = mapper.selectByExample(example);
        for(Module dc:dcs){
            ids.add(dc.getId());
        }
        return ids;
    }
}
