package io.ganguo.single

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import io.ganguo.rxresult.GGActivityResultLauncher
import io.ganguo.utils.Intents
import io.ganguo.utils.URIs
import io.ganguo.utils.cursorImageFile
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.functions.Functions
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File


/**
 * RxImagePickerActivity 一个透明主题的Activity，用来实现onActivityResult回调处理
 * update by leo on 2020/05/11.
 */
class ImageChooserActivity : AppCompatActivity() {
    private var takePhotoUri: Uri? = null
    private var pickMode = ImageChooseMode.PICK_PHOTO
    private var disposable: Disposable? = null
    private val takePhotoLauncher = GGActivityResultLauncher(this, ActivityResultContracts.TakePicture())
    private val pickPhotoLauncher = GGActivityResultLauncher(this, ActivityResultContracts.StartActivityForResult())

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
        outState?.putParcelable(ImageChooser.KEY_TAKE_PHOTO_URI, takePhotoUri)
    }

    /**
     * 恢复拍照保存路径Uri
     * 屏幕方向恢复后，从onRestoreInstanceState函数恢复数据
     * @param savedInstanceState
     */
    private fun onRestorePictureUri(savedInstanceState: Bundle?) {
        savedInstanceState?.getParcelable<Parcelable>(ImageChooser.KEY_TAKE_PHOTO_URI)
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
            ImageChooser.SCHEME_CONTENT -> file = photoUri.cursorImageFile(this)
            ImageChooser.SCHEME_FILE -> file = File(photoUri.path.orEmpty())
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
                    it.cursorImageFile(this)!!
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    ImageChooser.get().onImagePicked(it)
                }
                .doOnError {
                    Log.d(javaClass.simpleName + " onHandleCameraImgFile:", it.message.orEmpty())
                }
                .doOnComplete {
                    ImageChooser.release()
                    finish()
                }
                .doFinally {
                    disposable?.dispose()
                }
                .subscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER)
    }

    /**
     * 根据key处理照片选择方式 -> 拍照/图库
     */
    private fun onStartPickerIntent() {
        pickMode = intent.getSerializableExtra(ImageChooser.KEY_PICKER_MODE) as ImageChooseMode
        when (pickMode) {
            ImageChooseMode.TAKE_PHOTO -> {
                takePhoto()
            }
            ImageChooseMode.PICK_PHOTO -> {
                pickPhoto()
            }
            ImageChooseMode.PICK_VIDEO -> {
                pickVideo()
            }
        }
    }

    /**
     * 调用系统相机进行拍照
     */
    private fun takePhoto() {
        takePhotoUri = URIs.createNewPhotoUri(this)
        takePhotoUri?.let { uri ->
            takePhotoLauncher.launch(uri) { isResultOk ->
                if (isResultOk) {
                    onHandleTakePhotoFile(uri)
                } else {
                    finish()
                }
            }
        }
    }

    /**
     * 调用系统相册进行图片选择
     */
    private fun pickPhoto() {
        val intent = Intents.createPickPhotoIntent()
        pickPhotoLauncher.launch(intent) {
            if (it.resultCode == Activity.RESULT_OK) {
                val file = onGalleryPhotoUriToFile(it.data)
                ImageChooser.get().onImagePicked(file)
                ImageChooser.release()
            }
            finish()
        }
    }

    /**
     * 调用系统相册进行视频选择
     */
    private fun pickVideo() {
        val intent = Intents.createPickVideoIntent()
        pickPhotoLauncher.launch(intent) {
            if (it.resultCode == Activity.RESULT_OK) {
                val file = onGalleryPhotoUriToFile(it.data)
                ImageChooser.get().onImagePicked(file)
                ImageChooser.release()
            }
            finish()
        }
    }
}