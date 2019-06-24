package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class SourceQuery extends BaseQuery<SourceQuery> {
    private String name;

    @Override
    public SourceQuery getQuery() {
        return this;
    }

    public String getName() throws MyException {
        SafetyUtil.checkSqlParam(name);
        return name;
    }

    public SourceQuery setName(String name) {
        this.name = name;
        return this;
    }
}
