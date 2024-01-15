package io.ganguo.gtest;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * 定义输出相关说明的规则，
 * 作用于JUnit测试用例方法
 *
 * @Test 修饰的方法
 * TODO {@link //io.ganguo.gtest.TestTSpecAnno}
 * Created by Lynn on 10/21/16.
 */

public class TestDesRule implements TestRule {

    /**
     * 提取注解的具体信息
     * 输出格式：
     * {
     * 测试类 :  ...
     * 测试方法: ...
     * 描述 : ...
     * }
     **/
    @Override
    public Statement apply(final Statement base, final Description description) {
        // 获取测试用例的描述注解
        final TestDes annotation = description.getAnnotation(TestDes.class);

        if (annotation == null) {
            // 如果没有修饰，则不进一步封装操作
            return base;
        }

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final StringBuilder desBuilder = new StringBuilder();
                desBuilder.append("\n@Test\n{\n\tClass : ");
                desBuilder.append(description.getTestClass().getSimpleName());
                desBuilder.append("\n\tMethod : ");
                desBuilder.append(description.getMethodName());
                desBuilder.append("\n\tDescription : ");
                desBuilder.append(annotation.value());
                desBuilder.append("\n}\n");
                try {
                    //正常执行用例
                    base.evaluate();
                } catch (final Throwable e) {
                    //如果运行测试用例过程出错, 添加额外出错信息
                    desBuilder.append("Run Test Case Failed!\n");
                    if (e.getMessage() != null) {
                        desBuilder.append(e.getMessage());
                    }
                    final Throwable throwable = new Throwable(desBuilder.toString(), e);
                    //调用链信息
                    throwable.setStackTrace(e.getStackTrace());
                    //输出错误
                    throw throwable;
                }
                //正常结束
                desBuilder.append("Run Test Case Passed!\n");
                //输出正常结果
                System.out.println(desBuilder.toString());
            }
        };
    }
}
