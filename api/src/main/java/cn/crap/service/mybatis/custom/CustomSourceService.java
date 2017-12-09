package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.UserMapper;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SourceDto;
import cn.crap.model.mybatis.Source;
import cn.crap.model.mybatis.User;
import cn.crap.service.ICacheService;
import cn.crap.service.ILuceneService;
import cn.crap.service.mybatis.imp.MybatisProjectService;
import cn.crap.springbeans.Config;
import cn.crap.utils.Aes;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

// TODO 重新生成所有的ID，保证ID有序
@Service
public class CustomSourceService implements ILuceneService<SourceDto> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private Config config;
    @Autowired
    private MybatisProjectService projectService;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private IProjectUserService projectUserService;



        public Source get(String id){
            Source model = sourceDao.get(id);
            if(model == null)
                return new Source();
            return model;
        }

        public List<Source> getAll() {
            return sourceDao.findByMap(null, null, null);
        }

        public List<Source> getAllByProjectId(String projectId) {
            return (List<Source>) sourceDao.queryByHql("from Interface where moduleId in (select id  from Module where projectId=:projectId)", Tools.getMap("projectId", projectId));
        }

        public String getLuceneType() {
            return "资源";
        }

}
