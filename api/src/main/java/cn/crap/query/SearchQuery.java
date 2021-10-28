package cn.crap.query;

import lombok.Getter;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class SearchQuery extends BaseQuery<SearchQuery>{
    @Getter
    private String keyword;

    @Getter
    private String dataType;

    @Getter
    private Boolean open;

    @Override
    public SearchQuery getQuery(){
        return this;
    }


    public SearchQuery setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public SearchQuery setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public SearchQuery setOpen(Boolean open) {
        this.open = open;
        return this;
    }
}
