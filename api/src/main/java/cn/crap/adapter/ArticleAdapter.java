package cn.crap.adapter;

import cn.crap.dto.ArticleDTO;
import cn.crap.dto.SearchDto;
import cn.crap.enu.*;
import cn.crap.model.Article;
import cn.crap.model.ArticleWithBLOBs;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.utils.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ArticleAdapter {
    public static ArticleDTO getDto(Article model, ModulePO module, ProjectPO project){
        if (model == null){
            return null;
        }

		ArticleDTO dto = new ArticleDTO();
		BeanUtil.copyProperties(model, dto);

		dto.setCanCommentName(new Byte("1").equals(model.getCanComment()) ? "是" : "否");
		dto.setStatusName(ArticleStatus.getNameByValue(model.getStatus()));
		dto.setTypeName(ArticleType.getByEnumName(model.getType()));

		if (model.getCreateTime() != null) {
			dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
		}
		if (module != null){
			dto.setModuleName(module.getName());
		}
		if (project != null){
			dto.setProjectName(project.getName());
		}
		return dto;
    }

	public static ArticleDTO getDtoWithBLOBs(ArticleWithBLOBs model, ModulePO module, ProjectPO project) {
		if (model == null) {
			return null;
		}
		ArticleDTO dto = getDto(model, module, project);
		dto.setContent(model.getContent());
		dto.setMarkdown(model.getMarkdown());
        dto.setUseMarkdown(false);
		if (AttributeUtils.containAttr(model.getAttributes(), AttributeEnum.MARK_DOWN)){
            dto.setUseMarkdown(true);
        }
		dto.setStatusName(ArticleStatus.getNameByValue(model.getStatus()));
		return dto;
	}

    /**
     * projectId不能修改
     * @param dto
     * @return
     */
    public static ArticleWithBLOBs getModel(ArticleDTO dto){
        if (dto == null){
            return null;
        }
		ArticleWithBLOBs model = new ArticleWithBLOBs();
		BeanUtil.copyProperties(dto, model);
        model.setCreateTime(null);
		model.setAttributes(null);
        return model;
    }

    public static List<ArticleDTO> getDtoWithBLOBs(List<ArticleWithBLOBs> models, ModulePO module){
        if (models == null){
            return new ArrayList<>();
        }
        List<ArticleDTO> dtos = new ArrayList<>();
        for (ArticleWithBLOBs model : models){
            dtos.add(getDtoWithBLOBs(model, module, null));
        }
        return dtos;
    }

	public static List<ArticleDTO> getDto(List<Article> models, ModulePO module, ProjectPO project){
		if (models == null){
			return new ArrayList<>();
		}
		List<ArticleDTO> dtos = new ArrayList<>();
		for (Article model : models){
			dtos.add(getDto(model, module, project));
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
		ProjectPO project = ServiceFactory.getInstance().getProjectCache().get(model.getProjectId());
		boolean open = false;
		if(LuceneSearchType.Yes.getByteValue().equals(project.getLuceneSearch())){
			open = true;
		}

		// 私有项目不能建立索引
		if(project.getType() == ProjectType.PRIVATE.getType()){
			open = false;
		}

		return new SearchDto(model.getProjectId(), model.getModuleId(), model.getId(), model.getName(),
                model.getType().equals(ArticleType.ARTICLE.name()) ? TableId.ARTICLE : TableId.DICTIONARY,
				MyString.getStr(model.getBrief()) + MyString.getStr(model.getContent()), null,  open, model.getCreateTime());
	}
}
