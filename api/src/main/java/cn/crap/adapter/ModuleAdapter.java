package cn.crap.adapter;

import cn.crap.dto.ModuleDTO;
import cn.crap.model.Interface;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Tools;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ModuleAdapter {
    public static ModuleDTO getDto(ModulePO model, ProjectPO project, Interface templeteInterface){
        if (model == null){
            return null;
        }

        ModuleDTO dto = new ModuleDTO();
        BeanUtil.copyProperties(model, dto);

        if (model.getCreateTime() != null) {
            dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        }
		if (project != null) {
            dto.setProjectName(project.getName());
        }
		if (templeteInterface != null){
            dto.setTemplateName(templeteInterface.getInterfaceName());
        }
        dto.setHasStaticize(false);
        String path = Tools.getStaticPath(project) + "/" + model.getId() + "-articleList--1.html";
        File file = new File(path);
        if (file.exists()){
            dto.setHasStaticize(true);
        }
        return dto;
    }

    public static ModulePO getModel(ModuleDTO dto){
        if (dto == null){
            return null;
        }
        ModulePO model = new ModulePO();
        model.setId(dto.getId());
		model.setName(dto.getName());
		model.setSequence(dto.getSequence());
		model.setUrl(dto.getUrl());
		model.setRemark(dto.getRemark());
		model.setCategory(dto.getCategory());
		
        return model;
    }

    public static List<ModuleDTO> getDto(List<ModulePO> models, ProjectPO project){
        if (CollectionUtils.isEmpty(models)){
            return new ArrayList<>();
        }
        List<ModuleDTO> dtos = new ArrayList<>();
        for (ModulePO model : models){
            dtos.add(getDto(model, project, null));
        }
        return dtos;
    }
}
