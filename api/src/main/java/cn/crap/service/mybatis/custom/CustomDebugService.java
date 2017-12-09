package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.DebugMapper;
import cn.crap.dao.mybatis.UserMapper;
import cn.crap.dao.mybatis.custom.CustomDebugMapper;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.model.mybatis.User;
import cn.crap.service.ICacheService;
import cn.crap.service.mybatis.imp.MybatisProjectService;
import cn.crap.service.mybatis.imp.MybatisProjectUserService;
import cn.crap.service.mybatis.imp.MybatisRoleService;
import cn.crap.springbeans.Config;
import cn.crap.utils.Aes;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO 自定义sql有问题
@Service
public class CustomDebugService {
    @Autowired
    private CustomDebugMapper customDebugMapper;

    public void deleteByModelId(String moduleId) {
        Assert.notNull(moduleId);
        customDebugMapper.deleteByModuleId(moduleId);
    }

}
