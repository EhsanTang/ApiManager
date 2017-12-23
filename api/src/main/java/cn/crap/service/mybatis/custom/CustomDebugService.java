package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.custom.CustomDebugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
