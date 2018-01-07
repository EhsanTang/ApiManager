package cn.crap.service.tool;

import cn.crap.model.mybatis.Project;
import cn.crap.service.ICacheService;
import cn.crap.service.mybatis.ProjectService;
import cn.crap.beans.Config;
import cn.crap.utils.MyString;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("projectCache")
public class ProjectCache implements ICacheService<Project> {
	private static Cache<String, Project> cache;
	public static final String CACHE_PREFIX = "project:";

	@Autowired
	private Config config;
	@Autowired
	private ProjectService projectService;

    public Cache<String, Project> getCache(){
        if (cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(10)
                    .concurrencyLevel(5)
                    .expireAfterWrite(config.getCacheTime(), TimeUnit.SECONDS)
                    .build();
        }
        return cache;
    }
	
	@Override
	public Project get(String projectId){
		if(MyString.isEmpty(projectId)){
			return new Project();
		}
		String cacheKey = assembleKey(projectId);
		Project project = getCache().getIfPresent(cacheKey);
		if(project == null){
			project = projectService.getById(projectId);
			if(project == null) {
				project = new Project();
			}
			getCache().put(cacheKey, project);
		}
		//内存缓存时拷贝对象，防止在Controller中将密码修改为空时导致问题
		Project p = new Project();
		BeanUtils.copyProperties(project, p);
		return p;
	}

    @Override
    public boolean del(String projectId){
		getCache().invalidate(assembleKey(projectId));
        return true;
    }


	@Override
    public boolean flushDB(){
		getCache().invalidateAll();
	    return true;
    }

	private String assembleKey(String projectId) {
		return CACHE_PREFIX + projectId;
	}
}
