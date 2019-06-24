package cn.crap.framework.interceptor;

import java.lang.annotation.*;

/**
 * 权限拦截器
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthPassport {
    boolean validate() default true;

    String authority() default "";
}
