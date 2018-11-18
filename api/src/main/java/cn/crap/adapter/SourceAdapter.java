package cn.crap.adapter;

import cn.crap.dto.SearchDto;
import cn.crap.dto.SourceDto;
import cn.crap.enu.LuceneSearchType;
import cn.crap.enu.ProjectType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.Source;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.GetTextFromFile;
import cn.crap.utils.MyString;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class SourceAdapter {
    public static SourceDto getDto(Source model, Module module){
        if (model == null){
            return null;
        }

        SourceDto dto = new SourceDto();
        BeanUtil.copyProperties(model, dto);
        if (model.getCreateTime() != null) {
            dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        }
        if (model.getUpdateTime() != null) {
            dto.setUpdateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getUpdateTime().getTime()));
        }
		if (module != null) {
            dto.setModuleName(module.getName());
        }
        return dto;
    }

    public static Source getModel(SourceDto dto){
        if (dto == null){
            return null;
        }
        Source model = new Source();
        BeanUtil.copyProperties(dto, model);
        model.setCreateTime(null);
        model.setUpdateTime(null);
        return model;
    }

    public static List<SourceDto> getDto(List<Source> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<SourceDto> dtos = new ArrayList<>();
        for (Source model : models){
            try{
                dtos.add(getDto(model, null));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dtos;
    }

    public static List<SearchDto> getSearchDto(List<Source> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<SearchDto> dtos = new ArrayList<>();
        for (Source model : models){
            try{
                dtos.add(getSearchDto(model));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dtos;
    }

    public static SearchDto getSearchDto(Source source){
        SearchDto dto = new SearchDto();
        dto.setId(source.getId());
        dto.setCreateTime(source.getCreateTime());
        dto.setTitle(source.getName());
        dto.setType(Source.class.getSimpleName());
        dto.setUrl("#/source/detail?projectId=" +source.getProjectId()+ "&id=" + source.getId());
        dto.setVersion("");
        dto.setProjectId(source.getProjectId());
        //索引内容 = 备注内容 + 文档内容
        String docContent = "";
        try {
            docContent = GetTextFromFile.getText(source.getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dto.setContent(MyString.getStr(source.getRemark()) + docContent);
        //如果备注为空，则提取文档内容前2500 个字
        if( MyString.isEmpty(source.getRemark()) ){
            source.setRemark( docContent.length() > 2500? docContent.substring(0, 2500) +" ... \r\n..." : docContent);
        }
        ProjectCache projectCache = SpringContextHolder.getBean("projectCache", ProjectCache.class);
        Project project = projectCache.get(source.getProjectId());

        dto.setNeedCreateIndex(false);
        if(LuceneSearchType.Yes.getByteValue().equals(project.getLuceneSearch())){
            dto.setNeedCreateIndex(true);
        }
        // 私有项目不能建立索引
        if(project.getType() == ProjectType.PRIVATE.getType()){
            dto.setNeedCreateIndex(false);
        }
        return dto;
    }
}
