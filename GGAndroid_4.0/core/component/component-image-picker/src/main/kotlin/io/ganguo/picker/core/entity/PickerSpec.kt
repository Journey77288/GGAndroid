package io.ganguo.picker.core.entity

import android.app.Activity
import android.content.Intent
import android.util.Log
import io.ganguo.picker.MimeType
import io.ganguo.picker.R
import io.ganguo.picker.core.bean.PickerConstants
import io.ganguo.picker.core.entity.PickerSpec.imageEngine
import io.ganguo.picker.core.entity.PickerSpec.mediaTypeExclusive
import io.ganguo.picker.core.entity.PickerSpec.mimeTypes
import io.ganguo.picker.engine.ImageEngine
import io.ganguo.picker.ui.PickerActivity
import io.ganguo.rxresult.ActivityResult
import io.ganguo.rxresult.RxActivityResult
import io.ganguo.utils.Action1
import io.ganguo.utils.LocationHelper.Companion.checkInit
import io.reactivex.rxjava3.internal.functions.Functions

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/06
 *     desc   : Image Picker 配置文件
 * </pre>
 *
 * Image Picker 配置文件
 * @param
 * @see
 * @author Raynor
 * @property [imageEngine] 图片加载引擎
 * @property [mediaTypeExclusive] 表示是否允许同时选择图片与视频类型的文件
 * @property [mimeTypes] 允许选择的文件类型 设置支持的文件类型 选择器自己判断是图片或视频
 * @property [showSingleMediaType] 是否只支持一种媒体类型 和[mimeTypes]搭配使用
 * @property [thumbnailScale] 缩略图尺寸压缩参数 假设item image view 大小为x时 使用图片框架加载图片时 可以将图片继续压缩至thumbnailScale*x 以期节省内存
 * @property [spanCount] 一行显示几张图片
 * @property [capture] 是否允许拍照
 * @property [itemClickPreview] 是否允许点击Item预览
 * @property [handleNotification] 允许外部传入函数用以处理提示信息的展示逻辑 包括Toast/Dialog
 * @property [selectionNumLimit] 媒体项目选择数量限制
 * @property [countable] 是否允许选择多张图片
 * @property [styleRes] 主题
 */
object PickerSpec {
    lateinit var imageEngine: ImageEngine
        private set
    var mediaTypeExclusive: Boolean = false
    var mimeTypes: MutableSet<MimeType> = mutableSetOf()
        private set
    var showSingleMediaType = true
        private set
    var thumbnailScale: Double = 0.8
        private set
    var spanCount = 4
        private set
    var capture = true
        private set
    var itemClickPreview = false
        private set
    var handleNotification: ((IncapableCause) -> Unit)? = null
    var selectionNumLimit = 1
        private set(value) {
            countable = value > 1
            field = value
        }
    var countable = false
        private set
    var styleRes = R.style.Theme_Picker
        private set

    /**
     * 初始化
     *
     * @param engine ImageEngine
     * @param thumbnailScale Double 缩略图缩放比
     */
    fun init(engine: ImageEngine, thumbnailScale: Double = 0.8) {
        check(!::imageEngine.isInitialized) { "Already initialized" }
        this.imageEngine = engine
        this.thumbnailScale = thumbnailScale
    }

    fun reset() {
        mediaTypeExclusive = false
        mimeTypes.clear()
        showSingleMediaType = true
        thumbnailScale = 0.8
        spanCount = 4
        capture = true
        selectionNumLimit = 1
        countable = false
        styleRes = R.style.Theme_Picker
    }

    /**
     * 设置只显示图片类型
     * @param [enableGif] 是否支持GIF 目前默认不支持GIF
     */
    fun onlyShowImages(enableGif: Boolean = false) {
        showSingleMediaType = true
        mimeTypes.addAll(MimeType.ofImage())
        if (!enableGif) {
            mimeTypes.remove(MimeType.GIF)
        }
    }

    /**
     * 设置只显示视频类型
     */
    fun onlyShowVideos() {
        showSingleMediaType = true
        mimeTypes.addAll(MimeType.ofVideo())
    }

    /**
     * 设置只显示音频类型
     */
    fun onlyShowAudios() {
        showSingleMediaType = true
        mimeTypes.addAll(MimeType.ofAudio())
    }

    /**
     * 显示所有资源类型
     * @param [enableGif] 是否支持GIF
     */
    fun showImageAndVideos(enableGif: Boolean = false) {
        showSingleMediaType = false
        mimeTypes.addAll(MimeType.ofAll())
        if (!enableGif) {
            mimeTypes.remove(MimeType.GIF)
        }
    }

    /**
     * 判断是否只显示图片/视频/音频/GIF
     * 需要与[mimeTypes]和[showSingleMediaType]配合使用
     */
    fun isOnlyShowImages() = showSingleMediaType && MimeType.ofImage().containsAll(mimeTypes)
    fun isOnlyShowVideos() = showSingleMediaType && MimeType.ofVideo().containsAll(mimeTypes)
    fun isOnlyShowAudios() = showSingleMediaType && MimeType.ofAudio().containsAll(mimeTypes)
    fun isOnlyShowGif() = showSingleMediaType && MimeType.ofGif() == mimeTypes

    /**
     * 返回是否是单选模式
     */
    fun isSingleSelectionModeEnabled(): Boolean {
        return !countable && (isSingleSelection())
    }

    /**
     * 是否单选
     */
    fun isSingleSelection(): Boolean = let {
        selectionNumLimit == 1
    }

    /**
     * 选择图片
     *
     * @param activity Activity
     * @param imageNum Int                              选择图片数量上限
     * @param spanCount Int                             预览图列数
     * @param itemClickPreview Boolean                  是否支持点击预览
     * @param enableCapture Boolean                     是否支持拍照
     * @param enableGif Boolean                         是否支持选择gif格式图片
     * @param styleRes Int                              风格Res
     * @param callback Function1<ActivityResult, Unit>? 选择完成回调
     */
    fun pickImage(
        activity: Activity,
        imageNum: Int,
        spanCount: Int = 4,
        itemClickPreview: Boolean = false,
        enableCapture: Boolean = true,
        enableGif: Boolean = false,
        styleRes: Int = R.style.Theme_Picker,
        callback: Action1<ActivityResult>? = null
    ) {
        checkInit()
        reset()
        this.selectionNumLimit = imageNum
        this.spanCount = spanCount
        this.itemClickPreview = itemClickPreview
        this.capture = enableCapture
        this.styleRes = styleRes
        onlyShowImages(enableGif)
        val intent = Intent(activity, PickerActivity::class.java)
        RxActivityResult.startIntent(activity, intent, PickerConstants.PICK_IMAGE_REQUEST)
            ?.doOnNext { callback?.invoke(it) }
            ?.subscribe(Functions.emptyConsumer()) {
                it.printStackTrace()
                Log.e("Tag", "pickImage error: ${it.message}")
            }
    }

    /**
     * 选择视频
     *
     * @param activity Activity
     * @param videoNum Int                              选择视频数量上限
     * @param spanCount Int                             预览图列数
     * @param itemClickPreview Boolean                  是否支持点击预览
     * @param enableCapture Boolean                     是否支持摄影
     * @param styleRes Int                              风格Res
     * @param callback Function1<ActivityResult, Unit>? 选择完成回调
     */
    fun pickVideo(
        activity: Activity,
        videoNum: Int,
        spanCount: Int = 4,
        itemClickPreview: Boolean = false,
        enableCapture: Boolean = true,
        styleRes: Int = R.style.Theme_Picker,
        callback: Action1<ActivityResult>? = null
    ) {
        checkInit()
        reset()
        this.selectionNumLimit = videoNum
        this.spanCount = spanCount
        this.itemClickPreview = itemClickPreview
        this.capture = enableCapture
        this.styleRes = styleRes
        onlyShowVideos()
        val intent = Intent(activity, PickerActivity::class.java)
        RxActivityResult.startIntent(activity, intent, PickerConstants.PICK_VIDEO_REQUEST)
            ?.doOnNext { callback?.invoke(it) }
            ?.subscribe(Functions.emptyConsumer()) {
                it.printStackTrace()
                Log.e("Tag", "pickVideo error: ${it.message}")
            }
    }

    /**
     * 选择音频
     *
     * @param activity Activity
     * @param audioNum Int                              选择音频数量上限
     * @param spanCount Int                             预览图列数
     * @param itemClickPreview Boolean                  是否支持点击预览
     * @param styleRes Int                              风格Res
     * @param callback Function1<ActivityResult, Unit>? 选择完成回调
     */
    fun pickAudio(
        activity: Activity,
        audioNum: Int,
        spanCount: Int = 4,
        itemClickPreview: Boolean = false,
        styleRes: Int = R.style.Theme_Picker,
        callback: Action1<ActivityResult>? = null
    ) {
        checkInit()
        reset()
        this.selectionNumLimit = audioNum
        this.spanCount = spanCount
        this.itemClickPreview = itemClickPreview
        this.capture = false
        this.styleRes = styleRes
        onlyShowAudios()
        val intent = Intent(activity, PickerActivity::class.java)
        RxActivityResult.startIntent(activity, intent, PickerConstants.PICK_AUDIO_REQUEST)
            ?.doOnNext { callback?.invoke(it) }
            ?.subscribe(Functions.emptyConsumer()) {
                it.printStackTrace()
                Log.e("Tag", "pickAudio error: ${it.message}")
            }
    }

    /**
     * 选择图片或视频
     *
     * @param activity Activity
     * @param limitNum Int                              选择数量上限
     * @param spanCount Int                             预览图列数
     * @param itemClickPreview Boolean                  是否支持点击预览
     * @param enableCapture Boolean                     是否支持拍照/摄影
     * @param enableGif Boolean                         是否支持Gif图
     * @param styleRes Int                              风格Res
     * @param callback Function1<ActivityResult, Unit>? 选择完成回调
     */
    fun pickImageOrVideo(
        activity: Activity,
        limitNum: Int,
        spanCount: Int = 4,
        itemClickPreview: Boolean = false,
        enableCapture: Boolean = true,
        enableGif: Boolean = false,
        styleRes: Int = R.style.Theme_Picker,
        callback: Action1<ActivityResult>? = null
    ) {
        checkInit()
        reset()
        this.selectionNumLimit = limitNum
        this.spanCount = spanCount
        this.itemClickPreview = itemClickPreview
        this.capture = enableCapture
        this.styleRes = styleRes
        showImageAndVideos(enableGif)
        val intent = Intent(activity, PickerActivity::class.java)
        RxActivityResult.startIntent(activity, intent, PickerConstants.PICK_MEDIA_REQUEST)
            ?.doOnNext { callback?.invoke(it) }
            ?.subscribe(Functions.emptyConsumer()) {
                it.printStackTrace()
                Log.e("Tag", "pickImageAndVideo error: ${it.message}")
            }
    }

    /**
     * 检查初始化
     */
    private fun checkInit() {
        check(::imageEngine.isInitialized) {
            "You have to initialize `PickerSpec.init(imageEngine, thumbnailScale)` in Application first!!"
        }
    }
}