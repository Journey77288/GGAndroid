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
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.ganguo.scanner.CodeHelper;
import io.ganguo.scanner.bean.ScannerConfig;
import io.ganguo.scanner.callback.ScanCallback;
import io.ganguo.scanner.core.Config;
import io.ganguo.scanner.core.Image;
import io.ganguo.scanner.core.ImageScanner;
import io.ganguo.scanner.core.Symbol;
import io.ganguo.utils.util.Screens;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.Systems;
import io.ganguo.utils.util.log.Logger;

/**
 * <p>
 * 解析从相机获得到的图像数据
 * <p/>
 */
class CameraScanAnalysis implements Camera.PreviewCallback, Runnable {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ImageScanner imageScanner;
    private Handler handler;
    private ScanCallback mCallback;
    private boolean allowAnalysis = true;
    private boolean isOnlyScanCenter;//是否仅仅识别相机中间部分内容
    private int scanWidth;//识别范围宽度，isOnlyScanCenter==true时，有效
    private int scanHeight;//识别范围高度，isOnlyScanCenter==true时，有效
    @ScannerConfig.ScanType
    private int scanType = ScannerConfig.TYPE_ALL;//识别所有类型
    private Image barcode;
    private float cameraWidth;
    private float cameraHeight;


    protected CameraScanAnalysis(Context context) {
        handler = createMainHandler();
        cameraWidth = Screens.getScreenWidth(context);
        cameraWidth = Screens.getScreenHeight(context);
    }


    /**
     * function：config ImageScanner
     */
    protected void onImgScannerConfig() {
        if (scanType == ScannerConfig.TYPE_QR_CODE) {
            onScannerQRcodeMode();
        } else if (scanType == ScannerConfig.TYPE_BAR_CODE) {
            onScannerBarCodeMode();
        } else if (scanType == ScannerConfig.TYPE_CUSTOM) {
            onScannerCustomCodeMode();
        } else {
            onScannerAllCodeMode();
        }
    }

    /**
     * function：配置相机扫描模式--自定义码率格式
     */
    protected void onScannerCustomCodeMode() {
        ImageScanner imageScanner = getImageScanner();
        imageScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
        imageScanner.setConfig(Symbol.NONE, Config.ENABLE, 1);
    }


    /**
     * function：配置相机扫描模式--全部
     */
    protected void onScannerAllCodeMode() {
        ImageScanner imageScanner = getImageScanner();
        imageScanner.setConfig(Symbol.NONE, Config.X_DENSITY, 3);
        imageScanner.setConfig(Symbol.NONE, Config.Y_DENSITY, 3);
    }


    /**
     * function：配置相机扫描模式--二维码
     */
    protected void onScannerQRcodeMode() {
        ImageScanner imageScanner = getImageScanner();
        imageScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
        imageScanner.setConfig(Symbol.QRCODE, Config.ENABLE, 1);
    }


    /**
     * function：配置相机扫描模式--条形码
     */
    protected void onScannerBarCodeMode() {
        ImageScanner imageScanner = getImageScanner();
        imageScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
        imageScanner.setConfig(Symbol.CODE128, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.CODE39, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.EAN13, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.EAN8, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.UPCA, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.UPCE, Config.ENABLE, 1);
    }


    void setScanCallback(ScanCallback callback) {
        this.mCallback = callback;
    }

    void onStop() {
        this.allowAnalysis = false;
    }

    void onStart() {
        this.allowAnalysis = true;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (!allowAnalysis) {
            return;
        }
        allowAnalysis = false;
        Camera.Size size = camera.getParameters().getPreviewSize();
        barcode = new Image(size.width, size.height, "Y800");
        barcode.setData(data);
        if (isOnlyScanCenter) {
            int cropWidth = (int) (scanWidth * (size.height / cameraWidth));
            int cropHeight = (int) (scanHeight * (size.width / cameraHeight));
            int cropX = size.width / 2 - cropHeight / 2;
            int cropY = size.height / 2 - cropWidth / 2;
            barcode.setCrop(cropX, cropY, cropHeight, cropWidth);
        }
        executorService.execute(this);
    }

    /**
     * function：解析获取相机扫描的数据
     */
    @Override
    public void run() {
        int result = getImageScanner().scanImage(barcode);
        String resultStr = "";
        if (result != 0) {
            resultStr = CodeHelper.getScannerResults(getImageScanner());
        }
        if (Strings.isNotEmpty(resultStr)) {
            Message message = handler.obtainMessage();
            message.obj = resultStr;
            message.sendToTarget();
        } else {
            allowAnalysis = true;
        }
    }


    /**
     * function：get ImageScanner
     *
     * @return
     */
    public ImageScanner getImageScanner() {
        if (imageScanner == null) {
            imageScanner = new ImageScanner();
        }
        return imageScanner;
    }


    /**
     * function：create Main Handler
     *
     * @return
     */
    protected Handler createMainHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mCallback != null) {
                    mCallback.onScanResult((String) msg.obj);
                }
            }
        };
    }

    /**
     * function：设置扫码类型
     *
     * @param scanType
     */
    public CameraScanAnalysis setScanType(@ScannerConfig.ScanType int scanType) {
        this.scanType = scanType;
        onImgScannerConfig();
        return this;
    }

    public CameraScanAnalysis setOnlyScanCenter(boolean onlyScanCenter) {
        isOnlyScanCenter = onlyScanCenter;
        return this;
    }

    public CameraScanAnalysis setScanWidth(int scanWidth) {
        this.scanWidth = scanWidth;
        return this;
    }

    public CameraScanAnalysis setScanHeight(int scanHeight) {
        this.scanHeight = scanHeight;
        return this;
    }

    public CameraScanAnalysis setCameraWidth(float cameraWidth) {
        this.cameraWidth = cameraWidth;
        return this;
    }

    public CameraScanAnalysis setCameraHeight(float cameraHeight) {
        this.cameraHeight = cameraHeight;
        return this;
    }

    public boolean isOnlyScanCenter() {
        return isOnlyScanCenter;
    }

    public int getScanWidth() {
        return scanWidth;
    }

    public int getScanHeight() {
        return scanHeight;
    }
}