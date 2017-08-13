package cn.crap.service.imp.table;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.dao.ISourceDao;
import cn.crap.service.ISourceService;
import cn.crap.service.ILuceneService;
import cn.crap.model.Source;
import cn.crap.utils.Tools;

@Service
public class SourceService extends BaseService<Source>
		implements ISourceService ,ILuceneService<Source>{

	@Resource(name="sourceDao")
	ISourceDao sourceDao;
	
	@Resource(name="sourceDao")
	public void setDao(IBaseDao<Source> dao ) {
		super.setDao(dao);
	}
	
	@Override
	@Transactional
	public Source get(String id){
		Source model = sourceDao.get(id);
		if(model == null)
			 return new Source();
		return model;
	}

	@Override
	@Transactional
	public List<Source> getAll() {
		return sourceDao.findByMap(null, null, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Source> getAllByProjectId(String projectId) {
		return (List<Source>) sourceDao.queryByHql("from Interface where moduleId in (select id  from Module where projectId=:projectId)", Tools.getMap("projectId", projectId));
	}

	@Override
	public String getLuceneType() {
		return "资源";
	}
}
