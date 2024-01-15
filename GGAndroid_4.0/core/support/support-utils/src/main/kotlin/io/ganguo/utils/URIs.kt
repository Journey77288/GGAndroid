package io.ganguo.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.text.SimpleDateFormat
import java.util.*

/**
 * <pre>
 *     author : leo
 *     time   : 2020/06/20
 *     desc   : Uri Tools
 * </pre>
 */
object URIs {


    /**
     * 在创建一个系统相册Uri，且拍完照片可以在系统相册中找到
     * @param context Context
     * @return Uri?
     */
    @JvmStatic
    fun createNewPhotoUri(context: Context): Uri? {
        val contentResolver = context.contentResolver
        val cv = ContentValues()
        val timeStamp =
            SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                .format(Date())
        cv.put(MediaStore.Images.Media.TITLE, timeStamp)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
    }
}