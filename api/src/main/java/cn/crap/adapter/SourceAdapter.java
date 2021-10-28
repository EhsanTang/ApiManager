package cn.crap.adapter;

import cn.crap.dto.SearchDto;
import cn.crap.dto.SourceDto;
import cn.crap.enu.LuceneSearchType;
import cn.crap.enu.ProjectType;
import cn.crap.enu.TableId;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.model.Source;
import cn.crap.utils.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class SourceAdapter {
    public static SourceDto getDto(Source model, ModulePO module){
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

    public static SearchDto getSearchDto(Source model){
        ProjectPO project = ServiceFactory.getInstance().getProjectCache().get(model.getProjectId());
        boolean open = false;
        if(LuceneSearchType.Yes.getByteValue().equals(project.getLuceneSearch())){
            open = true;
        }

        // 私有项目不能建立索引
        if(project.getType() == ProjectType.PRIVATE.getType()){
            open = false;
        }

        SearchDto searchDto = new SearchDto(model.getProjectId(), model.getModuleId(), model.getId(), model.getName(), TableId.SOURCE,
                model.getRemark(), model.getFilePath(), open, model.getCreateTime());

        //索引内容 = 备注内容 + 文档内容
        try {
            searchDto.setContent(MyString.getStr(model.getRemark()) + GetTextFromFile.getText(model.getFilePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchDto;
    }
}
