package cn.crap.service.table;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.ISourceDao;
import cn.crap.inter.service.table.ISourceService;
import cn.crap.inter.service.tool.ILuceneService;
import cn.crap.model.Source;

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
}
