package io.ganguo.library;

import org.robolectric.annotation.Config;

import io.ganguo.gtest.AndroidTestCase;
import io.ganguo.gtest.TestDes;

/**
 * 单元测试 - GGLibrary 基类
 * TODO test util methods for RxJava
 * Created by Lynn on 11/28/16.
 */
@Config(application = BaseApp.class,
        packageName = "io.ganguo.library")
public class GGLibraryTestBase extends AndroidTestCase {

    @TestDes("test GGLibrary测试环境配置")
    public void testGGlibrary() {
        //检查是否正确加载指定包名
        asEquals(getApplicationContext().getPackageName(), "io.ganguo.library");
        asEquals("GGLibrary", getApplicationContext().getResources().getString(R.string.gg_app_name));
    }
}
