package cn.crap.utils.generate;

import com.google.common.base.Splitter;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ehsan
 * @date 2018/12/31 15:43
 */
public abstract class BaseGenerateUtil {
    public abstract boolean canExecute(String type);

    /**
     * 数据库表
     * @return
     */
    public abstract String execute(String fieldNames) throws Exception;

    /**
     * 分割参数
     * @param fieldNames
     * @return
     */
    public Set<String> getFields(String fieldNames){
        if (StringUtils.isEmpty(fieldNames)){
            return new HashSet<>();
        }
        Set<String> fieldSet = new HashSet<>();
        fieldSet.addAll(Splitter.on(",").omitEmptyStrings().splitToList(fieldNames));
        return fieldSet;
    }

    /**
     * 首字母大写
     * @param field
     * @return
     */
    public String upperCaseFirst(String field){
        if (StringUtils.isEmpty(field)){
            return field;
        }
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 转换为驼峰命名
     * 例：user_name --> userName
     */
    public static String getCamel(String field) {
        if (StringUtils.isEmpty(field)){
            return field;
        }
        while (field.indexOf("_") > 0) {
            int index = field.indexOf("_");
            field = field.substring(0, index) + field.substring(index + 1, index + 2).toUpperCase() + field.substring(index + 2);
        }
        return field;
    }
}
