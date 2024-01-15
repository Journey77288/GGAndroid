package io.ganguo.rximagepicker2

import android.app.Activity
import android.content.Context
import android.content.Intent
import io.ganguo.utils.util.Files
import io.ganguo.utils.util.Permissions
import io.ganguo.utils.util.Strings
import io.ganguo.utils.util.checkPermissions
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.io.File

/**
 * 相机/图片选择工具类
 * update by leo on 2018/07/06.
 * @property [publishSubject] 用于订阅图片选择结果
 * @property [pickerModel] 图片选择模式
 * @property [saveImagePath] 拍照，保存文件路径
 */
class RxImagePicker private constructor() {
    private var publishSubject: PublishSubject<File>? = null
    private var pickerModel = RxImagePickerMode.PHOTO_PICK
    internal var saveImagePath: String? = null

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
    fun start(context: Context): Observable<File> = let {
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
        if (pickerModel == RxImagePickerMode.PHOTO_TAKE) {
            require(activity.checkPermissions(Permissions.CAMERA)) { javaClass.simpleName + "Please grant { READ_EXTERNAL_STORAGE、WRITE_EXTERNAL_STORAGE、CAMERA } permission" }
        }
        if (pickerModel == RxImagePickerMode.PHOTO_PICK) {
            require(activity.checkPermissions(Permissions.STORAGE)) { javaClass.simpleName + "Please grant { READ_EXTERNAL_STORAGE、WRITE_EXTERNAL_STORAGE } permission" }
        }
    }

    /**
     * 检查压缩图片保存路径是否有效
     * @return
     */
    private fun checkSaveImagePath() {
        if (pickerModel == RxImagePickerMode.PHOTO_PICK) {
            return
        }
        require(!Strings.isEmpty(saveImagePath)) { javaClass.simpleName + " must set compressImagePath before request image" }
        require(!Files.checkFileExists(saveImagePath)) { javaClass.simpleName + " " + saveImagePath + "  The file path does not exist" }
    }

    /**
     * intent create
     * @param context
     * @return
     */
    private fun createImagePickerIntent(context: Context?) = let {
        val intent = Intent(context, RxImagePickerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(KEY_PICKER_MODE, pickerModel)

    }

    /**
     * 指定拍照后，保存图片的路径。如果是从文件夹/图库选择图片，则不需要指定保存文件路径。
     * @param saveImagePath
     * @return
     */
    fun setSaveImagePath(saveImagePath: String?) = apply {
        this.saveImagePath = saveImagePath
    }

    /**
     * 设置选择图片的方式
     * @param mode 选择图片模式，拍照/相册。
     * @return
     */
    fun pickerMode(mode: RxImagePickerMode = RxImagePickerMode.PHOTO_PICK) = apply {
        this.pickerModel = mode
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
        const val KEY_PICKER_MODE = "choose_picker_mode" //选择图片的方式。相机/文件、图库
        const val KEY_TAKE_PHOTO_URI = "take_photo_uri"

        /**
         * file scheme
         * @see [SCHEME_CONTENT] Uri类型路径，一般为contentProvider访问路径
         * @see [SCHEME_FILE] 普通文件
         */
        const val SCHEME_CONTENT = "content"
        const val SCHEME_FILE = "file"

        /**
         * RxImagePicker Instance
         */
        private val INSTANCE by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RxImagePicker()
        }

        /**
         * 单例，懒加载模式
         * @return
         */
        fun get(): RxImagePicker {
            return INSTANCE
        }
    }

}