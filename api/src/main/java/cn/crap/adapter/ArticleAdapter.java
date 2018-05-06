package cn.crap.adapter;

import cn.crap.dto.ArticleDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.ArticleStatus;
import cn.crap.enumer.ArticleType;
import cn.crap.enumer.LuceneSearchType;
import cn.crap.enumer.ProjectType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.model.mybatis.Article;
import cn.crap.model.mybatis.ArticleWithBLOBs;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.service.tool.LuceneSearchService;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.DateFormartUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ArticleAdapter {
    public static ArticleDto getDto(Article model, Module module){
        if (model == null){
            return null;
        }

        ArticleDto dto = new ArticleDto();
        dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setBrief(model.getBrief());
		dto.setClick(model.getClick());
		dto.setType(model.getType());
		dto.setStatus(model.getStatus());
		dto.setModuleId(model.getModuleId());
		dto.setMkey(model.getMkey());
		dto.setCanDelete(model.getCanDelete());
		dto.setCategory(model.getCategory());
		dto.setCanComment(model.getCanComment());
		dto.setCommentCount(model.getCommentCount());
		dto.setSequence(model.getSequence());
		dto.setProjectId(model.getProjectId());
		dto.setTypeName(ArticleType.getByEnumName(model.getType()));
		if (model.getCreateTime() != null) {
			dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
		}
		if (module != null){
			dto.setModuleName(module.getName());
		}
		return dto;
    }

	public static ArticleDto getDtoWithBLOBs(ArticleWithBLOBs model, Module module) {
		if (model == null) {
			return null;
		}
		ArticleDto dto = getDto(model, module);
		dto.setContent(model.getContent());
		dto.setMarkdown(model.getMarkdown());
		dto.setStatusName(ArticleStatus.getNameByValue(model.getStatus()));
		return dto;
	}

    /**
     * projectId不能修改
     * @param dto
     * @return
     */
    public static ArticleWithBLOBs getModel(ArticleDto dto){
        if (dto == null){
            return null;
        }
		ArticleWithBLOBs model = new ArticleWithBLOBs();
        model.setId(dto.getId());
		model.setName(dto.getName());
		model.setBrief(dto.getBrief());
		model.setContent(dto.getContent());
		model.setClick(dto.getClick());
		model.setType(dto.getType());
		model.setStatus(dto.getStatus());
		model.setModuleId(dto.getModuleId());
		model.setMkey(dto.getMkey());
		model.setCanDelete(dto.getCanDelete());
		model.setCategory(dto.getCategory());
		model.setCanComment(dto.getCanComment());
		model.setCommentCount(dto.getCommentCount());
		model.setSequence(dto.getSequence());
		model.setMarkdown(dto.getMarkdown());
		model.setStatus(dto.getStatus());
		model.setProjectId(null);

        return model;
    }

    public static List<ArticleDto> getDtoWithBLOBs(List<ArticleWithBLOBs> models, Module module){
        if (models == null){
            return new ArrayList<>();
        }
        List<ArticleDto> dtos = new ArrayList<>();
        for (ArticleWithBLOBs model : models){
            dtos.add(getDtoWithBLOBs(model, module));
        }
        return dtos;
    }

	public static List<ArticleDto> getDto(List<Article> models, Module module){
		if (models == null){
			return new ArrayList<>();
		}
		List<ArticleDto> dtos = new ArrayList<>();
		for (Article model : models){
			dtos.add(getDto(model, module));
		}
		return dtos;
	}

	public static List<SearchDto> getSearchDto(List<ArticleWithBLOBs> models){
		if (models == null){
			return new ArrayList<>();
		}
		List<SearchDto> dtos = new ArrayList<>();
		for (ArticleWithBLOBs model : models){
		    try {
                dtos.add(getSearchDto(model));
            }catch (Exception e){
		        e.printStackTrace();
            }
		}
		return dtos;
	}

	public static SearchDto getSearchDto(ArticleWithBLOBs model){
        ModuleCache moduleCache = SpringContextHolder.getBean("moduleCache", ModuleCache.class);
        ProjectCache projectCache = SpringContextHolder.getBean("projectCache", ProjectCache.class);
        Project project = projectCache.get(model.getProjectId());
        SearchDto dto = new SearchDto();
		String moduleId = model.getId();
		dto.setId(moduleId);
		dto.setCreateTime(model.getCreateTime());
		dto.setContent(model.getBrief() + model.getContent());
		dto.setModuleName(moduleCache.get(moduleId).getName());
		dto.setTitle(model.getName());
		dto.setType("Article");
		dto.setUrl("#/"+model.getProjectId()+"/article/detail/"+model.getModuleId()+"/"+model.getType()+"/"+moduleId);
		dto.setVersion("");
		dto.setProjectId(model.getProjectId());

		if(project.getType() == ProjectType.PRIVATE.getType()){
			dto.setNeedCreateIndex(false);
		}

		if(project.getLuceneSearch().intValue() == LuceneSearchType.No.getValue()){
			dto.setNeedCreateIndex(false);
		}

		return dto;
	}
}
