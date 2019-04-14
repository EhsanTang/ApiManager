package cn.crap.utils.generate;

/**
 * @author Ehsan
 * @date 2018/12/31 15:43
 */

import cn.crap.enu.GenerateType;
import cn.crap.utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
public class JavaPOGenerateUtil extends BaseGenerateUtil {
    private static String PO_FIELD = "CA_PO_FIELD";
    private static String PO_GET_SET = "CA_PO_GET_SET";

    private static String FIELD = "\n\tprivate %s %s;";
    private static String GET_SET = "\n\tpublic %s get%s(){\n\t\treturn %s;\n\t}" + "\n\tpublic void set%s(%s %s){\n\t\tthis.%s = %s;\n\t}\n";

    public boolean canExecute(String type){
        if (GenerateType.JAVA_PO.name().equals(type)) {
            return true;
        }
        return false;
    }

    /**
     * 数据库表
     * @return
     */
    public String execute(String fields) throws Exception{
        Set<String> fieldSet = getFields(fields);
        if(fieldSet.size() == 0){
            return "字段不能为空，请选择字段";
        }
        StringBuilder poField = new StringBuilder();
        StringBuilder poGetSet = new StringBuilder();

        for (String field : fieldSet){
            String[] fieldSplits = field.split("_CA_SEPARATOR_");
            String type = fieldSplits.length > 1 ? fieldSplits[1]: null;
            field = fieldSplits[0];
            poField.append(String.format(FIELD, sqlType2JavaType(type), getCamel(field)));
            poGetSet.append(String.format(GET_SET, sqlType2JavaType(type), upperCaseFirst(getCamel(field)), getCamel(field),
                    upperCaseFirst(getCamel(field)), sqlType2JavaType(type), getCamel(field), getCamel(field), getCamel(field)));

        }
        String poFilUrl = Tools.getServicePath() + "WEB-INF/classes/generate/JavaPO.txt";
        String poContent = Tools.readFile(poFilUrl);
        poContent = poContent.replaceAll(PO_FIELD, poField.toString());
        poContent = poContent.replaceAll(PO_GET_SET, poGetSet.toString());

        return poContent;
    }

    public String sqlType2JavaType(String sqlType) {
        if (StringUtils.isEmpty(sqlType)){
            return "未知类型";
        }
        sqlType = sqlType.toLowerCase();
        if (sqlType.indexOf("bit") >= 0) {
            return "Boolean";
        } else if (sqlType.indexOf("tinyint") > 0) {
            return "Byte";
        } else if (sqlType.indexOf("bigint") >= 0) {
            return "Long";
        } else if (sqlType.indexOf("int") >= 0) {
            return "Integer";
        } else if (sqlType.indexOf("float") >= 0) {
            return "Float";
        } else if (sqlType.indexOf("decimal") >= 0 || sqlType.indexOf("numeric") >= 0 || sqlType.indexOf("real") >= 0) {
            return "Double";
        } else if (sqlType.indexOf("char") >= 0 || sqlType.indexOf("text") >= 0) {
            return "String";
        } else if (sqlType.indexOf("datetime") >= 0) {
            return "Date";
        }else if (sqlType.indexOf("timestamp") >= 0){
            return "Date";
        }
        return "未知类型";
    }
}
