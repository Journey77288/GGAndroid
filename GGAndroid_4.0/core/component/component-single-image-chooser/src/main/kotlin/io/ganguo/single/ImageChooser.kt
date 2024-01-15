package io.ganguo.single

import android.app.Activity
import android.content.Context
import android.content.Intent
import io.ganguo.PermissionsGather
import io.ganguo.permission.checkPermissions
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.io.File

/**
 * 相机/图片选择工具类
 * update by leo on 2018/07/06.
 * @property [publishSubject] 用于订阅图片选择结果
 * @property [chooseMode] 图片选择模式
 * @property [saveImagePath] 拍照，保存文件路径
 */
class ImageChooser private constructor() {
    private var publishSubject: PublishSubject<File>? = null
    private var chooseMode = ImageChooseMode.PICK_PHOTO
    private var saveImagePath: String? = null

    /**
     * 选择图片完成
     * @param imgFile
     */
    internal fun onImagePicked(imgFile: File?) {
        if (publishSubject != null && imgFile != null) {
            publishSubject!!.onNext(imgFile)
            publishSubject!!.onComplete()
        }
    }

    /**
     * 选择图片
     * @param context
     * @return
     */
    internal fun start(context: Context): Observable<File> = let {
        checkPermission(context as Activity)
        checkSaveImagePath()
        createImagePickerIntent(context).let {
            context.startActivity(it)
        }
        publishSubject = PublishSubject.create()
        publishSubject!!
    }


    /**
     * 检测相机权限
     * @return
     */
    private fun checkPermission(activity: Activity) {
        if (chooseMode == ImageChooseMode.TAKE_PHOTO) {
            require(activity.checkPermissions(PermissionsGather.CAMERA)) {
                javaClass.simpleName + "Please grant { READ_EXTERNAL_STORAGE、WRITE_EXTERNAL_STORAGE、CAMERA } permission"
            }
        }
        if (chooseMode == ImageChooseMode.PICK_PHOTO || chooseMode == ImageChooseMode.PICK_VIDEO) {
            require(activity.checkPermissions(PermissionsGather.STORAGE_IMAGE)) {
                javaClass.simpleName + "Please grant { READ_EXTERNAL_STORAGE、WRITE_EXTERNAL_STORAGE } permission"
            }
        }
    }

    /**
     * 检查压缩图片保存路径是否有效
     * @return
     */
    private fun checkSaveImagePath() {
        if (chooseMode == ImageChooseMode.PICK_PHOTO || chooseMode == ImageChooseMode.PICK_VIDEO) {
            return
        }
        require(saveImagePath.orEmpty().isNotEmpty()) {
            javaClass.simpleName + " must set compressImagePath before request image"
        }
        require(File(saveImagePath.orEmpty()).exists()) {
            javaClass.simpleName + " " + saveImagePath + "  The file path does not exist"
        }
    }

    /**
     * intent create
     * @param context
     * @return
     */
    private fun createImagePickerIntent(context: Context?) = let {
        val intent = Intent(context, ImageChooserActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(KEY_PICKER_MODE, chooseMode)

    }

    /**
     * 指定拍照后，保存图片的路径。如果是从文件夹/图库选择图片，则不需要指定保存文件路径。
     * @param saveImagePath
     * @return
     */
    private fun setSavePhotoPath(saveImagePath: String?) = apply {
        this.saveImagePath = saveImagePath
    }

    /**
     * 设置选择图片的方式
     * @param mode 选择图片模式，拍照/相册。
     * @return
     */
    private fun chooseMode(mode: ImageChooseMode = ImageChooseMode.PICK_PHOTO) = apply {
        this.chooseMode = mode
    }

    /**
     * RxImagePicker 伴生对象：用于处理一些静态实例
     */
    companion object {
        /**
         * key
         * @see [KEY_PICKER_MODE] 选择图片的方式，key
         * @see [KEY_TAKE_PHOTO_URI] 拍照保存Uri路径，key
         */
        internal const val KEY_PICKER_MODE = "choose_picker_mode" //选择图片的方式。相机/文件、图库
        internal const val KEY_TAKE_PHOTO_URI = "take_photo_uri"

        /**
         * file scheme
         * @see [SCHEME_CONTENT] Uri类型路径，一般为contentProvider访问路径
         * @see [SCHEME_FILE] 普通文件
         */
        internal const val SCHEME_CONTENT = "content"
        internal const val SCHEME_FILE = "file"

        /**
         * ImageChooser Instance
         */
        private var instance: ImageChooser? = null

        /**
         * 从图库选择照片
         * @return
         */
        @JvmStatic
        fun pickPhoto(context: Context): Observable<File> {
            instance = ImageChooser()
            instance!!.chooseMode = ImageChooseMode.PICK_PHOTO
            return instance!!.start(context)
        }


        /**
         * 拍照
         * @param context Context
         * @param savePhotoPath String
         * @return Observable<File>
         */
        @JvmStatic
        fun takePhoto(context: Context, savePhotoPath: String): Observable<File> {
            instance = ImageChooser()
            instance!!.chooseMode = ImageChooseMode.TAKE_PHOTO
            instance!!.setSavePhotoPath(savePhotoPath)
            return instance!!.start(context)
        }

        /**
         * 从图库选择视频
         * @param context Context
         * @return Observable<File>
         */
        @JvmStatic
        fun pickVideo(context: Context): Observable<File> {
            instance = ImageChooser()
            instance!!.chooseMode = ImageChooseMode.PICK_VIDEO
            return instance!!.start(context)
        }

        /**
         * get Instance
         * @return ImageChooser
         */
        internal fun get(): ImageChooser {
            return instance!!
        }

        /**
         * 资源释放
         */
        internal fun release() {
            instance = null
        }
    }

}
