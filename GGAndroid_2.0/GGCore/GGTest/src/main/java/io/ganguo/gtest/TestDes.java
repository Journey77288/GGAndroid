package io.ganguo.gtest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试用例说明 注解
 * Created by Lynn on 10/21/16.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestDes {
    /**
     * 用于修饰测试用例的方法, 描述测试用例的目的，帮助减少冗长测试方法的命名，和方便日记信息输出
     */
    String value() default "";
}
