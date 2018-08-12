package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public abstract class BaseQuery<T> {
    private String id;
    private Integer currentPage;
    private Integer pageSize;
    private String sort;
    private Byte status;

    abstract T getQuery();

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getId() {
        return id;
    }

    public T setId(String id) {
        this.id = id;
        return getQuery();
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public T setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return getQuery();
    }

    public Integer getPageSize() {
        return pageSize;
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

    public Byte getStatus() {
        return status;
    }

    public T setStatus(Byte status) {
        this.status = status;
        return getQuery();
    }
}
