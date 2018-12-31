package cn.crap.utils;

/**
 * @author Ehsan
 * @date 2018/12/31 15:43
 */

import cn.crap.enu.GenerateType;
import org.springframework.stereotype.Service;

@Service
public class SqlSelectGenerateUtil extends BaseGenerateUtil{
    public boolean canHanle(String type){
        if (GenerateType.SQL_SELECT.name().equals(type)) {
            return true;
        }
        return false;
    };

    /**
     * 数据库表
     * @return
     */
    public String hanle(){
        return "SqlSelectGenerateUtil";
    }
}
