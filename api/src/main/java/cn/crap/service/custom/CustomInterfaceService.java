package cn.crap.service.custom;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dao.mybatis.InterfaceDao;
import cn.crap.dao.custom.CustomInterfaceDao;
import cn.crap.dto.*;
import cn.crap.enumer.LogType;
import cn.crap.model.mybatis.*;
import cn.crap.service.ILuceneService;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.mybatis.LogService;
import cn.crap.service.mybatis.ModuleService;
import cn.crap.beans.Config;
import cn.crap.utils.MyString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CustomInterfaceService implements ILuceneService {
    @Autowired
    private InterfaceDao dao;
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

    /**
     *
     * @param interFace
     * @param module
     * @param handleText 是否需要处理字符内容
     * @return
     */
    public InterfacePDFDto getInterDto(InterfaceWithBLOBs interFace, Module module, boolean handleText) {
        InterfacePDFDto interDto = new InterfacePDFDto();
        interDto.setModel(InterfaceAdapter.getDto(interFace, module, handleText));
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


//	@Override
//	@Transactional
//	public JsonResult getInterfaceList(Page page,List<String> moduleIds, Interface interFace, Integer currentPage) {
//		Map<String, Object> params = Tools.getMap("moduleId", interFace.getModuleId(),
//				"interfaceName|like", interFace.getInterfaceName(),"fullUrl|like", interFace.getUrl()==null?"":interFace.getUrl().trim());
//		if(moduleIds != null){
//			moduleIds.add("NULL");// 防止长度为0，导致in查询报错
//			params.put("moduleId|in", moduleIds);
//		}
//
//		List<Interface> interfaces = findByMap(
//				params, " new Interface(id,moduleId,interfaceName,version,createTime,updateBy,updateTime,remark,sequence)", page, null);
//
//		List<Module> modules = new ArrayList<Module>();
//		// 搜索接口时，modules为空
//		if (interFace.getModuleId() != null && MyString.isEmpty(interFace.getInterfaceName()) && MyString.isEmpty(interFace.getUrl()) ) {
//			ModuleCriteria example = new ModuleCriteria();
//			example.createCriteria().andPa
//
//			params = Tools.getMap("parentId", interFace.getModuleId(), "type", "MODULE");
//			if(moduleIds != null){
//				moduleIds.add("NULL");// 防止长度为0，导致in查询报错
//				params.put("id|in", moduleIds);
//			}
//			params.put("id|!=", "top");// 顶级目录不显示
//
//			modules = moduleService.findByMap(params, null, null);
//		}
//		params.clear();
//		params.put("interfaces", interfaces);
//		params.put("modules", modules);
//		return new JsonResult(1, params, page,
//				Tools.getMap("crumbs", Tools.getCrumbs("接口列表:"+cacheService.getModuleName(interFace.getModuleId()),"void"),
//						"module",cacheService.getModule(interFace.getModuleId())));
//	}

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

    public List<InterfaceWithBLOBs> getByProjectId(String projectId) {
        InterfaceCriteria example = new InterfaceCriteria();
        InterfaceCriteria.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        return dao.selectByExampleWithBLOBs(example);
    }

    public int countByFullUrl(String moduleId, String fullUrl, String expectId){
        Assert.notNull(moduleId, "moduleId can't be null");
        Assert.notNull(fullUrl);

        InterfaceCriteria example = new InterfaceCriteria();
        InterfaceCriteria.Criteria criteria = example.createCriteria().andModuleIdEqualTo(moduleId).andFullUrlEqualTo(fullUrl);
        if (expectId != null){
            criteria.andIdNotEqualTo(expectId);
        }
        return dao.countByExample(example);
    }

    /**
     * update article and add update log
     * @param model
     * @param modelName
     * @param remark
     */
    public void update(InterfaceWithBLOBs model, String modelName, String remark) {
        InterfaceWithBLOBs dbModel = dao.selectByPrimaryKey(model.getId());
        if(MyString.isEmpty(remark)) {
            remark = model.getInterfaceName();
        }

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.UPDATE, dbModel.getClass(), dbModel);
        logService.insert(log);

        dao.updateByPrimaryKeySelective(model);
    }

    public void delete(String id, String modelName, String remark){
        Assert.notNull(id);
        InterfaceWithBLOBs dbModel = dao.selectByPrimaryKey(id);
        if(MyString.isEmpty(remark)) {
            remark = dbModel.getInterfaceName();
        }

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);

        dao.deleteByPrimaryKey(dbModel.getId());
    }

    public List<InterfaceWithBLOBs> selectByModuleId(String moduleId){
        Assert.notNull(moduleId);
        InterfaceCriteria example = new InterfaceCriteria();
        example.createCriteria().andModuleIdEqualTo(moduleId);

        return dao.selectByExampleWithBLOBs(example);
    }

    public int countByModuleId(String moduleId){
        Assert.notNull(moduleId);
        InterfaceCriteria example = new InterfaceCriteria();
        example.createCriteria().andModuleIdEqualTo(moduleId);

        return dao.countByExample(example);
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
        return InterfaceAdapter.getSearchDto(InterfaceAdapter.getDto(dao.selectByExampleWithBLOBs(new InterfaceCriteria()), null));
    }

    @Override
    public List<SearchDto> getAllByProjectId(String projectId) {
        InterfaceCriteria example = new InterfaceCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return  InterfaceAdapter.getSearchDto(InterfaceAdapter.getDto(dao.selectByExampleWithBLOBs(example), null));
    }
}
