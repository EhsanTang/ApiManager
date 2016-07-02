package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.service.ISourceService;
import cn.crap.model.Source;

@Service
public class SourceService extends BaseService<Source>
		implements ISourceService {

	@Resource(name="sourceDao")
	public void setDao(IBaseDao<Source> dao ) {
		super.setDao(dao, new Source());
	}
}
