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
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.ganguo.utils.util.Screens;
import io.ganguo.utils.util.Systems;
import io.ganguo.utils.util.log.Logger;

/**
 * <p>
 * Camera配置工具类
 * <p/>
 */
public final class CameraConfiguration {
    private static final String TAG = CameraConfiguration.class.getSimpleName();
    private static final int MIN_PREVIEW_PIXELS = 480 * 320;
    private static final double MAX_ASPECT_DISTORTION = 0.15;
    private final Context context;
    private Point screenResolution;
    private Point cameraResolution;

    public CameraConfiguration(Context context) {
        this.context = context;
    }

    /**
     * function：init camera
     *
     * @param camera
     */
    public void init(Camera camera) {
        screenResolution = getDisplaySize(Screens.getDisplay(context));
        Point screenResolutionForCamera = new Point();
        screenResolutionForCamera.x = screenResolution.x;
        screenResolutionForCamera.y = screenResolution.y;

        // Convert to vertical screen.
        if (screenResolution.x < screenResolution.y) {
            screenResolutionForCamera.x = screenResolution.y;
            screenResolutionForCamera.y = screenResolution.x;
        }
        cameraResolution = findBestPreviewSizeValue(camera.getParameters(), screenResolutionForCamera);
    }


    private Point getDisplaySize(final Display display) {
        final Point point = new Point();
        if (Build.VERSION.SDK_INT >= 13)
            display.getSize(point);
        else {
            point.set(display.getWidth(), display.getHeight());
        }
        return point;
    }

    /**
     * function：set camera Parameters
     *
     * @param camera
     * @param
     */
    public void setCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        if (parameters == null) {
            Logger.w(TAG, "Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
        camera.setParameters(parameters);
        Camera.Parameters afterParameters = camera.getParameters();
        Camera.Size afterSize = afterParameters.getPreviewSize();
        if (afterSize != null && (cameraResolution.x != afterSize.width || cameraResolution.y != afterSize.height)) {
            cameraResolution.x = afterSize.width;
            cameraResolution.y = afterSize.height;
        }
        camera.setDisplayOrientation(90);
    }

    /**
     * Calculate the preview interface size.
     *
     * @param parameters       camera params.
     * @param screenResolution screen resolution.
     * @return {@link Point}.
     */
    private Point findBestPreviewSizeValue(Camera.Parameters parameters, Point screenResolution) {
        List<Camera.Size> rawSupportedSizes = parameters.getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
            Log.w(TAG, "Device returned no supported preview sizes; using default");
            Camera.Size defaultSize = parameters.getPreviewSize();
            return new Point(defaultSize.width, defaultSize.height);
        }
        // Sort by size, descending
        List<Camera.Size> supportedPreviewSizes = new ArrayList<>(rawSupportedSizes);
        //对预览数据进行排序处理
        onSortPreviewSizes(supportedPreviewSizes);
        //打印图片预览数据
        onPrintPreviewData(supportedPreviewSizes);
        //过滤删除不合适的数据大小
        Point point = onFilterUnsuitableSize(supportedPreviewSizes, screenResolution);
        if (point != null) {
            return point;
        }
        //判断过滤后，集合是否还有数据，有则取出返回
        if (!supportedPreviewSizes.isEmpty()) {
            Camera.Size largestPreview = supportedPreviewSizes.get(0);
            Point largestSize = new Point(largestPreview.width, largestPreview.height);
            Log.i(TAG, "Using largest suitable preview size: " + largestSize);
            return largestSize;
        }
        // If there is nothing at all suitable, return current preview size
        Camera.Size defaultPreview = parameters.getPreviewSize();
        Point defaultSize = new Point(defaultPreview.width, defaultPreview.height);
        return defaultSize;
    }


    /**
     * function: 打印图像预览数据
     *
     * @param supportedPreviewSizes
     */
    protected void onPrintPreviewData(List<Camera.Size> supportedPreviewSizes) {
        if (Log.isLoggable(TAG, Log.INFO)) {
            StringBuilder previewSizesString = new StringBuilder();
            for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
                previewSizesString.append(supportedPreviewSize.width)
                        .append('x')
                        .append(supportedPreviewSize.height)
                        .append(' ');
            }
            Logger.i(TAG, "Supported preview sizes: " + previewSizesString);
        }
    }


    /**
     * function: 过滤删除不合适的数据大小
     *
     * @param screenResolution
     * @param supportedPreviewSizes
     * @return {@link Point}.
     */
    protected Point onFilterUnsuitableSize(List<Camera.Size> supportedPreviewSizes, Point screenResolution) {
        double screenAspectRatio = (double) screenResolution.x / (double) screenResolution.y;
        Iterator<Camera.Size> it = supportedPreviewSizes.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewSize = it.next();
            int realWidth = supportedPreviewSize.width;
            int realHeight = supportedPreviewSize.height;
            if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }
            boolean isCandidatePortrait = realWidth < realHeight;
            int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;

            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }
            if (maybeFlippedWidth == screenResolution.x && maybeFlippedHeight == screenResolution.y) {
                Point exactPoint = new Point(realWidth, realHeight);
                Logger.i(TAG, "Found preview size exactly matching screen size: " + exactPoint);
                return exactPoint;
            }
        }
        return null;
    }


    /**
     * 对预览数据进行排序
     *
     * @param supportedPreviewSizes
     * @return {@link Point}.
     */
    protected void onSortPreviewSizes(List<Camera.Size> supportedPreviewSizes) {
        Collections.sort(supportedPreviewSizes, (a, b) -> {
            int aPixels = a.height * a.width;
            int bPixels = b.height * b.width;
            if (bPixels < aPixels) {
                return -1;
            }
            if (bPixels > aPixels) {
                return 1;
            }
            return 0;
        });
    }
}
