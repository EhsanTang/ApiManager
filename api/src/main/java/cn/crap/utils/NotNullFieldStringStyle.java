package cn.crap.utils;

import org.apache.commons.lang.builder.ToStringStyle;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Ehsan
 * @date 2019/3/30 14:30
 */
public class NotNullFieldStringStyle extends ToStringStyle {
    public NotNullFieldStringStyle(){
        super();
        this.setUseShortClassName(true);
        this.setUseIdentityHashCode(false);
    }

    @Override
    public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail){
        if (value == null){
            return;
        }
        if (value instanceof  String){
            if (MyString.isEmpty(value)){
                return;
            }
        }
        if (value instanceof Date){
            value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
        }

        StringBuilder sb = new StringBuilder();
        format(value, sb);
        value = (sb == null ? null : sb.toString());

        if (value == null){
            return;
        }

        super.append(buffer, fieldName, value, fullDetail);
    }

    private static void format(Object object, StringBuilder sb) {
        if (object == null) {
            sb.append("null");
            return;
        }

        if (object instanceof Map<?, ?>) {
            formatMap((Map<?, ?>) object, sb);
        } else if (object instanceof Iterable<?>) {
            formatIterable((Iterable<?>) object, sb);
        }else if (object.getClass().isArray()) {
            formatArray(object, sb);
        } else if (object.getClass() == Object.class) {
            sb.append(object);
        } else {
//            // 针对没有重载Object.toString()方法的对象
//            Method toStringMethod = null;
//            Class<? extends Object> clazz = object.getClass();
//            try {
//                toStringMethod = clazz.getMethod("toString");
//            } catch (Exception e) {
//               // logger.error("指定的类" + clazz.getName() + "不存在toString()方法");
//            }
//            if (toStringMethod == null || toStringMethod.getDeclaringClass() == Object.class) {
//                object = ToStringBuilder.reflectionToString(object);
//            }
            sb.append(object);
        }
    }

    private static <K, V> void formatMap(Map<K, V> map, StringBuilder sb) {
        if (map.size() == 0) {
            return;
        }

        boolean first = true;
        sb.append('{');
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            K key = entry.getKey();
            V value = entry.getValue();
            sb.append(key).append('=');
            format(value, sb);
        }
        sb.append('}');
    }

    private static void formatArray(Object array, StringBuilder sb) {
        if (Array.getLength(array) == 0) {
            return;
        }

        int length = Array.getLength(array);
        sb.append('[');
        for (int i = 0; i < length; ++i) {
            if (i > 0) {
                sb.append(',');
            }
            Object object = Array.get(array, i);
            format(object, sb);
        }
        sb.append(']');
    }

    private static <T> void formatIterable(Iterable<T> iterable, StringBuilder sb) {
        Iterator<T> it=iterable.iterator();
        if (!it.hasNext()) {
            return;
        }

        boolean first = true;
        sb.append('[');
        while(it.hasNext()){
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            format(it.next(), sb);
        }
        sb.append(']');
    }
}
