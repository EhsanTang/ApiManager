package cn.crap.dao.mybatis.custom;
import cn.crap.utils.Page;
import org.springframework.stereotype.Service;


public class SqlProvider {
    public String queryArticleSql(String moduleId, String name, String type, String category, Page page)
    {
        page = setPage(page);
        StringBuilder sb = new StringBuilder("SELECT id, type, name, click, category, createTime, key, moduleId, brief, sequence FROM article ");
        sb.append(" where moduleId = '" + SqlHelper.checkParams(moduleId) + "'");
        if (name != null){
            sb.append(" and name like '%" + SqlHelper.checkParams(name) + "%'");
        }
        if (type != null){
            sb.append(" and type = '" + SqlHelper.checkParams(type) +"'");
        }
        if (category != null){
            sb.append(" and category = '" + SqlHelper.checkParams(category) +"'");
        }

        sb.append(" order by sequence asc");
        sb.append(" limit " + page.getStart() + "," + page.getSize());
        return sb.toString();
    }


    public String queryProjectSqlByUserIdName(String userId, String name, Page page)
    {
        page = setPage(page);

        StringBuilder sb = new StringBuilder("select id, name, type, remark, userId, createTime, cover from project where userId=#{userId} or " +
                "id in (select projectId from projectUser where userId=#{userId}) ");
       if (name != null){
           sb.append(" and name like '%" + SqlHelper.checkParams(name) + "%'");
       }

        sb.append(" order by sequence asc");
        sb.append(" limit " + page.getStart() + "," + page.getSize());
        return sb.toString();
    }

    public String countProjectSqlByUserIdName(String userId, String name)
    {
        StringBuilder sb = new StringBuilder("select count from project where userId=#{userId} or " +
                "id in (select projectId from projectUser where userId=#{userId}) ");
        if (name != null){
            sb.append(" and name like '%" + SqlHelper.checkParams(name) + "%'");
        }
        return sb.toString();
    }

    private Page setPage(Page page) {
        if (page == null){
            page = new Page(20, 1);
        }
        if (page.getSize() > 100){
            page = new Page(100, page.getCurrentPage());
        }

        return page;
    }

}