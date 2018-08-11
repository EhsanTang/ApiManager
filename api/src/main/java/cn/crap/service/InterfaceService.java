package cn.crap.service;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.beans.Config;
import cn.crap.dao.custom.CustomInterfaceDao;
import cn.crap.dao.mybatis.InterfaceDao;
import cn.crap.dto.*;
import cn.crap.enumer.LogType;
import cn.crap.enumer.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.InterfaceCriteria;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.Log;
import cn.crap.model.Module;
import cn.crap.query.InterfaceQuery;
import cn.crap.service.tool.ModuleCache;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class InterfaceService extends BaseService<InterfaceWithBLOBs, InterfaceDao> implements ILuceneService, IConst {
    private InterfaceDao interfaceDao;
    @Autowired
    private ModuleCache moduleCache;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private LogService logService;
    @Autowired
    private CustomInterfaceDao customInterfaceMapper;
    @Autowired
    private Config config;

    @Resource
    public void InterfaceDao(InterfaceDao interfaceDao) {
        this.interfaceDao = interfaceDao;
        super.setBaseDao(interfaceDao, TableId.INTERFACE);
    }

    @Override
    public boolean insert(InterfaceWithBLOBs model) throws MyException{
        if (model == null) {
            return false;
        }
        if (model.getSequence() == null){
            List<InterfaceWithBLOBs>  models = this.query(new InterfaceQuery().setProjectId(model.getProjectId()).setPageSize(1));
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setUpdateTime(new Date());
        return super.insert(model);
    }


    /**
     * 查询项目
     * @param query
     * @return
     * @throws MyException
     */
    public List<InterfaceWithBLOBs> query(InterfaceQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        InterfaceCriteria example = getInterfaceCriteria(query);
        if (page.getSize() != ALL_PAGE_SIZE) {
            example.setLimitStart(page.getStart());
            example.setMaxResults(page.getSize());
        }
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return interfaceDao.selectByExampleWithBLOBs(example);
    }

    /**
     * 查询项目数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(InterfaceQuery query) throws MyException {
        Assert.notNull(query);

        InterfaceCriteria example = getInterfaceCriteria(query);
        return interfaceDao.countByExample(example);
    }

    private InterfaceCriteria getInterfaceCriteria(InterfaceQuery query) throws MyException {
        InterfaceCriteria example = new InterfaceCriteria();
        InterfaceCriteria.Criteria criteria = example.createCriteria();
        if (query.getStatus() != null) {
            criteria.andStatusEqualTo(query.getStatus());
        }
        if (query.getInterfaceName() != null) {
            criteria.andInterfaceNameLike("%" + query.getInterfaceName() + "%");
        }
        if (query.getEqualInterfaceName() != null) {
            criteria.andInterfaceNameEqualTo(query.getEqualInterfaceName());
        }
        if (query.getModuleId() != null) {
            criteria.andModuleIdEqualTo(query.getModuleId());
        }
        if (query.getProjectId() != null) {
            criteria.andProjectIdEqualTo(query.getProjectId());
        }
        if (query.getEqualFullUrl() != null) {
            criteria.andFullUrlEqualTo(query.getEqualFullUrl());
        }
        if (query.getFullUrl() != null) {
            criteria.andFullUrlLike("%" + query.getFullUrl() + "%");
        }
        if (query.getExceptId() != null) {
            criteria.andIdNotEqualTo(query.getExceptId());
        }
        if (query.getExceptVersion() != null) {
            criteria.andVersionNotEqualTo(query.getExceptVersion());
        }
        return example;
    }
    /**
     *
     * @param interFace
     * @param module
     * @param handleText 是否需要处理字符内容
     * @return
     */
    public InterfacePDFDto getInterDto(InterfaceWithBLOBs interFace, Module module, boolean handleText) {
        InterfacePDFDto interDto = new InterfacePDFDto();
        interDto.setModel(InterfaceAdapter.getDto(interFace, module, null, handleText));
        if(interFace.getParam().startsWith("form=")){
            interDto.setFormParams(JSONArray.toList(JSONArray.fromObject(interFace.getParam().substring(5)), new ParamDto(), new JsonConfig()));
        }else{
            interDto.setCustom(true);
            interDto.setCustomParams( interFace.getParam());
        }
        interDto.setTrueMockUrl(config.getDomain()+"/mock/trueExam.do?id="+interFace.getId());
        interDto.setFalseMockUrl(config.getDomain()+"/mock/falseExam.do?id="+interFace.getId());

        interDto.setHeaders(JSONArray.toList( JSONArray.fromObject(interFace.getHeader()), new ParamDto(), new JsonConfig()));
        interDto.setResponseParam(JSONArray.toList( JSONArray.fromObject(interFace.getResponseParam()),new ResponseParamDto(), new JsonConfig()));
        interDto.setParamRemarks(JSONArray.toList( JSONArray.fromObject(interFace.getParamRemark()), new ResponseParamDto(), new JsonConfig()));
        interDto.setErrors(JSONArray.toList( JSONArray.fromObject(interFace.getErrors()),new ErrorDto(), new JsonConfig()));
        return interDto;
    }


    public void getInterFaceRequestExam(InterfaceDto interFace) {
        Module module = moduleCache.get(interFace.getModuleId());
        interFace.setRequestExam("请求地址:"+ module.getUrl() + interFace.getUrl()+"\r\n");

        // 请求头
        JSONArray headers = JSONArray.fromObject(interFace.getHeader());
        StringBuilder strHeaders = new StringBuilder("请求头:\r\n");
        JSONObject obj = null;
        for(int i=0;i<headers.size();i++){
            obj = (JSONObject) headers.get(i);
            strHeaders.append("\t"+obj.getString("name") + "="+ (obj.containsKey("def")?obj.getString("def"):"")+"\r\n");
        }

        // 请求参数
        StringBuilder strParams = new StringBuilder("请求参数:\r\n");
        if(!MyString.isEmpty(interFace.getParam())){
            JSONArray params = null;
            if(interFace.getParam().startsWith("form=")){
                params = JSONArray.fromObject(interFace.getParam().substring(5));
                for(int i=0;i<params.size();i++){
                    obj = (JSONObject) params.get(i);
                    if(obj.containsKey("inUrl") && obj.getString("inUrl").equals("true")){
                        interFace.setRequestExam(interFace.getRequestExam().replace("{"+obj.getString("name")+"}", (obj.containsKey("def")?obj.getString("def"):"")));
                    }else{
                        strParams.append("\t"+obj.getString("name") + "=" + (obj.containsKey("def")?obj.getString("def"):"")+"\r\n");
                    }
                }
            }else{
                strParams.append(interFace.getParam());
            }
        }
        interFace.setRequestExam(interFace.getRequestExam()+strHeaders.toString()+strParams.toString());
    }


    public String getLuceneType() {
        return "接口";
    }

    /**
     * update article and add update log
     * @param model
     * @param modelName
     * @param remark
     */
    public void update(InterfaceWithBLOBs model, String modelName, String remark) throws MyException{
        InterfaceWithBLOBs dbModel = interfaceDao.selectByPrimaryKey(model.getId());
        if(MyString.isEmpty(remark)) {
            remark = model.getInterfaceName();
        }

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.UPDATE, dbModel.getClass(), dbModel);
        logService.insert(log);

        super.update(model);
    }

    public void delete(String id, String modelName, String remark) throws MyException{
        Assert.notNull(id);
        InterfaceWithBLOBs dbModel = interfaceDao.selectByPrimaryKey(id);
        if(MyString.isEmpty(remark)) {
            remark = dbModel.getInterfaceName();
        }

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);

        super.delete(id);
    }


    /**
     * 根据模块下的所有fullUrl
     * @param moduleUrl
     * @param moduleId
     */
    public void updateFullUrlByModuleId(String moduleUrl, String moduleId){
        Assert.notNull(moduleId);
        if (MyString.isEmpty(moduleUrl)){
            moduleUrl = "";
        }
        customInterfaceMapper.updateFullUrlByModuleId(moduleUrl, moduleId);
        return;
    }

    public void  deleteTemplateByModuleId(String moduleId){
        Assert.notNull(moduleId);
        customInterfaceMapper.deleteTemplateByModuleId(moduleId);

    }

    public List<SearchDto> getAll() {
        return InterfaceAdapter.getSearchDto(InterfaceAdapter.getDto(interfaceDao.selectByExampleWithBLOBs(new InterfaceCriteria()), null, null));
    }

    @Override
    public List<SearchDto> getAllByProjectId(String projectId) {
        InterfaceCriteria example = new InterfaceCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return  InterfaceAdapter.getSearchDto(InterfaceAdapter.getDto(interfaceDao.selectByExampleWithBLOBs(example), null, null));
    }
}
