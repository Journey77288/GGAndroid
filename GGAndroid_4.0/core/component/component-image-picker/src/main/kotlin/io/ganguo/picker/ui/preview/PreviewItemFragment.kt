package io.ganguo.picker.ui.preview

import android.content.Context
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.github.chrisbanes.photoview.PhotoView
import io.ganguo.picker.R
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/07/22
 *     desc   : 预览图片 View Pager 使用的 展示图片的 Fragment
 * </pre>
 *
 * 预览图片 View Pager 使用的 展示图片的 Fragment
 * @param
 * @see
 * @author Raynor
 * @property
 */
class PreviewItemFragment : Fragment() {
    private var interactionListener: OnFragmentInteractionListener? = null
    private var imageSize: Pair<Int, Int> ?= null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            interactionListener = context
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val media = arguments?.getParcelable<Media>(ARGS_MEDIA) ?: return
        val photoView = view.findViewById<PhotoView>(R.id.photo_view)
        val longIv = view.findViewById<SubsamplingScaleImageView>(R.id.iv_long)

        photoView.setOnClickListener {
            interactionListener?.onClick()
        }
        longIv.setOnClickListener {
            interactionListener?.onClick()
        }

        if (imageSize == null) {
            PickerSpec.imageEngine.loadImage(context, 0, 0, media.uri!!) {
                imageSize = it.intrinsicWidth to it.intrinsicHeight
                loadPhoto(media, photoView, longIv)
            }
        } else {
            loadPhoto(media, photoView, longIv)
        }
    }

    private fun loadPhoto(media: Media, photoView: PhotoView, longView: SubsamplingScaleImageView) {
        imageSize ?: return
        if (imageSize!!.second > imageSize!!.first * 3) {// 长图预览
            loadLongPhoto(media, photoView, longView)
        } else {
            loadNormalPhoto(media, photoView, longView)
        }
    }

    private fun loadLongPhoto(media: Media, photoView: PhotoView, longView: SubsamplingScaleImageView) {
        photoView.visibility = View.GONE
        longView.apply {
            visibility = View.VISIBLE
            maxScale = PREVIEW_MAX_SCALE
            orientation = SubsamplingScaleImageView.ORIENTATION_USE_EXIF
            setDoubleTapZoomDuration(PREVIEW_SCALE_DURATION)
            setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
            setImage(ImageSource.uri(media.uri!!))
        }
    }

    private fun loadNormalPhoto(media: Media, photoView: PhotoView, longView: SubsamplingScaleImageView) {
        photoView.apply {
            visibility = View.VISIBLE
            PickerSpec.imageEngine.loadImage(context, 0, 0, this, media.uri!!)
        }
        longView.visibility = View.GONE
    }

    override fun onDetach() {
        super.onDetach()
        interactionListener = null
    }

    fun resetView() {
        val v = view
        if (v != null) {
            val photoView = v.findViewById<PhotoView>(R.id.photo_view)
            val longView = v.findViewById<SubsamplingScaleImageView>(R.id.iv_long)
            photoView.setDisplayMatrix(Matrix())
            longView.resetScaleAndCenter()
        }
    }

    /**
     * @property [PREVIEW_MAX_SCALE] 预览支持最大放大比例
     * @property [PREVIEW_SCALE_DURATION] 预览双击放大动画延时
     */
    companion object {
        private const val ARGS_MEDIA = "args_media"
        private const val PREVIEW_MAX_SCALE = 5f
        private const val PREVIEW_SCALE_DURATION = 300

        fun newInstance(item: Media?): PreviewItemFragment {
            val fragment = PreviewItemFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_MEDIA, item)
            fragment.arguments = bundle
            return fragment
        }
    }
}

/**
 * PreViewItemFragment 和  BasePreViewActivity 通信的接口 ，为了方便拿到 ImageViewTouch 的点击事件
 */
interface OnFragmentInteractionListener {
    fun onClick()
}