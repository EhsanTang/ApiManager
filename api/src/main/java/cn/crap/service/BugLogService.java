package cn.crap.service;

import cn.crap.dao.mybatis.BugLogDao;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.BugLogPO;
import cn.crap.query.BugLogQuery;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.IConst;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;


@Service
public class BugLogService extends NewBaseService<BugLogPO, BugLogQuery> implements IConst {
    private BugLogDao bugLogDao;

    @Autowired
    private ModuleCache moduleCache;

    @Autowired
    private ProjectCache projectCache;

    @Autowired
    private UserService userService;

    @Resource
    public void bugLogDao(BugLogDao bugLogDao) {
        this.bugLogDao = bugLogDao;
        super.setBaseDao(bugLogDao, TableId.BUG_LOG);
    }

    /**
     * 新增
     * @param bugLog
     * @return
     */
    @Override
    public boolean insert(BugLogPO bugLog) throws MyException {
        Assert.notNull(bugLog);
        Assert.notNull(bugLog.getBugId());
        Assert.notNull(bugLog.getProjectId());

        if (bugLog == null) {
            return false;
        }

        LoginInfoDto user = LoginUserHelper.getUser();
        bugLog.setOperator(user.getId());
        bugLog.setOperatorStr(MyString.isEmpty(user.getTrueName()) ? user.getUserName() : user.getTrueName());
        bugLog.setStatus(Byte.parseByte("1"));
        return super.insert(bugLog);
    }
}
