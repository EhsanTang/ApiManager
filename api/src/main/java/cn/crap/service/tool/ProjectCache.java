package cn.crap.service.tool;

import cn.crap.beans.Config;
import cn.crap.model.ProjectPO;
import cn.crap.service.ProjectService;
import cn.crap.utils.MyString;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("projectCache")
public class ProjectCache{
	private static Cache<String, ProjectPO> cache;
	public static final String CACHE_PREFIX = "project:";

	@Autowired
	private ProjectService projectService;

    public Cache<String, ProjectPO> getCache(){
        if (cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(10)
                    .concurrencyLevel(5)
                    .expireAfterWrite(Config.cacheTime, TimeUnit.SECONDS)
                    .build();
        }
        return cache;
    }

	public String getName(String projectId){
		if(MyString.isEmpty(projectId)){
			return "";
		}
		String cacheKey = assembleKey(projectId);
		ProjectPO project = getCache().getIfPresent(cacheKey);
		if(project == null){
			project = projectService.get(projectId);
			if(project == null) {
				return "";
			}
			getCache().put(cacheKey, project);
		}
		return project.getName();
	}
	
	public ProjectPO get(String projectId){
		if(MyString.isEmpty(projectId)){
			return new ProjectPO();
		}
		String cacheKey = assembleKey(projectId);
		ProjectPO project = getCache().getIfPresent(cacheKey);
		if(project == null){
			project = projectService.get(projectId);
			if(project == null) {
				project = new ProjectPO();
			}
			getCache().put(cacheKey, project);
		}
		//内存缓存时拷贝对象，防止在Controller中将密码修改为空时导致问题
		ProjectPO p = new ProjectPO();
		BeanUtils.copyProperties(project, p);
		return p;
	}

    
    public boolean del(String projectId){
		getCache().invalidate(assembleKey(projectId));
        return true;
    }


	
    public boolean flushDB(){
		getCache().invalidateAll();
	    return true;
    }

	private String assembleKey(String projectId) {
		return CACHE_PREFIX + projectId;
	}
}
