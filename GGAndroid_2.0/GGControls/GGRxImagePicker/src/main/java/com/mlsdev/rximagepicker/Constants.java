package com.mlsdev.rximagepicker;

/**
 * <p>
 * 常量
 * </p>
 * Created by leo on 2018/7/5 下午7:15
 */
public class Constants {
    /**
     * key
     */
    public static final String KEY_CHOOSE_IMAGE_MODE = "choose_image_mode";//选择图片的方式。相机/文件、图库
    public static final String KEY_CAMERA_PICTURE_URI = "cameraPictureUrl";

    /**
     * request_code
     */
    public static final int REQUEST_CODE_PICK_PHOTO = 100;//选取照片数据
    public static final int REQUEST_CODE_TAKE_PHOTO = 101;//拍照并或者返回的照片数据

    /**
     * Scheme
     */
    public static final String SCHEME_CONTENT = "content";
    public static final String SCHEME_FILE = "file";
}
