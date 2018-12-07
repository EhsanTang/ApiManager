package cn.crap.service;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.beans.Config;
import cn.crap.dao.custom.CustomInterfaceDao;
import cn.crap.dao.mybatis.InterfaceDao;
import cn.crap.dto.ErrorDto;
import cn.crap.dto.InterfacePDFDto;
import cn.crap.dto.ParamDto;
import cn.crap.dto.SearchDto;
import cn.crap.enu.LogType;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.*;
import cn.crap.query.InterfaceQuery;
import cn.crap.service.tool.ModuleCache;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import com.alibaba.fastjson.JSONArray;
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
            List<InterfaceWithBLOBs>  models = this.queryAll(new InterfaceQuery().setProjectId(model.getProjectId()).setPageSize(1));
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
    public List<InterfaceWithBLOBs> queryAll(InterfaceQuery query) throws MyException {
        Assert.notNull(query);
        InterfaceCriteria example = getInterfaceCriteria(query);
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return interfaceDao.selectByExampleWithBLOBs(example);
    }

    public List<Interface> query(InterfaceQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        InterfaceCriteria example = getInterfaceCriteria(query);
        if (page.getSize() != ALL_PAGE_SIZE) {
            example.setLimitStart(page.getStart());
            example.setMaxResults(page.getSize());
        }
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return interfaceDao.selectByExample(example);
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
     * @param handleText4Word 是否需要处理字符内容
     * @return
     */
    public InterfacePDFDto getInterPDFDto(InterfaceWithBLOBs interFace, Module module, boolean handleText4Word) {
        InterfacePDFDto interDto = new InterfacePDFDto();
        interDto.setModel(InterfaceAdapter.getDtoWithBLOBs(interFace, module, null, handleText4Word));


        if(interFace.getParam().startsWith("form=")){
            List<ParamDto> paramList = JSONArray.parseArray(interFace.getParam() == null ? "[]" : interFace.getParam().substring(5, interFace.getParam().length()), ParamDto.class);
            interDto.setFormParams(InterfaceAdapter.sortParam(null, paramList, null));
        }else{
            interDto.setCustom(true);
            interDto.setCustomParams( interFace.getParam());
        }
        interDto.setTrueMockUrl(Config.domain+"/mock/trueExam.do?id="+interFace.getId());
        interDto.setFalseMockUrl(Config.domain+"/mock/falseExam.do?id="+interFace.getId());

        List<ParamDto> headerList = JSONArray.parseArray(interFace.getHeader() == null ? "[]" : interFace.getHeader(), ParamDto.class);
        interDto.setHeaders(InterfaceAdapter.sortParam(null, headerList, null));

        List<ParamDto> resParamList = JSONArray.parseArray(interFace.getResponseParam() == null ? "[]" : interFace.getResponseParam(), ParamDto.class);
        interDto.setResponseParam(InterfaceAdapter.sortParam(null, resParamList, null));

        interDto.setErrors(JSONArray.parseArray(interFace.getErrors(), ErrorDto.class));
        return interDto;
    }

    /**
    public void getInterFaceRequestExam(InterfaceDto interFace) {
        Module module = moduleCache.get(interFace.getModuleId());
        interFace.setRequestExam("请求地址:"+ module.getUrl() + interFace.getUrl()+"\r\n");

        // 请求头
        List<ParamDto> headerList =JSONArray.toList(JSONArray.fromObject(
                interFace.getHeader() == null ? "[]" : interFace.getHeader()), new ParamDto(), new JsonConfig());
        InterfaceAdapter.sortParam(null, headerList, null);

        StringBuilder strHeaders = new StringBuilder("请求头:\r\n");
        ParamDto paramDto = null;
        for(int i=0;i<headerList.size();i++){
            paramDto = headerList.get(i);
            strHeaders.append("\t"+paramDto.getRealName() + "="+ paramDto.getDef() +"\r\n");
        }

        // 请求参数
        JSONObject obj = null;
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
    }**/


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
        super.delete(id);
        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);
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
        return InterfaceAdapter.getSearchDto(interfaceDao.selectByExampleWithBLOBs(new InterfaceCriteria()));
    }

    @Override
    public List<SearchDto> getAllByProjectId(String projectId) {
        InterfaceCriteria example = new InterfaceCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return  InterfaceAdapter.getSearchDto(interfaceDao.selectByExampleWithBLOBs(example));
    }
}
