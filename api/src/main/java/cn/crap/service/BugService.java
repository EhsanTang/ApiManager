package cn.crap.service;

import cn.crap.dao.mybatis.BugDao;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.model.Bug;
import cn.crap.query.BugQuery;
import cn.crap.utils.IConst;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;


@Service
public class BugService extends NewBaseService<Bug, BugQuery> implements IConst {
    private BugDao bugDao;

    @Resource
    public void bugDao(BugDao bugDao) {
        this.bugDao = bugDao;
        super.setBaseDao(bugDao, TableId.BUG);
    }

    /**
     * 新增
     * @param bug
     * @return
     */
    @Override
    public boolean insert(Bug bug){
        Assert.notNull(bug);
        Assert.notNull(bug.getProjectId());
        if (bug == null) {
            return false;
        }

        if (bug.getContent() == null){
            bug.setContent("");
        }
        bug.setStatus(BugStatus.NEW.getByteValue());
        bug.setSequence(getMaxSequence(bug, new BugQuery()));
        return super.insert(bug);
    }

    public Bug getChangeStatusPO(String id, String type, Byte value) throws Exception{
        Bug bug = new Bug();
        bug.setId(id);
        if (PickCode.BUG_STATUS.getCode().equals(type)){
            BugStatus bugStatus = Optional.ofNullable(BugStatus.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "状态有误"));
            bug.setStatus(bugStatus.getByteValue());
        }else if (PickCode.SEVERITY.getCode().equals(type)){
            BugSeverity bugSeverity = Optional.ofNullable(BugSeverity.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "严重程度有误"));
            bug.setSeverity(bugSeverity.getByteValue());
        }else if (PickCode.PRIORITY.getCode().equals(type)){
            BugPriority bugPriority = Optional.ofNullable(BugPriority.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "优先级有误"));
            bug.setPriority(bugPriority.getByteValue());
        }else {
            throw new MyException(MyError.E000065, "不识别的类型：" + type);
        }
        return bug;
    }

}
