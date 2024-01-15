/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ganguo.scanner.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.View;

import java.io.IOException;

import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.Views;
import io.ganguo.utils.util.log.Logger;


/**
 * 相机管理工具类
 */
public class ScannerCameraManager {
    private final CameraConfiguration configuration;
    private Camera camera;

    public ScannerCameraManager(Context context) {
        this.configuration = new CameraConfiguration(context);
    }

    /**
     * Opens the camera driver and initializes the hardware parameters.
     *
     * @throws Exception ICamera open failed, occupied or abnormal.
     */
    public synchronized void openCamera() throws Exception {
        if (camera != null) {
            return;
        }
        camera = Camera.open();
        if (camera == null) {
            throw new IOException("The camera is occupied.");
        }
        configuration.init(camera);
        try {
            configuration.setCameraParameters(camera);
        } catch (RuntimeException re) {
            onTryConfigCamera();
        }
    }


    /**
     * function: 配置相机出现异常后，尝试重新配置
     */
    protected void onTryConfigCamera() {
        if (camera == null) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        String parametersFlattened = parameters == null ? null : parameters.flatten();
        if (Strings.isEmpty(parametersFlattened) || configuration == null) {
            return;
        }
        try {
            parameters.unflatten(parametersFlattened);
            camera.setParameters(parameters);
            configuration.setCameraParameters(camera);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


    /**
     * Closes the camera driver if still in use.
     */
    public synchronized void closeDriver() {
        if (camera == null) {
            return;
        }
        camera.setPreviewCallback(null);
        camera.release();
        camera = null;
    }

    /**
     * Camera is opened.
     *
     * @return true, other wise false.
     */
    public boolean isOpen() {
        return camera != null;
    }

    /**
     * Get camera configuration.
     *
     * @return {@link CameraConfiguration}.
     */
    public CameraConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Camera start preview.
     *
     * @param holder          {@link SurfaceHolder}.
     * @param previewCallback {@link Camera.PreviewCallback}.
     * @throws IOException if the method fails (for example, if the surface is unavailable or unsuitable).
     */
    public void startPreview(SurfaceHolder holder, Camera.PreviewCallback previewCallback) throws IOException {
        if (camera != null) {
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(previewCallback);
            camera.startPreview();
        }
    }

    /**
     * Camera stop preview.
     */
    public void stopPreview() {
        if (camera == null) {
            return;
        }
        try {
            camera.stopPreview();
        } catch (Exception ignored) {
            // nothing.
        }
        try {
            camera.setPreviewDisplay(null);
        } catch (IOException ignored) {
            // nothing.
        }
    }

    /**
     * Focus on, make a scan action.
     *
     * @param callback {@link Camera.AutoFocusCallback}.
     */
    public void autoFocus(Camera.AutoFocusCallback callback) {
        if (camera != null)
            try {
                camera.autoFocus(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    /**
     * function：打开闪光灯
     */
    public void onLight() {
        if (isCameraNull()) {
            return;
        }
        if (isOnLight()) {
            return;
        }
        Camera.Parameters parameters = getCameraParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
    }

    /**
     * function：关闭闪光灯
     */
    public void offLight() {
        if (isCameraNull()) {
            return;
        }
        if (!isOnLight()) {
            return;
        }
        Camera.Parameters parameters = getCameraParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
    }


    /**
     * function：是否打开闪光灯
     *
     * @return
     */
    public boolean isOnLight() {
        if (getCamera() == null) {
            return false;
        }
        if (Strings.isEquals(Camera.Parameters.FLASH_MODE_TORCH, getCameraParameters().getFlashMode())) {
            return true;
        }
        return false;
    }


    /**
     * function：检查相机是否正确开启
     *
     * @return
     */
    public boolean isCameraNull() {
        return getCamera() == null;
    }

    /**
     * function：get current Camera
     *
     * @return
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * function：get Camera.Parameters
     *
     * @return
     */
    public Camera.Parameters getCameraParameters() {
        return getCamera().getParameters();
    }
}
