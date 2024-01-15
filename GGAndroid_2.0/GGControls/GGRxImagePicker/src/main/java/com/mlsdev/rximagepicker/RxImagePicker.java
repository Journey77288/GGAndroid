package com.mlsdev.rximagepicker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.ganguo.utils.util.Files;
import io.ganguo.utils.util.Strings;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * function：相机/图片选择工具类
 * update by leo on 2018/07/06.
 */
public class RxImagePicker {
    private PublishSubject<File> publishSubject;
    @ChooseMode
    private int chooseMode = ChooseMode.CAMERA;//选择图片的方式
    private String saveImagePath;//拍照后，保存文件的路径
    @StringRes
    private int notPermissionHintRes = R.string.rationale_dialog_rx_picker_msg;

    /**
     * function：单例，线程安全
     *
     * @return
     */
    public static RxImagePicker get() {
        return LazyHolder.holder;
    }

    static class LazyHolder {
        volatile static RxImagePicker holder = new RxImagePicker();
    }

    /**
     * function：选择图片完成
     *
     * @param imgFile
     */
    public void onImagePicked(File imgFile) {
        if (publishSubject != null) {
            publishSubject.onNext(imgFile);
            publishSubject.onComplete();
        }
    }

    /**
     * function：选择图片
     *
     * @param context
     * @return
     */
    public Observable<File> start(Context context) {
        checkSaveImagePath();
        publishSubject = PublishSubject.create();
        Intent intent = createImagePickerIntent(context);
        context.startActivity(intent);
        return publishSubject;
    }


    /**
     * function：检查压缩图片保存路径是否有效
     *
     * @return
     */
    protected RxImagePicker checkSaveImagePath() {
        if (chooseMode == ChooseMode.GALLERY) {
            return this;
        }
        if (Strings.isEmpty(saveImagePath)) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " must set compressImagePath before request image");
        }
        if (Files.checkFileExists(saveImagePath)) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " " + saveImagePath + "  The file path does not exist");
        }
        return this;
    }


    /**
     * function：intent create
     *
     * @param context
     * @return
     */
    public Intent createImagePickerIntent(Context context) {
        Intent intent = new Intent(context, RxImagePickerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.KEY_CHOOSE_IMAGE_MODE, chooseMode);
        return intent;
    }


    /**
     * function：get saveImagePath
     *
     * @return
     */
    public String getSaveImagePath() {
        return saveImagePath;
    }

    /**
     * function：指定拍照后，保存图片的路径。如果是从文件夹/图库选择图片，则不需要指定保存文件路径。
     *
     * @param saveImagePath
     * @return
     */
    public RxImagePicker setSaveImagePath(String saveImagePath) {
        this.saveImagePath = saveImagePath;
        return this;
    }


    /**
     * function：get chooseMode
     *
     * @return
     */
    @ChooseMode
    public int getChooseMode() {
        return chooseMode;
    }

    /**
     * function：设置选择图片的方式
     *
     * @param chooseMode
     * @return
     */
    public RxImagePicker setChooseMode(@ChooseMode int chooseMode) {
        this.chooseMode = chooseMode;
        return this;
    }


    /**
     * <p>
     * 设置没有权限提示语
     * <p/>
     */
    public RxImagePicker setNotPermissionHintRes(@StringRes int noPermissionHintRes) {
        this.notPermissionHintRes = noPermissionHintRes;
        return this;
    }

    public int getNotPermissionHintRes() {
        return notPermissionHintRes;
    }

    /**
     * Type
     * 获取图片的方式类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {ChooseMode.CAMERA, ChooseMode.GALLERY})
    public @interface ChooseMode {
        int CAMERA = 100;//通过相机
        int GALLERY = 200;//通过系统相册/文件夹
    }


}

