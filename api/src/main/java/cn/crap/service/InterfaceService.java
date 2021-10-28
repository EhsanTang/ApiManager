package cn.crap.service;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dao.custom.CustomInterfaceDao;
import cn.crap.dao.mybatis.InterfaceDao;
import cn.crap.dto.*;
import cn.crap.enu.LogType;
import cn.crap.enu.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.framework.MyException;
import cn.crap.model.*;
import cn.crap.query.InterfaceQuery;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.SettingCache;
import cn.crap.utils.*;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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
    private SettingCache settingCache;
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

        // TODO 提取到公共内
        if (model.getVersionNum() == null){
            model.setVersionNum(0);
        }

        if (model.getUniKey() == null){
            model.setUniKey(IdGenerator.getId(TableId.INTERFACE));
        }

        if (model.getIsTemplate() == null){
            model.setIsTemplate(false);
        }
        if(model.getParam() == null){
            model.setParam("");
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
        Assert.isTrue(MyString.isNotEmptyOrNUll(query.getProjectId())
                || MyString.isNotEmptyOrNUll(query.getModuleId()), "projectId、moduleId不能同时为空");

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
        Assert.isTrue(MyString.isNotEmptyOrNUll(query.getProjectId())
                || MyString.isNotEmptyOrNUll(query.getModuleId()), "projectId、moduleId不能同时为空");

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
        if (query.getModuleId() != null ) {
            if (IConst.NULL.equals(query.getModuleId())){
                criteria.andModuleIdEqualTo("");
            } else {
                criteria.andModuleIdEqualTo(query.getModuleId());
            }
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
        if (query.getUniKey() != null) {
            criteria.andUniKeyEqualTo(query.getUniKey());
        }

        return example;
    }
    /**
     *
     * @param interFace
     * @param module
     * @param escape 是否需要处理字符内容
     * @return
     */
    public InterfacePDFDto getInterPDFDto(InterfaceWithBLOBs interFace, ModulePO module, boolean escape, boolean isPdf) {
        InterfacePDFDto interDto = new InterfacePDFDto();
        interDto.setModel(InterfaceAdapter.getDtoWithBLOBs(interFace, module, null, escape));

        if(interFace.getParam() != null && interFace.getParam().startsWith("form=")){
            List<ParamDto> paramList = JSONArray.parseArray(interFace.getParam() == null ? "[]" : interFace.getParam().substring(5, interFace.getParam().length()), ParamDto.class);
            interDto.setFormParams(InterfaceAdapter.sortParam(null, paramList, null));
        }else{
            interDto.setCustom(true);
            interDto.setCustomParams(interDto.getModel().getParam());
        }

        // 如果是pdf，需要将 <w:br/> 转成<br/>
        if (escape && isPdf){
            InterfaceDto interfaceDTO = interDto.getModel();
            interfaceDTO.setFalseExam(interfaceDTO.getFalseExam().replaceAll("<w:br/>", "<br/>"));
            interfaceDTO.setTrueExam(interfaceDTO.getTrueExam().replaceAll("<w:br/>", "<br/>"));
            interfaceDTO.setParam(interfaceDTO.getParam().replaceAll("<w:br/>", "<br/>"));
            interfaceDTO.setRemark(interfaceDTO.getRemark().replaceAll("<w:br/>", "<br/>"));
        }

        interDto.setTrueMockUrl(Tools.getUrlPath()+"/mock/trueExam.do?id="+interFace.getId());
        interDto.setFalseMockUrl(Tools.getUrlPath()+"/mock/falseExam.do?id="+interFace.getId());
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
        model.setVersionNum(dbModel.getVersionNum() + 1);
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

    public void  deleteByModuleId(String moduleId){
        Assert.notNull(moduleId);
        customInterfaceMapper.deleteByModuleId(moduleId);
    }

    public void  deleteByModuleId(String moduleId, List<String> uniKeyList) throws Exception{
        Assert.notNull(moduleId);
        customInterfaceMapper.deleteByModuleId(moduleId, uniKeyList);
    }

    @Override
    public List<SearchDto> selectOrderById(String projectId, String id, int pageSize){
        Assert.isTrue(pageSize > 0 && pageSize <= 1000);
        InterfaceCriteria example = new InterfaceCriteria();
        InterfaceCriteria.Criteria criteria = example.createCriteria();
        if (projectId != null){
            criteria.andProjectIdEqualTo(projectId);
        }
        example.setMaxResults(pageSize);
        if (id != null){
            criteria.andIdGreaterThan(id);
        }
        example.setOrderByClause(TableField.SORT.ID_ASC);
        return InterfaceAdapter.getSearchDto(interfaceDao.selectByExampleWithBLOBs(example));
    }
}
