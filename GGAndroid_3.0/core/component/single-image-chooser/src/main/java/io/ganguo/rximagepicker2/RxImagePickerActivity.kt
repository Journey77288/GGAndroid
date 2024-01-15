package io.ganguo.rximagepicker2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import io.ganguo.log.core.Logger
import io.ganguo.utils.util.Files
import io.ganguo.utils.util.Images
import io.ganguo.utils.util.URIs
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * RxImagePickerActivity 一个透明主题的Activity，用来实现onActivityResult回调处理
 * update by leo on 2020/05/11.
 */
class RxImagePickerActivity : AppCompatActivity() {
    private var takePhotoUri: Uri? = null
    private var pickMode = RxImagePickerMode.PHOTO_PICK
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onStartPickerIntent()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        onSavePictureUri(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        onRestorePictureUri(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        onStartPickerIntent()
    }

    /**
     * 保存拍照保存路径Uri
     * 拍照可能会旋转屏幕，导致Activity重新跑声明周期，所以需要onSaveInstanceState函数保存数据
     * @param outState
     */
    private fun onSavePictureUri(outState: Bundle?) {
        outState?.putParcelable(RxImagePicker.KEY_TAKE_PHOTO_URI, takePhotoUri)
    }

    /**
     * 恢复拍照保存路径Uri
     * 屏幕方向恢复后，从onRestoreInstanceState函数恢复数据
     * @param savedInstanceState
     */
    private fun onRestorePictureUri(savedInstanceState: Bundle?) {
        savedInstanceState?.getParcelable<Parcelable>(RxImagePicker.KEY_TAKE_PHOTO_URI)
    }

    /**
     * onActivityResult 处理拍照/选择图片数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            finish()
            return
        }
        when (requestCode) {
            RxImagePickerMode.PHOTO_PICK.value -> {
                val file = onGalleryPhotoUriToFile(data)
                RxImagePicker.get().onImagePicked(file)
                finish()
            }
            RxImagePickerMode.PHOTO_TAKE.value -> {
                takePhotoUri?.let {
                    onHandleTakePhotoFile(it)
                }
            }
        }
    }

    /**
     * 处理从图库选中的的图片
     * @param data
     * @return
     */
    private fun onGalleryPhotoUriToFile(data: Intent?): File? {
        var file: File? = null
        if (data == null || data.data == null) {
            return null
        }
        val photoUri = data.data
        when (photoUri!!.scheme) {
            RxImagePicker.SCHEME_CONTENT -> file = Files.cursorImageFile(this, photoUri)
            RxImagePicker.SCHEME_FILE -> file = File(photoUri.path.orEmpty())
        }
        return file
    }

    /**
     * 处理拍照后，返回照片文件Uri
     * @param uri
     */
    private fun onHandleTakePhotoFile(uri: Uri) {
        disposable = Observable
                .just(uri)
                .subscribeOn(Schedulers.io())
                .map {
                    val filePath = RxImagePicker.get().saveImagePath
                    URIs.uriToFile(this, filePath, it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    RxImagePicker.get().onImagePicked(it)
                }
                .doOnError {
                    Logger.d(javaClass.simpleName + " onHandleCameraImgFile:" + it.message)
                }
                .doOnComplete { finish() }
                .doFinally {
                    disposable?.dispose()
                }
                .subscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER)
    }

    /**
     * 根据key处理照片选择方式 -> 拍照/图库
     */
    private fun onStartPickerIntent() {
        pickMode = intent.getSerializableExtra(RxImagePicker.KEY_PICKER_MODE) as RxImagePickerMode
        when (pickMode) {
            RxImagePickerMode.PHOTO_TAKE -> {
                takePhotoUri = Images.takePhoto(this, RxImagePickerMode.PHOTO_TAKE.value)
            }
            RxImagePickerMode.PHOTO_PICK -> {
                Images.takePick(this, RxImagePickerMode.PHOTO_PICK.value)
            }
        }
    }


}