package cn.crap.service;

import cn.crap.adapter.Adapter;
import cn.crap.dao.mybatis.ModuleDao;
import cn.crap.enu.*;
import cn.crap.framework.IdGenerator;
import cn.crap.framework.MyException;
import cn.crap.model.Log;
import cn.crap.model.ModulePO;
import cn.crap.query.*;
import cn.crap.utils.IConst;
import cn.crap.utils.ILogConst;
import cn.crap.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ModuleService extends NewBaseService<ModulePO, ModuleQuery>  implements ILogConst, IConst{
    @Autowired
    private LogService logService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private BugService bugService;
    @Autowired
    private ArticleService articleService;

    private ModuleDao moduleDao;
    @Resource
    public void ModuleDao(ModuleDao moduleDao) {
        this.moduleDao = moduleDao;
        super.setBaseDao(moduleDao, TableId.MODULE);
    }

    public ModulePO getByUniKey(String projectId, String uniKey) throws Exception{
        Assert.notNull(projectId, "projectId");
        Assert.notNull(uniKey, "uniKey");

        List<ModulePO> modulePOS = super.select(new ModuleQuery().setProjectId(projectId).setUniKey(uniKey));
       return CollectionUtils.isEmpty(modulePOS) ? null : modulePOS.get(0);
    }

    public boolean insert(ModulePO po) throws MyException{
        Assert.notNull(po, "ModuleService insert po is null");

        if (po.getUniKey() == null){
            po.setUniKey(IdGenerator.getId(TableId.MODULE));
        }

        if (po.getVersionNum() == null){
            po.setVersionNum(0);
        }

        if (po.getUrl() == null){
            po.setUrl("");
        }

        if (po.getCanDelete() == null){
            po.setCanDelete(CanDeleteEnum.CAN.getCanDelete());
        }

        if (po.getCategory() == null){
            po.setCategory("");
        }

        if (po.getStatus() == null){
            po.setStatus(Byte.valueOf("1"));
        }

        return super.insert(po);
    }

    /**
     * 更新模块
     * @param model
     * @param needAddLog 是否需要添加日志
     * @return
     */
    public boolean update(ModulePO model, boolean needAddLog) throws MyException{
        if (model == null) {
            return false;
        }
        if (needAddLog) {
            ModulePO dbModule = get(model.getId());
            Log log = Adapter.getLog(dbModule.getId(), L_MODULE_CHINESE, dbModule.getName(), LogType.UPDATE, dbModule.getClass(), dbModule);
            logService.insert(log);
        }

        model.setProjectId(null);
        if (model.getUrl() == null){
            model.setUrl("");
        }
        return super.update(model);
    }

    public boolean delete(String id) throws MyException{
        Assert.notNull(id, "id can't be null");

        if(interfaceService.count(new InterfaceQuery().setModuleId(id)) >0 ){
            throw new MyException(MyError.E000024);
        }

        if(articleService.count(new ArticleQuery().setModuleId(id).setType(ArticleType.ARTICLE.name())) >0 ){
            throw new MyException(MyError.E000034);
        }

        if(sourceService.count(new SourceQuery().setModuleId(id)) >0 ){
            throw new MyException(MyError.E000035);
        }

        if(articleService.count(new ArticleQuery().setModuleId(id).setType(ArticleType.DICTIONARY.name())) >0 ){
            throw new MyException(MyError.E000036);
        }

        if(bugService.count(new BugQuery().setModuleId(id)) >0 ){
            throw new MyException(MyError.E000076);
        }

        ModulePO dbModule = get(id);
        Log log = Adapter.getLog(dbModule.getId(), L_MODULE_CHINESE, dbModule.getName(), LogType.DELTET, dbModule.getClass(), dbModule);
        logService.insert(log);

        return super.delete(id);
    }

    /**
     * 根据模块id查询分类
     * @param moduleId
     * @return
     */
    public List<String> queryCategoryByModuleId(String moduleId){
        Assert.notNull(moduleId);
        ModulePO module = super.get(moduleId);
        if (module == null || MyString.isEmpty(module.getCategory())){
            return new ArrayList<>();
        }
        List<String> categories = Arrays.asList(module.getCategory().split(","));
        categories.remove("");
        return categories;
    }
}
