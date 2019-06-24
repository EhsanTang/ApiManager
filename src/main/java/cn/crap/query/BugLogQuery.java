package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class BugLogQuery extends BaseQuery<BugLogQuery> {
    private String bugId;

    @Override
    public BugLogQuery getQuery() {
        return this;
    }

    public String getBugId() {
        return bugId;
    }

    public BugLogQuery setBugId(String bugId) {
        this.bugId = bugId;
        return this;
    }
}
