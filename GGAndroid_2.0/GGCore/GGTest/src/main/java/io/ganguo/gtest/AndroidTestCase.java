package io.ganguo.gtest;

import android.app.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * 需要继承该类的 test case ,
 * 涉及到 Android Env 的测试环境, 需要在 JVM 下运行 Android 的 api ,
 * TODO 第一次运行继承该类 或者 使用 Robolectric Api 的 test case
 * TODO 需要下载对应sdk版本的 robolectric api , 记得翻墙
 *
 * @Config( constants = BuildConfig.class, // doc 配置
 * sdk = 21, //sdk version 指定需要的 sdk 版本
 * application = XXX.class //可以指定自定义 application )
 * <p>
 * 默认使用 targetSdkVersion 测试,
 * TODO config wiki link: http://robolectric.org/configuring/
 * <p>
 * 目前貌似不支持 api > 23 版本, 防止出问题，测试 api 限制在 23 ,
 * <p>
 * RobolectricGradleTestRunner 在目前 (3.1.1以上) 版本已经被 deprecated 了( 3.1.3 后被 remove 了),
 * 网上教程在写这个类时基本上没有更新, 坑...
 * update 至 3.1.4 ,
 * 引用对应的 Module 资源文件应该更改 @Config下的packageName
 * 参考 {@link //io.ganguo.library.test.BaseFragmentTest}
 * Created by Lynn on 10/26/16.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = 23,
        application = TestApplication.class)
public class AndroidTestCase extends BaseTestCase {

    @Before
    public void setup() {
        //重定向输出Log到console
        ShadowLog.stream = System.out;
    }

    /**
     * 测试用到的application context
     *
     * @return
     */
    protected Application getApplicationContext() {
        return RuntimeEnvironment.application;
    }

    @Test
    @TestDes("运行该用例测试Robolectric的环境是否正常")
    @Config(manifest = Config.NONE,
            packageName = "io.ganguo.gtest")
    public void testConfiguration() throws Exception {
        asNotNull(getApplicationContext());
        //检查是否正确加载指定包名
        asEquals(getApplicationContext().getPackageName(), "io.ganguo.gtest");
        // 不要在这里访问res 资源(R.id, R.string, R.anim...)，
        // 猜测是main目录下的testcase, 无法访问main本身的res目录, 如果需要请在test目录对应test case访问
    }
}
