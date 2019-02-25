package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class BugQuery extends BaseQuery<BugQuery>{
    private String name;
    private String creator;
    private String executor;
    private String tracer;
    private String tester;
    private String greatThenId;

    @Override
    public BugQuery getQuery(){
        return this;
    }

    public BugQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public BugQuery setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getExecutor() {
        return executor;
    }

    public BugQuery setExecutor(String executor) {
        this.executor = executor;
        return this;
    }

    public String getTracer() {
        return tracer;
    }

    public BugQuery setTracer(String tracer) {
        this.tracer = tracer;
        return this;
    }

    public String getTester() {
        return tester;
    }

    public BugQuery setTester(String tester) {
        this.tester = tester;
        return this;
    }

    public String getGreatThenId() {
        return greatThenId;
    }

    public BugQuery setGreatThenId(String greatThenId) {
        this.greatThenId = greatThenId;
        return this;
    }
}
