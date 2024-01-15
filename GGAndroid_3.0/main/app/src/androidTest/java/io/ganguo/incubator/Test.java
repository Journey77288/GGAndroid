package com.jsrs.user;

import android.os.Build;

import io.ganguo.utils.util.Systems;


/**
 * Created by Tony on 11/10/15.
 */
public class Test extends ApplicationTest {

    public void test() {
        // rider/1.0_dev (android; 4.4.4; 19)
        String userAgent = "rider/" + Systems.getVersionName(AppContext.me()) + " (android; " + Build.VERSION.RELEASE + "; " + Build.VERSION.SDK_INT + ")";
        System.out.println(userAgent);
    }
}
