package cn.crap.query;

import java.util.List;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class MenuQuery extends BaseQuery<MenuQuery> {
    private String parentId;
    private List<String> parentIds;
    private String menuName;
    private String type;

    @Override
    public MenuQuery getQuery() {
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public MenuQuery setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public List<String> getParentIds() {
        return parentIds;
    }

    public MenuQuery setParentIds(List<String> parentIds) {
        this.parentIds = parentIds;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public MenuQuery setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
