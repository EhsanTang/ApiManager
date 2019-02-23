package cn.crap.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * 属性拷贝，详情见org.springframework.beans.BeanUtils
 * 区别在于类型不匹配时，不报错
 * @author Ehsan
 * @date 2018/7/1 11:26
 */
public class BeanUtil extends BeanUtils{
    private static Log logger = LogFactory.getLog(BeanUtil.class);
    private static String[] defIgnoreProperties = new String[]{"password", "passwordSalt", "createTime", "updateTime"};
    private static String[] canNotUpdateProperties = new String[]{"createTime", "password", "passwordSalt", "click",
            "projectId", "userId", "articleId"};

    /**
     * @param source the source bean
     * @param target the target bean
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, defIgnoreProperties);
    }


    /**
     * @param source the source bean
     * @param target the target bean
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }


                            //新增逻辑，类型一致才拷贝
                            if (writeMethod.getParameterTypes().length == 1
                                    && isAssignmentCompatible(readMethod.getReturnType(), writeMethod.getParameterTypes()[0])){
                                writeMethod.invoke(target, value);
                            }else if(logger.isInfoEnabled() || logger.isDebugEnabled()){
                                logger.info("BeanUtil 拷贝属性" + source.getClass().getName() + "[" + sourcePd.getName() + "]至"
                                + target.getClass().getName() + "[" + targetPd.getName() + "]失败");
                            }
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    public static final boolean isAssignmentCompatible(Class<?> targetType, Class<?> sourceType){
        Assert.notNull(targetType);

        if (sourceType == null){
            // 此方法主要用来判断Class是否为原始类型，8种基本类型返回false，基本类型不能为null
            return !targetType.isPrimitive();
        }

        // targetType 为 sourceType 的父类或是相同类型返回true
        if (targetType.isAssignableFrom(sourceType)){
            return true;
        }

        // 基本类型与包装类型一致，返回true
        if (targetType.isPrimitive()){
            Class<?> targetWrapperClazz = getPrimitiveWrapper(targetType);
            if (targetWrapperClazz != null){
                return targetType.equals(sourceType);
            }
        }
        return false;
    }

    /**
     * 获取基本类型的包装类
     * @param primitiveType
     * @return
     */
    public static Class<?> getPrimitiveWrapper(Class<?> primitiveType){
        if (boolean.class.equals(primitiveType)){
            return Boolean.class;
        }
        if (float.class.equals(primitiveType)){
            return Float.class;
        }
        if (long.class.equals(primitiveType)){
            return Long.class;
        }
        if (int.class.equals(primitiveType)){
            return Integer.class;
        }
        if (short.class.equals(primitiveType)){
            return Short.class;
        }
        if (byte.class.equals(primitiveType)){
            return Byte.class;
        }
        if (double.class.equals(primitiveType)){
            return Double.class;
        }
        if (char.class.equals(primitiveType)){
            return Character.class;
        }
        return null;
    }

}
