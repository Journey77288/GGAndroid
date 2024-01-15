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

import android.Manifest;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import io.ganguo.scanner.R;
import io.ganguo.scanner.bean.ScannerConfig;
import io.ganguo.scanner.callback.ScanCallback;
import io.ganguo.scanner.callback.SurfaceAdapterCallback;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.Permissions;
import io.ganguo.utils.util.Tasks;
import io.ganguo.utils.util.log.Logger;

/**
 * <p>
 * 自定义扫描相机
 * <p/>
 */
public class ScanCodeCamera extends FrameLayout implements Camera.AutoFocusCallback {
    private ScannerCameraManager cameraManager;
    private CameraScanAnalysis scanAnalysis;
    private SurfaceView surfaceView;
    @ScannerConfig.ScanType
    private int scanType = ScannerConfig.TYPE_ALL;//识别所有类型

    public ScanCodeCamera(@NonNull Context context) {
        this(context, null);
    }

    public ScanCodeCamera(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanCodeCamera(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
        initCamera();
    }


    /**
     * function:读取控件自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.zbar_view);
        scanType = a.getColor(R.styleable.zbar_view_scan_qr_type, ScannerConfig.TYPE_ALL);
        a.recycle();
    }


    /**
     * function：init Camera
     */
    protected void initCamera() {
        this.cameraManager = new ScannerCameraManager(getContext());
        this.scanAnalysis = new CameraScanAnalysis(getContext());
        this.scanAnalysis.setScanType(scanType);
    }

    /**
     * function：设置扫码类型
     *
     * @param scanType
     */
    public ScanCodeCamera setScanType(@ScannerConfig.ScanType int scanType) {
        this.scanAnalysis.setScanType(scanType);
        return this;
    }


    /**
     * function: Set Scan results callback.
     *
     * @param callback {@link ScanCallback}.
     * @return
     */
    public ScanCodeCamera setScanCallback(ScanCallback callback) {
        this.scanAnalysis.setScanCallback(callback);
        return this;
    }

    /**
     * function: Set Scan is onlyScanCenter.
     *
     * @param onlyScanCenter
     * @return
     */
    public ScanCodeCamera setOnlyScanCenter(boolean onlyScanCenter) {
        this.scanAnalysis.setOnlyScanCenter(onlyScanCenter);
        return this;
    }


    /**
     * function: Set Scan scanWidth.
     *
     * @param scanWidth
     * @return
     */
    public ScanCodeCamera setScanWidth(int scanWidth) {
        this.scanAnalysis.setScanWidth(scanWidth);
        return this;
    }

    /**
     * function: Set Scan scanHeight.
     *
     * @param scanHeight
     */
    public ScanCodeCamera setScanHeight(int scanHeight) {
        this.scanAnalysis.setScanHeight(scanHeight);
        return this;
    }

    /**
     * function：Camera start preview.
     */
    public void start() {
        if (!checkStartCamera()) {
            return;
        }
        initSurfaceView();
        Tasks.runOnThreadPool(() -> startCamera());
    }

    /**
     * function：Check Camera if startup conditions are met
     *
     * @return
     */
    protected boolean checkStartCamera() {
        if (!Permissions.hasSelfPermission(getContext(), Manifest.permission.CAMERA)) {
            ToastHelper.showMessage( "Failed to obtain camera permissions");
            return false;
        }
        checkOnlyScanCenter();
        return true;
    }


    /**
     * function：Camera start preview.
     */
    protected void startCamera() {
        try {
            cameraManager.openCamera();
            scanAnalysis.onStart();
            startCamera(surfaceView.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("startCamera:" + e.getStackTrace());
        }
    }


    /**
     * function：isOnlyScanCenter is true,Check that the property configuration is complete
     */
    protected void checkOnlyScanCenter() {
        if (!scanAnalysis.isOnlyScanCenter()) {
            return;
        }
        if (scanAnalysis.getScanWidth() <= 0) {
            throw new RuntimeException("Please set scanWidth property");
        }
        if (scanAnalysis.getScanHeight() <= 0) {
            throw new RuntimeException("Please set scanHeight property");
        }
    }


    /**
     * function：Start Camera  preview.
     *
     * @param holder
     */
    private void startCamera(SurfaceHolder holder) {
        try {
            cameraManager.startPreview(holder, scanAnalysis);
            cameraManager.autoFocus(ScanCodeCamera.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Camera pause preview.
     */
    public void pauseCamera() {
        Tasks.runOnThreadPool(() -> {
            pausePreview();
        });
    }


    /**
     * Camera pause preview.
     */
    public void stopCamera() {
        Tasks.runOnThreadPool(() -> {
            pausePreview();
            releaseSurfaceView();
        });
    }

    /**
     * Camera pause preview.
     */
    private void pausePreview() {
        removeCallbacks(autoFocusRun);
        cameraManager.stopPreview();
        scanAnalysis.onStop();
        cameraManager.closeDriver();
    }


    /**
     * function：打开闪光灯
     */
    public void onLight() {
        if (cameraManager == null) {
            return;
        }
        cameraManager.onLight();
    }

    /**
     * function：关闭闪光灯
     */
    public void offLight() {
        if (cameraManager == null) {
            return;
        }
        cameraManager.offLight();
    }

    /**
     * function：camera auto Focus Runnable
     */
    private Runnable autoFocusRun = new Runnable() {
        public void run() {
            cameraManager.autoFocus(ScanCodeCamera.this);
        }
    };

    /**
     * function：自动获取焦点回调函数
     *
     * @param camera
     * @param success
     */
    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        postDelayed(autoFocusRun, 1000);
    }

    /**
     * function：init SurfaceView
     *
     * @return
     */
    public SurfaceView initSurfaceView() {
        if (surfaceView == null) {
            surfaceView = createSurfaceView();
        }
        return surfaceView;
    }

    /**
     * function：释放SurfaceView资源，避免内存泄露
     */
    private void releaseSurfaceView() {
        if (surfaceView == null) {
            return;
        }
        if (surfaceView.getHolder() == null) {
            surfaceView = null;
            return;
        }
        if (surfaceView.getHolder().getSurface() == null) {
            return;
        }
        surfaceView.getHolder().getSurface().release();
        surfaceView = null;
    }


    /**
     * function：create SurfaceView and add  to ViewGroup
     *
     * @return
     */
    protected SurfaceView createSurfaceView() {
        SurfaceView surfaceView = new SurfaceView(getContext());
        addView(surfaceView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceAdapterCallback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (holder.getSurface() == null) {
                    return;
                }
                cameraManager.stopPreview();
                startCamera(holder);
            }
        });
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        return surfaceView;
    }


    @Override
    protected void onDetachedFromWindow() {
        pauseCamera();
        super.onDetachedFromWindow();
    }
}