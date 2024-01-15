package com.ganguo.tab.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by leo on 2017/10/13.
 * 参数限定
 */
public class RetentionConstants {
    public static final int LAYOUT_VERTICAL = 0;//垂直布局
    public static final int LAYOUT_HORIZONTAL = 1;//水平布局

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {LAYOUT_VERTICAL, LAYOUT_HORIZONTAL})
    public @interface LayoutType {

    }
}
