package cn.crap.adapter;

import cn.crap.dto.BugDTO;
import cn.crap.dto.SearchDto;
import cn.crap.enu.*;
import cn.crap.model.BugPO;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.ServiceFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class BugAdapter {
    public static BugDTO getDto(BugPO model, ModulePO module, ProjectPO project){
        if (model == null){
            return null;
        }

        BugDTO dto = new BugDTO();
        BeanUtil.copyProperties(model, dto);
        dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime()));
        dto.setUpdateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getUpdateTime()));
        dto.setStatusStr(BugStatus.getNameByValue(model.getStatus()));
        dto.setPriorityStr(BugPriority.getNameByValue(model.getPriority()));
        dto.setSeverityStr(BugSeverity.getNameByValue(model.getSeverity()));
        dto.setTypeStr(BugType.getNameByValue(model.getType()));
        dto.setModuleName(Optional.ofNullable(module).map(m -> m.getName()).orElse("无"));
        dto.setProjectName(Optional.ofNullable(project).map(p -> p.getName()).orElse("无"));
        dto.setExecutorStr(Optional.ofNullable(dto.getExecutorStr()).orElse("无"));
        dto.setTesterStr(Optional.ofNullable(dto.getTesterStr()).orElse("无"));
        dto.setTracerStr(Optional.ofNullable(dto.getTracerStr()).orElse("无"));

        return dto;
    }

    public static BugPO getPO(BugDTO bugDTO){
        if (bugDTO == null){
            return null;
        }

        BugPO bugPO = new BugPO();
        BeanUtil.copyProperties(bugDTO, bugPO);
        return bugPO;
    }


    public static BugDTO getDTO(ProjectPO project, ModulePO module){
        Assert.notNull(project, "project 不能为空");
        BugPO bugPO = new BugPO();
        bugPO.setType(BugType.FUNCTION.getByteValue());
        bugPO.setPriority(BugPriority.MIDDLE.getByteValue());
        bugPO.setSeverity(BugSeverity.MAJOR.getByteValue());
        bugPO.setStatus(BugStatus.NEW.getByteValue());
        bugPO.setProjectId(project.getId());
        bugPO.setModuleId(Optional.ofNullable(module).map(m -> m.getId()).orElse(null));
        bugPO.setSequence(System.currentTimeMillis());

        BugDTO bugDTO = getDto(bugPO, module, project);
        bugDTO.setName("");
        bugDTO.setContent("<p>[缺陷描述]：<br>[重现步骤]：<br>[期望结果]：<br>[原因定位]：<br>[建议修改]：<br></p>");
        return bugDTO;
    }

    public static BugPO getDTO(BugDTO dto){
        if (dto == null){
            return null;
        }
        BugPO model = new BugPO();
        BeanUtil.copyProperties(dto, model);

        return model;
    }

    public static List<BugDTO> getDto(List<BugPO> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<BugDTO> dtos = new ArrayList<>();
        for (BugPO model : models){
            dtos.add(getDto(model, null, null));
        }
        return dtos;
    }

    public static List<SearchDto> getSearchDto(List<BugPO> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<SearchDto> dtos = new ArrayList<>();
        for (BugPO model : models){
            try {
                dtos.add(getSearchDto(model));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dtos;
    }

    public static SearchDto getSearchDto(BugPO model){
        ProjectPO project = ServiceFactory.getInstance().getProjectCache().get(model.getProjectId());
        boolean open = false;
        if(LuceneSearchType.Yes.getByteValue().equals(project.getLuceneSearch())){
            open = true;
        }

        // 私有项目不能建立索引
        if(project.getType() == ProjectType.PRIVATE.getType()){
            open = false;
        }

        return new SearchDto(model.getProjectId(), model.getModuleId(), model.getId(), model.getName(), TableId.BUG,
                 MyString.getStr(model.getContent()), null,  open, model.getCreateTime());
    }
}
