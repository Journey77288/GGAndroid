/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.ganguo.utils.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;

import java.util.Arrays;
import java.util.List;

import io.ganguo.utils.common.DialogHelper;

/**
 * <p>
 * 权限请求工具类，部分函数基于AndPermission库的基础上进行封装
 * <p>
 *
 * @see {@AndPermission https://github.com/yanzhenjie/AndPermission}
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 */
public class Permissions {


    /**
     * function:判断是否已经获得相关权限
     *
     * @param context
     * @param permissions
     * @see Activity#checkSelfPermission(String)
     */
    @SuppressLint("NewApi")
    public static boolean hasSelfPermission(Context context, String... permissions) {
        if (!isMNC()) {
            return true;
        }
        for (String permission : permissions) {
            if (!hasSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }


    /**
     * function:判断是否已经获得权限
     *
     * @param context
     * @param permission 对应权限
     * @see Activity#checkSelfPermission(String)
     */
    @SuppressLint("NewApi")
    public static boolean hasSelfPermission(Context context, String permission) {
        if (!isMNC()) {
            return true;
        }
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * function:判断是否已经获得权限
     *
     * @param context
     * @param permission 对应权限
     * @see Activity#checkSelfPermission(String)
     */
    @SuppressLint("NewApi")
    public static boolean hasSelfPermission(Context context, onRequestPermissionListener listener, String... permission) {
        boolean isPermission = hasSelfPermission(context, permission);
        if (!isPermission) {
            return false;
        }
        if (listener != null) {
            listener.onRequestSuccess(Arrays.asList(permission));
        }
        return true;
    }


    /**
     * function:判断编译版本是否是Android M以上，低于Android M则不需要动态获取权限
     *
     * @see Activity#checkSelfPermission(String)
     */
    public static boolean isMNC() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    /**
     * function:请求权限
     *
     * @param permissions 权限，可以直接传递多个权限，以逗号分隔
     * @param context     context上下文
     * @param listener    结果回调接口
     * @param rationale   自定义申请权限被拒绝过一次之后，弹窗提示，并可以直接使用回调函数中RequestExecutor，进行重新请求/取消全选请求
     * @return true[已获得相关权限]，false[未获得相关权限，并执行权限请求]
     */
    public static boolean requestPermission(final Context context, Rationale rationale, final onRequestPermissionListener listener, String... permissions) {
        if (hasSelfPermission(context, listener, permissions)) {
            return true;
        }
        AndPermission
                .with(context)
                .runtime()
                .permission(permissions)
                .rationale(rationale)
                .onGranted(data -> {
                    if (listener != null) {
                        listener.onRequestSuccess(data);
                    }
                })
                .onDenied(data -> {
                    if (listener != null) {
                        listener.onRequestFailure(data);
                    }
                })
                .start();
        return false;
    }


    /**
     * function:请求权限
     *
     * @param permissions 权限，可以直接传递多个权限，以逗号分隔
     * @param context     context上下文
     * @param listener    结果回调接口
     */
    public static void requestPermission(final Context context, final CharSequence content, final onRequestPermissionListener listener, String... permissions) {
        requestPermission(context, (context1, data, executor) -> DialogHelper
                .onPermissionDialog(context1, content, executor)
                .show(), listener, permissions);
    }


    /**
     * <p>
     * 请求系统权限结果回调接口
     * </p>
     */
    public interface onRequestPermissionListener {
        /**
         * function:请求权限成功回调接口
         *
         * @param successPermissions 成功获得的权限
         */
        void onRequestSuccess(List<String> successPermissions);

        /**
         * function:请求权限失败回调接口
         *
         * @param failurePermissions 未获得的权限
         */
        void onRequestFailure(List<String> failurePermissions);
    }


    /**
     * <p>
     * 请求系统权限结果回调接口
     * </p>
     */
    public abstract static class onPermissionAdapterListener implements onRequestPermissionListener {
        @Override
        public void onRequestFailure(List<String> failurePermissions) {

        }
    }
}