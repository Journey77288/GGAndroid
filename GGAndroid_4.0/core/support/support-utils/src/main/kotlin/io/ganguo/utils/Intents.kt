package io.ganguo.utils

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/20
 *     desc   :
 * </pre>
 */
object Intents {


    /**
     * 1、创建一个拍照intent
     * 2、在回调处 ContentResolver.openOutputStream(uri)
     * @param uri Uri?
     * @return Intent?
     */
    @JvmStatic
    fun createTakePhotoIntent(uri: Uri?): Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
        intent.putExtra("noFaceDetection", true)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        return intent
    }

    /**
     * 1、创建一个选取照片intent
     * 2、挑选图片在4.4以下处理会有所不同，在onActivityResult那里要区分处理
     * @return Intent?
     */
    @JvmStatic
    fun createPickPhotoIntent(): Intent {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        return intent
    }

    /**
     * 1、创建一个选择视频intent
     * 2、挑选视频在4.4以下处理会有所不同，在onActivityResult哪里要区分处理
     * @return Intent?
     */
    @JvmStatic
    fun createPickVideoIntent(): Intent {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        return intent
    }
}