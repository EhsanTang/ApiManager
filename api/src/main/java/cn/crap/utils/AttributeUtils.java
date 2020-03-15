package cn.crap.utils;

import cn.crap.enu.AttributeEnum;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author Ehsan
 * @date 2018/12/16 12:04
 */
public class AttributeUtils {
    private static final String ESCAPE_SPLITTER  = "_CA_S_";
    private static final String ESCAPE_KEY_SPLITTER = "_CA_K_S_";

    private static final String SPLITTER  = ";";
    private static final String KEY_SPLITTER  = ":";

    public static boolean hasAttr(String attributesStr, AttributeEnum attributeEnum){
        if (MyString.isEmpty(attributesStr) || attributeEnum == null){
            return false;
        }
        String value = getAttributeMap(attributesStr).get(attributeEnum.getKey());
        if (attributeEnum.getValue().equals(value)){
            return true;
        }
        return false;
    }

    public static boolean containAttr(String attributesStr, AttributeEnum attributeEnum){
        if (MyString.isEmpty(attributesStr) || attributeEnum == null){
            return false;
        }
        String value = getAttributeMap(attributesStr).get(attributeEnum.getKey());
        if (value != null){
            return true;
        }
        return false;
    }

    public static Integer getAttr(String attributesStr, AttributeEnum attributeEnum, Integer def){
        String value = getAttr(attributesStr, attributeEnum);
        if (MyString.isEmpty(value)){
            return def;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e){
            return def;
        }
    }

    public static String getAttr(String attributesStr, AttributeEnum attributeEnum){
        if (MyString.isEmpty(attributesStr) || attributeEnum == null){
            return null;
        }
        return getAttributeMap(attributesStr).get(attributeEnum.getKey());
    }
    /**
     * 根据字符串获取所有的map
     * @param attributesStr
     * @return
     */
    public static Map<String, String> getAttributeMap(String attributesStr){
        if (MyString.isEmpty(attributesStr)){
            return Maps.newHashMap();
        }

        Map<String, String> attributeMap = Maps.newHashMap();
        attributeMap.putAll(Splitter.on(SPLITTER)
                .omitEmptyStrings()
                .withKeyValueSeparator(KEY_SPLITTER)
                .split(StringEscapeUtils.unescapeHtml(attributesStr)));
        for(Map.Entry<String, String> entry: attributeMap.entrySet()) {
            entry.setValue(entry.getValue().replaceAll(ESCAPE_KEY_SPLITTER, KEY_SPLITTER).replaceAll(ESCAPE_SPLITTER, SPLITTER));
        }
        return attributeMap;
    }

    /**
     *
     * @param attributeMap
     * @return
     */
    public static String getAttributeStr(Map<String, String> attributeMap){
        if (MapUtils.isEmpty(attributeMap)){
            return StringUtils.EMPTY;
        }
        for(Map.Entry<String, String> entry: attributeMap.entrySet()) {
            entry.setValue(entry.getValue().replaceAll( KEY_SPLITTER, ESCAPE_KEY_SPLITTER).replaceAll(SPLITTER, ESCAPE_SPLITTER));
        }
        return Joiner.on(SPLITTER)
                .withKeyValueSeparator(KEY_SPLITTER)
                .useForNull("")
                .appendTo(new StringBuilder(SPLITTER), attributeMap)
                .append(SPLITTER)
                .toString();
    }
}
