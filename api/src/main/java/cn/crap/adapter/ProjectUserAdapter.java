package cn.crap.adapter;

import cn.crap.dto.ProjectUserDto;
import cn.crap.framework.base.BaseController;
import cn.crap.model.Project;
import cn.crap.model.ProjectUser;
import cn.crap.utils.BeanUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ProjectUserAdapter {
    public static ProjectUserDto getDto(ProjectUser model, Project project){
        if (model == null){
            return null;
        }

        ProjectUserDto dto = new ProjectUserDto();
		BeanUtil.copyProperties(model, dto);

		dto.setProjectName(project == null ? "" : project.getName());

		Boolean[] projectAuth = new Boolean[19];
		projectAuth[BaseController.ADD_MODULE] = model.getAddModule();
		projectAuth[BaseController.DEL_MODULE] = model.getDelModule();
		projectAuth[BaseController.MOD_MODULE] = model.getModModule();
		projectAuth[BaseController.ADD_INTER] = model.getAddInter();
		projectAuth[BaseController.DEL_INTER] = model.getDelInter();
		projectAuth[BaseController.MOD_INTER] = model.getModInter();
		projectAuth[BaseController.ADD_ARTICLE] = model.getAddArticle();
		projectAuth[BaseController.DEL_ARTICLE] = model.getDelArticle();
		projectAuth[BaseController.MOD_ARTICLE] = model.getModArticle();
		projectAuth[BaseController.ADD_SOURCE] = model.getAddSource();
		projectAuth[BaseController.DEL_SOURCE] = model.getDelSource();
		projectAuth[BaseController.MOD_SOURCE] = model.getModSource();
		projectAuth[BaseController.ADD_DICT] = model.getAddDict();
		projectAuth[BaseController.DEL_DICT] = model.getDelDict();
		projectAuth[BaseController.MOD_DICT] = model.getModDict();
		projectAuth[BaseController.ADD_ERROR] = model.getAddError();
		projectAuth[BaseController.DEL_ERROR] = model.getDelError();
		projectAuth[BaseController.MOD_ERROR] = model.getModError();
		
		dto.setProjectAuth(projectAuth);

		return dto;
    }

    public static ProjectUser getModel(ProjectUserDto dto){
        if (dto == null){
            return null;
        }
        ProjectUser model = new ProjectUser();
		BeanUtil.copyProperties(dto, model);
        return model;
    }

    public static List<ProjectUserDto> getDto(List<ProjectUser> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<ProjectUserDto> dtos = new ArrayList<>();
        for (ProjectUser model : models){
            dtos.add(getDto(model, null));
        }
        return dtos;
    }
}
