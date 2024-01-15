package io.ganguo.picker.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.picker.R
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/22
 *     desc   : PickerActivity - RecyclerView - Item View - 实际显示的View
 * </pre>
 */
/**
 * PickerActivity - RecyclerView - Item View - 实际显示的View
 * 把一个FrameLayout+子视图封装成一个View
 * @param
 * @see
 * @author Raynor
 * @property [media] 当前需要显示的媒体资源
 * @property [preMedia] 上一次[bindMedia]时显示的媒体资源
 */
class MediaView(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) : SquareFrameLayout(context, attr, defStyleAttr), View.OnClickListener {

    var thumbnail: ImageView? = null
    var audio: ImageView? = null
    var checkView: CheckView? = null
    var gifTag: ImageView? = null
    var videoDuration: TextView? = null

    var media: Media? = null
    var preMedia: Media? = null

    var resizeWidth = 0
    var placeholderDrawable: Drawable? = null
    var isCheckViewCountable = false
    var viewHolder: RecyclerView.ViewHolder? = null
    var listener: MediaViewClickListener? = null

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet? = null) : this(context, attr, 0)

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.media_grid_content, this, true)

        thumbnail = findViewById<ImageView>(R.id.media_thumbnail)
        audio = findViewById<ImageView>(R.id.media_audio)
        checkView = findViewById<CheckView>(R.id.check_view)
        gifTag = findViewById<ImageView>(R.id.gif)
        videoDuration = findViewById<TextView>(R.id.video_duration)

        thumbnail?.scaleType = ImageView.ScaleType.CENTER_CROP
        thumbnail?.setOnClickListener(this)
        checkView?.setOnClickListener(this)

    }

    /**
     * 点击事件之后再处理
     */
    override fun onClick(v: View?) {
        if (v == thumbnail) {
            listener?.onMediaViewThumbnailClicked(thumbnail!!, media!!, viewHolder!!)
        } else if (v == checkView) {
            listener?.onMediaViewCheckViewClicked(checkView!!, media!!, viewHolder!!)
        }
    }

    fun bindMedia(media: Media) {
        this.media = media
        initGifTag()
        initImage()
        initDuration()
    }

    private fun initGifTag() {
        gifTag?.visibility = if (media?.isGif() == true) View.VISIBLE else View.GONE
    }


    fun initCheckView() {
        checkView?.setCountable(isCheckViewCountable)
    }

    fun setCheckEnable(enabled: Boolean) {
        checkView?.isEnabled = enabled
    }

    fun setChecked(isChecked: Boolean) {
        checkView?.setChecked(isChecked)
    }

    fun setCheckedNum(checkedNum: Int) {
        checkView?.setCheckedNum(checkedNum)
    }

    /**
     * 初始化视频时长TextView
     */
    private fun initDuration() {
        if (media?.isVideo() == true || media?.isAudio() == true) {
            videoDuration?.isVisible = true
            videoDuration?.text = DateUtils.formatElapsedTime(media!!.duration / 1000)
        } else {
            videoDuration?.isVisible = false
        }
    }

    /**
     * 初始化预览图
     * 使用这种方式 防止在选择视频时 notifyDataSetChanged 导致所有视频重新加载封面
     * 因为coil可能不支持视频封面的缓存
     */
    private fun initImage() {
        if (media != preMedia) {
            when {
                media?.isGif() == true -> {
                    audio?.isVisible = false
                    PickerSpec.imageEngine.loadGifThumbnail(context, resizeWidth, placeholderDrawable, thumbnail, media?.uri)
                }
                media?.isAudio() == true -> {
                    audio?.isVisible = true
                    thumbnail?.setImageDrawable(null)
                }
                else -> {
                    audio?.isVisible = false
                    if (media?.isVideo() == true) {
                        PickerSpec.imageEngine.loadVideoThumbnail(context, resizeWidth, placeholderDrawable, thumbnail, media?.uri)
                    } else {
                        PickerSpec.imageEngine.loadThumbnail(context, resizeWidth, placeholderDrawable, thumbnail, media?.uri)
                    }
                }
            }
            preMedia = media
        }
    }

    /**
     * MediaView的点击回调接口
     */
    interface MediaViewClickListener {
        /**
         * 图片缩略图点击回调
         */
        fun onMediaViewThumbnailClicked(imageView: ImageView, media: Media, viewHolder: RecyclerView.ViewHolder)

        /**
         * 图片选择按钮（圈）点击回调
         */
        fun onMediaViewCheckViewClicked(checkView: CheckView, media: Media, viewHolder: RecyclerView.ViewHolder)
    }


}