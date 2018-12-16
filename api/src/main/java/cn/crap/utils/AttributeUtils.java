package cn.crap.utils;

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
        attributeMap.putAll(Splitter.on(";")
                .omitEmptyStrings()
                .withKeyValueSeparator(":")
                .split(StringEscapeUtils
                .unescapeHtml(attributesStr)));
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
        return Joiner.on(";")
                .withKeyValueSeparator(":")
                .appendTo(new StringBuilder(";"), attributeMap)
                .append(";")
                .toString();
    }
}
