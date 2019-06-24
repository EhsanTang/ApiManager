package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class LogQuery extends BaseQuery<LogQuery> {
    private String identy;
    private String modelName;

    @Override
    public LogQuery getQuery() {
        return this;
    }


    public String getIdenty() {
        return identy;
    }

    public LogQuery setIdenty(String identy) {
        this.identy = identy;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public LogQuery setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }
}
