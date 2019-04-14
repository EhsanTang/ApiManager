package cn.crap.query;

import lombok.Getter;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class BugQuery extends BaseQuery<BugQuery> {
    @Getter
    private String name;
    @Getter
    private String creator;
    @Getter
    private String executor;
    @Getter
    private String tracer;
    @Getter
    private String tester;
    @Getter
    private String idGreatThen;

    @Override
    public BugQuery getQuery() {
        return this;
    }

    public BugQuery setName(String name) {
        this.name = name;
        return this;
    }

    public BugQuery setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public BugQuery setExecutor(String executor) {
        this.executor = executor;
        return this;
    }

    public BugQuery setTracer(String tracer) {
        this.tracer = tracer;
        return this;
    }

    public BugQuery setTester(String tester) {
        this.tester = tester;
        return this;
    }

    public BugQuery setIdGreatThen(String idGreatThen) {
        this.idGreatThen = idGreatThen;
        return this;
    }
}

