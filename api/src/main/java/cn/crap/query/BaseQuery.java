package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;
import lombok.Getter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public abstract class BaseQuery<T> {
    @Getter
    private String id;
    @Getter
    private String projectId;
    @Getter
    private String moduleId;
    @Getter
    private Byte status;
    private String sort;

    /**
     * 一下不能修改为int，否则在springMVC参数自动注入的时候会出现异常
     */
    private Integer pageSize;
    private Integer currentPage;
    private Integer allRow;
    @Getter
    private Boolean queryAll;



    abstract T getQuery();

    public T setQueryAll(boolean queryAll) {
        this.queryAll = queryAll;
        return getQuery();
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public T setId(String id) {
        this.id = id;
        return getQuery();
    }

    public T setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return getQuery();
    }

    public T setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return getQuery();
    }

    public String getSort() throws MyException{
        SafetyUtil.checkSqlParam(sort);
        return sort;
    }

    public T setSort(String sort) {
        this.sort = sort;
        return getQuery();
    }

    public T setStatus(Byte status) {
        this.status = status;
        return getQuery();
    }

    public T setProjectId(String projectId) {
        this.projectId = projectId;
        return getQuery();
    }

    public T setModuleId(String moduleId) {
        this.moduleId = moduleId;
        return getQuery();
    }

    public T setAllRow(int allRow) {
        this.allRow = allRow;
        return getQuery();
    }

    /*************** GET ****************/
    public int getCurrentPage() {
        return (currentPage == null || currentPage <= 0) ? 1 : currentPage;
    }

    /**
     * 一次查询不能超过100
     * @return
     */
    public int getPageSize() {
        if (pageSize == null){
            return 20;
        }
        if (pageSize > 100){
            return 100;
        }
        return (pageSize <= 0 ? 20 : pageSize);
    }

    /**
     * 起始位置
     */
    public int getStart(){
        return  this.getPageSize() * (this.getCurrentPage() - 1);
    }

    public int getAllRow() {
        return (allRow == null ||allRow < 0) ? 0 : allRow;
    }

    public int getTotalPage(){
        return (this.getAllRow() + this.getPageSize() -1) / this.getPageSize();
    }
}
