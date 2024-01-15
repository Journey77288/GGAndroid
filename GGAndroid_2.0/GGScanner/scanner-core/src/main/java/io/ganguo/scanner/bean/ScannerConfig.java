package io.ganguo.scanner.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 扫码相关参数定义
 */
public class ScannerConfig {
    public static final int TYPE_QR_CODE = 1;//扫描二维码
    public static final int TYPE_BAR_CODE = 2;//扫描条形码（UPCA）
    public static final int TYPE_ALL = 3;//扫描全部类型码
    public static final int TYPE_CUSTOM = 4;//扫描用户定义类型码


    /**
     * 扫码类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {TYPE_QR_CODE, TYPE_BAR_CODE, TYPE_ALL})
    public @interface ScanType {

    }


}
