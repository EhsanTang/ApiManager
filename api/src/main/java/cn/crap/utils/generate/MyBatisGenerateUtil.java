package cn.crap.utils.generate;

/**
 * @author Ehsan
 * @date 2018/12/31 15:43
 */

import cn.crap.enu.GenerateType;
import cn.crap.utils.Tools;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MyBatisGenerateUtil extends BaseGenerateUtil {
    private static String XML_RESULT_MAP = "CA_RESULT_MAP";
    private static String XML_BASE_COLUMN = "CA_BASE_COLUMN";
    private static String XML_JAVA_FIELD = "CA_JAVA_FIELD";
    private static String XML_UPDATE_SET = "CA_UPDATE_SET";

    private static String UPDATE_SET = "\n\t\t<if test=\"%s != null\">\n\t\t\t %s = #{%s},\n\t\t</if>";
    private static String RESUMT_MAP_ID = "\n\t<id column=\"%s\" property=\"%s\"/>";
    // private static String RESUMT_MAP_ID_WITH_JDBC = "<id column=\"%s\" property=\"%s\" jdbcType=\"%s\"/>";

    public boolean canExecute(String type){
        if (GenerateType.MY_BATIS_XML.name().equals(type)) {
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

        StringBuilder resultColumns = new StringBuilder();
        StringBuilder javaFields = new StringBuilder();
        StringBuilder resultMaps = new StringBuilder();
        StringBuilder updateSet = new StringBuilder();

        for (String field : fieldSet){
            field = field.split("_CA_SEPARATOR_")[0];
            resultColumns.append(field + ",");
            javaFields.append("#{" + getCamel(field) + "},");
            resultMaps.append(String.format(RESUMT_MAP_ID, field, getCamel(field)));
            updateSet.append(String.format(UPDATE_SET, getCamel(field), field, getCamel(field)));
        }
        String mapperFilUrl = Tools.getServicePath() + "WEB-INF/classes/generate/mapperTemplete.txt";
        String mapperContent = Tools.readFile(mapperFilUrl);
        mapperContent = mapperContent.replaceAll(XML_BASE_COLUMN, resultColumns.length() > 0 ? resultColumns.substring(0, resultColumns.length() - 1) : "");
        mapperContent = mapperContent.replaceAll(XML_JAVA_FIELD, javaFields.length() > 0 ? javaFields.substring(0, javaFields.length() - 1) : "");
        mapperContent = mapperContent.replaceAll(XML_RESULT_MAP, resultMaps.toString());
        mapperContent = mapperContent.replaceAll(XML_UPDATE_SET, updateSet.toString());

        return mapperContent;
    }

    private String sqlType2MybatisType(String sqlType) {
        sqlType = sqlType.toLowerCase();
        if (sqlType.indexOf("bit") >= 0) {
            return "BIT";
        } else if (sqlType.indexOf("tinyint") >=0 ) {
            return "TINYINT";
        } else if (sqlType.indexOf("smallint") >=0 ) {
            return "SMALLINT";
        } else if (sqlType.indexOf("bigint") >= 0) {
            return "BIGINT";
        } else if (sqlType.indexOf("int") >= 0) {
            return "INTEGER";
        } else if (sqlType.indexOf("float") >= 0) {
            return "FLOAT";
        } else if (sqlType.indexOf("decimal") >= 0 || sqlType.indexOf("numeric") >= 0 || sqlType.indexOf("real") >= 0) {
            return "DECIMAL";
        } else if (sqlType.indexOf("char") >= 0) {
            return "VARCHAR";
        } else if (sqlType.indexOf("datetime") >= 0) {
            return "DATE";
        } else if (sqlType.indexOf("text") >= 0) {
            return "TEXT";
        } else if (sqlType.indexOf("timestamp") >= 0){
            return "TIMESTAMP";
        }
        return "未知类型";
    }
}
