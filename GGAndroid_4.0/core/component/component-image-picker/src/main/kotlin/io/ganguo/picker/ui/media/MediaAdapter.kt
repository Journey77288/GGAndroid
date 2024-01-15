package io.ganguo.picker.ui.media

import android.content.Context
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ganguo.picker.R
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.entity.IncapableCause
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.core.model.SelectedMediaModel
import io.ganguo.picker.ui.widget.CheckView
import io.ganguo.picker.ui.widget.MediaView

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/22
 *     desc   : 显示在PickerActivity的container内部的Fragment的RecyclerView的Adapter
 * </pre>
 *
 * 显示在PickerActivity的container内部的Fragment的RecyclerView的Adapter
 * @param
 * @see
 * @author Raynor
 * @property
 */
class MediaAdapter private constructor(cursor: Cursor? = null) : RecyclerViewCursorAdapter<RecyclerView.ViewHolder>(cursor), MediaView.MediaViewClickListener {

    private var recyclerView: RecyclerView? = null
    private var imageResize = 0
    private var placeholder: Drawable? = null
    var checkStateListener: CheckStateListener? = null
    var mediaClickListener: MediaClickListener? = null
    var captureClickListener: CaptureClickListener? = null
    lateinit var selectedMediaModel: SelectedMediaModel


    constructor(context: Context, selectedMediaModel: SelectedMediaModel, recyclerView: RecyclerView) : this(null) {
        this.recyclerView = recyclerView
        this.selectedMediaModel = selectedMediaModel

        val ta = context.theme.obtainStyledAttributes(intArrayOf(R.attr.item_placeholder_color))
        placeholder = ta.getDrawable(0)
        ta.recycle()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CAPTURE) {
            val v: View = LayoutInflater.from(parent.context).inflate(R.layout.photo_capture_item, parent, false)
            CaptureViewHolder(v)
        } else {
            val v: View = LayoutInflater.from(parent.context).inflate(R.layout.media_grid_item, parent, false)
            MediaViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, cursor: Cursor?) {
        if (holder is MediaViewHolder) {
            val media = Media(cursor)
            val mediaView = holder.mediaView
            mediaView.let {
                it.resizeWidth = calculateImageResize(mediaView.context)
                it.bindMedia(media)
                it.listener = this
                it.viewHolder = holder
                it.isCheckViewCountable = PickerSpec.countable
                it.initCheckView()
            }
            setCheckStatus(media, holder.mediaView)
        }
        if (holder is CaptureViewHolder) {
            val captureView = holder.captureView
            captureView.setOnClickListener { captureClickListener?.onCaptureClick() }
        }
    }

    override fun getItemViewType(position: Int, cursor: Cursor): Int {
        return if (Media(cursor).isCapture()) {
            VIEW_TYPE_CAPTURE
        } else {
            VIEW_TYPE_MEDIA
        }
    }


    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mediaView = itemView as MediaView
    }

    class CaptureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val captureView = itemView as FrameLayout
    }


    private fun calculateImageResize(context: Context): Int {
        if (imageResize == 0) {
            val layoutManager = recyclerView?.layoutManager as GridLayoutManager?
            layoutManager?.let {
                val spanCount = it.spanCount
                val screenWidth = context.resources.displayMetrics.widthPixels
                val availableWidth = screenWidth - context.resources.getDimensionPixelSize(
                        R.dimen.media_grid_spacing) * (spanCount - 1)
                imageResize = availableWidth / spanCount
                imageResize = (imageResize * PickerSpec.thumbnailScale).toInt()
            }
        }

        return imageResize
    }

    override fun onMediaViewThumbnailClicked(imageView: ImageView, media: Media, viewHolder: RecyclerView.ViewHolder) {
        if (PickerSpec.itemClickPreview) {
            mediaClickListener?.onMediaClick(null, media)
        } else {
            updateSelectedMedia(media, viewHolder)
        }
    }

    override fun onMediaViewCheckViewClicked(checkView: CheckView, media: Media, viewHolder: RecyclerView.ViewHolder) {
        updateSelectedMedia(media, viewHolder)
    }

    /**
     * 处理选中点击事件
     * @param [media] Media 对象
     * @param [holder] ViewHolder
     *
     */
    private fun updateSelectedMedia(media: Media, holder: RecyclerView.ViewHolder) {
        //查看设置是单选还是多选 countable == true <- 多选
        if (PickerSpec.countable) {
            //检查是否已经选中了
            val checkedNum = selectedMediaModel.checkedNum(media)

            if (checkedNum == CheckView.UNCHECKED) {
                //没有选中 尝试添加
                if (assertAddSelection(holder.itemView.context, media)) {
                    selectedMediaModel.add(media)
                    notifyCheckStateChanged()
                }
            } else {
                //已经选中 从selectedMediaModel中移除
                selectedMediaModel.remove(media)
                notifyCheckStateChanged()
            }
        } else {
            //检查是否已经选中了
            if (selectedMediaModel.isSelected(media)) {
                //已经选中 从selectedMediaModel中移除
                selectedMediaModel.remove(media)
            } else {
                //没有选中 尝试添加
                selectedMediaModel.selectedMedias.clear()
                if (assertAddSelection(holder.itemView.context, media)) {
                    selectedMediaModel.add(media)
                }
            }
            notifyCheckStateChanged()
        }
    }

    private fun setCheckStatus(media: Media, mediaView: MediaView) {
        if (PickerSpec.countable) {
            val checkedNum = selectedMediaModel.checkedNum(media)
            if (checkedNum > 0) {
                mediaView.setCheckEnable(true)
                mediaView.setCheckedNum(checkedNum)
            } else {
                if (selectedMediaModel.isNumLimitReached()) {
                    mediaView.setCheckEnable(false)
                    mediaView.setCheckedNum(CheckView.UNCHECKED)
                } else {
                    mediaView.setCheckEnable(true)
                    mediaView.setCheckedNum(checkedNum)
                }
            }
        } else {
            val isSelected = selectedMediaModel.isSelected(media)
            if (isSelected) {
                mediaView.setCheckEnable(true)
                mediaView.setChecked(true)
            } else {
                if (selectedMediaModel.isNumLimitReached()) {
                    mediaView.setCheckEnable(false)
                    mediaView.setChecked(false)
                } else {
                    mediaView.setCheckEnable(true)
                    mediaView.setChecked(false)
                }
            }
        }
    }

    private fun notifyCheckStateChanged() {
        notifyDataSetChanged()
        checkStateListener?.onCheckStateUpdate()
    }

    /**
     * 判断[selectedMediaModel]是否允许插入[media]
     * 如果不允许 使用IncapableCause类内的逻辑处理异常信息提示
     * @return 如允许 返回true 如不允许 返回false
     */
    private fun assertAddSelection(context: Context, media: Media): Boolean {
        val cause = selectedMediaModel.isAcceptable(media)
        IncapableCause.handleCause(context, cause)

        return cause == null
    }

    /**
     * @property [VIEW_TYPE_CAPTURE] 拍照类型
     * @property [VIEW_TYPE_MEDIA] 媒体数据类型
     */
    companion object {
        private const val VIEW_TYPE_CAPTURE = 0x01
        private const val VIEW_TYPE_MEDIA = 0x02
    }

    interface CheckStateListener {
        fun onCheckStateUpdate()
    }

    interface MediaClickListener {
        fun onMediaClick(bucket: Bucket?, media: Media)
    }

    interface CaptureClickListener {
        fun onCaptureClick()
    }
}