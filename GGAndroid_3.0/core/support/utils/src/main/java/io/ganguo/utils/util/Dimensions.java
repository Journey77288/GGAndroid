package io.ganguo.utils.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import io.ganguo.utils.bean.Globals;

/**
 * <p>
 * Dimension 计算工具类
 * </p>
 * Created by leo on 2018/8/6.
 */

public class Dimensions {
    private Dimensions() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @param context
     * @return
     */
    public static int dpToPx(Context context, float dpValue) {
        return (int) applyDimension(context, TypedValue.COMPLEX_UNIT_DIP, dpValue);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     */
    public static int pxTodp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 单位转换
     *
     * @param context
     * @param unit    TypedValue.COMPLEX_UNIT_DIP
     * @param value   px
     * @return
     */

    public static float applyDimension(Context context, int unit, float value) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(unit, value, displayMetrics);
    }

}
