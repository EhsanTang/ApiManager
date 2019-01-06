package cn.crap.service;

import cn.crap.dao.mybatis.BugDao;
import cn.crap.enu.ArticleStatus;
import cn.crap.enu.TableId;
import cn.crap.model.Bug;
import cn.crap.query.BugQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.TableField;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;


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
    public boolean insert(Bug bug){
        Assert.notNull(bug);
        Assert.notNull(bug.getProjectId());
        if (bug == null) {
            return false;
        }

        if (bug.getContent() == null){
            bug.setContent("");
        }
        if (bug.getStatus() == null) {
            bug.setStatus(ArticleStatus.COMMON.getStatus());
        }

        if (bug.getSequence() == null){
            List<Bug> bugs = this.select(new BugQuery().setPageSize(1).setProjectId(bug.getProjectId())
                    .setSort(TableField.SORT.SEQUENCE_DESC));
            if (bugs.size() > 0){
                bug.setSequence(bugs.get(0).getSequence() + 1);
            }
        }
        return super.insert(bug);
    }

}
