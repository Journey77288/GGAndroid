package com.mlsdev.rximagepicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.util.Files;
import io.ganguo.utils.util.Images;
import io.ganguo.utils.util.Permissions;
import io.ganguo.utils.util.URIs;
import io.ganguo.utils.util.log.Logger;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;


/**
 * function：RxImagePickerActivity 一个透明主题的Activity，用来实现onActivityResult回调处理
 * update by leo on 2018/07/06.
 */
public class RxImagePickerActivity extends Activity implements Permissions.onRequestPermissionListener {
    private Uri cameraPictureUri;
    @RxImagePicker.ChooseMode
    private int chooseMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        onSavePictureUri(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onRestorePictureUri(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        onHandlerIntent();
    }


    /**
     * function：打开该页面时，检查是否有相应权限
     * <p>
     * 权限获取成功后，则直接在成功回调函数中，执行打开相机/图库操作，否则关闭该页面
     * <p/>
     *
     * @param savedInstanceState
     */
    protected void checkPermissions(Bundle savedInstanceState) {
        chooseMode = getIntent().getIntExtra(Constants.KEY_CHOOSE_IMAGE_MODE, RxImagePicker.ChooseMode.CAMERA);
        if (savedInstanceState != null) {
            return;
        }
        ArrayList<String> permissions = new ArrayList<>();
        if (chooseMode == RxImagePicker.ChooseMode.CAMERA) {
            permissions.add(Permission.CAMERA);
        }
        permissions.add(Permission.READ_EXTERNAL_STORAGE);
        permissions.add(Permission.WRITE_EXTERNAL_STORAGE);
        Permissions.requestPermission(this, ResHelper.getString(R.string.rationale_dialog_rx_picker_msg), this, permissions.toArray(new String[]{}));

    }


    /**
     * function：保存cameraPictureUri
     * <p>
     * 拍照可能会旋转屏幕，导致Activity重新跑声明周期，所以需要onSaveInstanceState函数保存数据
     * <p/>
     *
     * @param outState
     */
    protected void onSavePictureUri(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(Constants.KEY_CAMERA_PICTURE_URI, cameraPictureUri);
        }
    }


    /**
     * function：恢复cameraPictureUri
     * <p>
     * 屏幕方向恢复后，从onRestoreInstanceState函数恢复数据
     * <p/>
     *
     * @param savedInstanceState
     */
    protected void onRestorePictureUri(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.getParcelable(Constants.KEY_CAMERA_PICTURE_URI);
        }
    }


    /**
     * function：onActivityResult 处理拍照/选择图片数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }
        switch (requestCode) {
            case Constants.REQUEST_CODE_PICK_PHOTO:
                RxImagePicker.get().onImagePicked(onFileFromGallery(data));
                finish();
                break;
            case Constants.REQUEST_CODE_TAKE_PHOTO:
                onHandleCameraImgFile(cameraPictureUri);
                break;
        }
    }


    /**
     * function：处理从图库选中的的图片
     *
     * @param data
     * @return
     */
    private File onFileFromGallery(Intent data) {
        File file = null;
        if (data == null || data.getData() == null) {
            return null;
        }
        Uri chooseUri = data.getData();
        switch (chooseUri.getScheme()) {
            case Constants.SCHEME_CONTENT:
                file = Files.cursorImageFile(this, chooseUri);
                break;
            case Constants.SCHEME_FILE:
                file = new File(chooseUri.getPath());
                break;
        }
        return file;
    }


    /**
     * function：处理拍照后，返回照片文件Uri
     *
     * @param uri
     */
    private void onHandleCameraImgFile(final Uri uri) {
        Observable
                .just(uri)
                .subscribeOn(Schedulers.io())
                .filter(uri12 -> uri12 != null)
                .map(uri1 -> {
                    String filePath = RxImagePicker.get().getSaveImagePath();
                    return URIs.uriToFile(RxImagePickerActivity.this, filePath, uri1);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(file -> RxImagePicker.get().onImagePicked(file))
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(getClass().getSimpleName() + " onHandleCameraImgFile:" + throwable.getMessage());
                    }
                })
                .doOnComplete(() -> finish())
                .subscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER);
    }


    /**
     * function：requestSuccess
     *
     * @param successPermissions
     */
    @Override
    public void onRequestSuccess(List<String> successPermissions) {
        onHandlerIntent();
    }


    /**
     * function：requestFailure
     *
     * @param failurePermissions
     */
    @Override
    public void onRequestFailure(List<String> failurePermissions) {
        finish();
    }


    /**
     * function：根据key处理照片选择方式 -> 拍照/图库
     */
    protected void onHandlerIntent() {
        chooseMode = getIntent().getIntExtra(Constants.KEY_CHOOSE_IMAGE_MODE, RxImagePicker.ChooseMode.CAMERA);
        switch (chooseMode) {
            case RxImagePicker.ChooseMode.CAMERA:
                cameraPictureUri = Images.takePhoto(this, Constants.REQUEST_CODE_TAKE_PHOTO);
                break;
            case RxImagePicker.ChooseMode.GALLERY:
                Images.takePick(this, Constants.REQUEST_CODE_PICK_PHOTO);
                break;
            default:
                finish();
                break;
        }
    }


    /**
     * function：Override finish
     */
    @Override
    public void finish() {
        super.finish();
    }


}