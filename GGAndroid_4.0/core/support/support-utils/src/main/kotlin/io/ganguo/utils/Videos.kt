package io.ganguo.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.DimenRes
import androidx.lifecycle.LifecycleCoroutineScope
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.UriUtils
import com.hw.videoprocessor.VideoProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import kotlin.math.min

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/04/27
 *     desc   : 视频相关工具类
 * </pre>
 * @property [MAX_BITRATE] 视频最大比特率
 * @property [MILLISECOND_TIMESTAMP] 毫秒时间戳
 * @property [VIDEO_MAX_WIDTH] 视频最大宽度
 * @property [VIDEO_MAX_HEIGHT] 视频最大高度
 */
object Videos {
    private const val MAX_BITRATE = 4097000
    private const val MILLISECOND_TIMESTAMP = 1000L
    private const val VIDEO_MAX_WIDTH = 2040
    private const val VIDEO_MAX_HEIGHT = 1080
    private var retriever: MediaMetadataRetriever? = null

    /**
     * 设置数据源
     *
     * @param uri Uri?
     */
    @Throws(IllegalArgumentException::class)
    fun setDataSource(context: Context, uri: Uri?) {
        retriever = MediaMetadataRetriever()
        retriever!!.setDataSource(context.applicationContext, uri)
    }

    /**
     * 获取指定时间戳的视频截图
     * 获取帧截图是耗时方法，建议在子线程运行
     *
     * @param context 上下文
     * @param position 时间戳位置
     * @param widthRes 帧截图宽度
     * @param heightRes 帧截图高度
     * @return Bitmap
     */
    fun getFrameBitmapAtTime(context: Context,
                             position: Int,
                             @DimenRes widthRes: Int,
                             @DimenRes heightRes: Int): Bitmap? {
        checkInit()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val width = context.resources.getDimensionPixelOffset(widthRes)
            val height = context.resources.getDimensionPixelOffset(heightRes)
            retriever!!.getScaledFrameAtTime(
                position * MILLISECOND_TIMESTAMP,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC,
                width,
                height
            )
        } else {
            retriever!!.getFrameAtTime(position * MILLISECOND_TIMESTAMP, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        }
    }

    /**
     * 检查是否已初始化
     */
    private fun checkInit() {
        checkNotNull(retriever) { "data source is empty, please set data source first" }
    }

    /**
     * 获取首帧图片
     *
     * @return Bitmap
     */
    fun getFirstFrameBitmap(): Bitmap? {
        checkInit()
        var bitmap: Bitmap? = null
        try {
            bitmap = retriever!!.frameAtTime
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            retriever!!.release()
        }
        return bitmap
    }

    /**
     * 获取视频时长
     *
     * @return Long
     */
    fun getDuration(): Long {
        checkInit()
        val time = retriever!!.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return time?.toLong() ?: 0
    }

    /**
     * 获取视频宽高
     * @return Pair<Long, Long>
     */
    fun getVideoSize(): Pair<Int, Int> {
        checkInit()
        val width = retriever!!.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        val height = retriever!!.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        //需要根据是否旋转过来获取准确宽高
        return when (getVideoRotation()) {
            90, 270 -> (height?.toInt() ?: 0) to (width?.toInt() ?: 0)
            else -> (width?.toInt() ?: 0) to (height?.toInt() ?: 0)
        }
    }

    /**
     * 获取视频旋转角度
     * @return Int
     */
    fun getVideoRotation(): Int {
        checkInit()
        val rotation = retriever!!.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
        return rotation?.toInt() ?: 0
    }

    /**
     * 释放资源
     */
    fun release() {
        retriever?.release()
        retriever = null
    }

    /**
     * 压缩视频
     *
     * @param context Context
     * @param scope LifecycleCoroutineScope
     * @param uri Uri
     * @param callback Function2<Boolean, String?, Unit>
     */
    fun compressVideo(
        context: Context,
        scope: LifecycleCoroutineScope,
        uri: Uri,
        callback: Action2<Boolean, String?>
    ) {
        val file = UriUtils.uri2File(uri)
        compressVideo(context, scope, file, callback)
    }

    /**
     * 压缩视频
     *
     * @param context Context
     * @param scope LifecycleCoroutineScope
     * @param file File?
     * @param callback Function2<Boolean, String?, Unit>
     */
    fun compressVideo(
        context: Context,
        scope: LifecycleCoroutineScope,
        file: File?,
        callback: Action2<Boolean, String?>
    ) {
        if (file == null || file.isNotExists()) {
            Log.w("Videos", "composed file is not exist")
            callback.invoke(false, null)
            return
        }
        val outputPath = "${context.cacheDir}/${System.currentTimeMillis()}.mp4"
        FileUtils.createOrExistsFile(outputPath)
        val bitrateRetriever = MediaMetadataRetriever()
        bitrateRetriever.setDataSource(file.path)
        val videoBitrate = bitrateRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)?.toInt() ?: 0
        var videoWidth = bitrateRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toInt()?: 0;//原视频宽度
        var videoHeight = bitrateRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toInt()?: 0;//原视频高度
        if (videoWidth > videoHeight) {
            //横屏视频超出默认宽度
            if (videoWidth > VIDEO_MAX_WIDTH) {
                val scale = (VIDEO_MAX_WIDTH / videoWidth);
                videoWidth = VIDEO_MAX_WIDTH;
                videoHeight *= scale;
            }
        } else {
            //竖屏视频超出默认高度
            if (videoHeight > VIDEO_MAX_HEIGHT) {
                val scale = (VIDEO_MAX_HEIGHT / videoHeight);
                videoHeight = VIDEO_MAX_HEIGHT;
                videoWidth *= scale;
            }
        }
        val bitrate = min(MAX_BITRATE, videoBitrate)// 限制视频压缩最大比特率
        scope.launch(Dispatchers.IO) {
            try {
                VideoProcessor.processor(context)
                    .input(file.absolutePath)
                    .output(outputPath)
                    .outWidth(videoWidth)
                    .outHeight(videoHeight)
                    .bitrate(bitrate)
                    .progressListener {
                        if (it == 1.0f) {
                            scope.launch(Dispatchers.Main) {
                                delay(600)
                                callback(true, outputPath)
                            }
                        }
                    }
                    .process()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Videos", "video compress error: ${e.message}")
                scope.launch(Dispatchers.Main) {
                    callback(false, null)
                }
            }
        }
    }
}