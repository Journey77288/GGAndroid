package io.ganguo.picker.ui

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import io.ganguo.picker.R
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.core.model.BucketMediaModel
import io.ganguo.picker.core.model.SelectedMediaModel
import io.ganguo.picker.databinding.FragmentMediaSelectionBinding
import io.ganguo.picker.ui.media.MediaAdapter
import io.ganguo.picker.ui.widget.MediaGridItemDecoration

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/22
 *     desc   : 显示在PickerActivity的container内部的Fragment
 * </pre>
 *
 * 显示在PickerActivity的container内部的Fragment
 * 是图片列表的容器
 * @param
 * @see
 * @author Raynor
 * @property
 */
class PickerFragment : Fragment(), BucketMediaModel.BucketMediaModelCallbacks, MediaAdapter.CheckStateListener, MediaAdapter.MediaClickListener,
    MediaAdapter.CaptureClickListener {

    private var binding: FragmentMediaSelectionBinding? = null
    private var mediaAdapter: MediaAdapter? = null
    private val bucketMediaModel = BucketMediaModel()
    private lateinit var selectedMediaModelProvider: SelectedMediaModel.SelectedMediaModelProvider
    private lateinit var activityCheckListener: MediaAdapter.CheckStateListener
    private lateinit var activityMediaClickListener: MediaAdapter.MediaClickListener
    private lateinit var activityCaptureClickListener: MediaAdapter.CaptureClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        check(context is SelectedMediaModel.SelectedMediaModelProvider) { "Context must implement SelectionProvider" }
        check(context is MediaAdapter.CheckStateListener) { "Context must implement MediaAdapter.CheckStateListener" }
        check(context is MediaAdapter.MediaClickListener) { "Context must implement MediaAdapter.MediaClickListener" }
        check(context is MediaAdapter.CaptureClickListener) { "Context must implement MediaAdapter.CaptureClickListener" }

        selectedMediaModelProvider = context
        activityCheckListener = context
        activityMediaClickListener = context
        activityCaptureClickListener = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMediaSelectionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //从Bundle中拿Bucket
        val bucket: Bucket = arguments?.getParcelable(EXTRA_BUCKET) ?: return

        //新建Bucket Media的Recycler View Adapter
        mediaAdapter = MediaAdapter(requireContext(), selectedMediaModelProvider.provideSelectedMediaModel(), binding!!.recyclerview)
        mediaAdapter?.checkStateListener = this
        mediaAdapter?.mediaClickListener = this
        mediaAdapter?.captureClickListener = this
        binding?.recyclerview?.setHasFixedSize(true)

        val spanCount: Int = PickerSpec.spanCount
        binding?.recyclerview?.layoutManager = GridLayoutManager(context, spanCount)

        //使用itemDecoration设施spacing
        val spaceing = resources.getDimensionPixelSize(R.dimen.media_grid_spacing)
        binding?.recyclerview?.addItemDecoration(MediaGridItemDecoration(spanCount, spaceing, false))
        binding?.recyclerview?.adapter = mediaAdapter

        //初始化BucketMediaModel
        bucketMediaModel.onCreate(requireActivity(), this)
        bucketMediaModel.load(bucket, PickerSpec.capture)
    }

    fun refreshData(bucket: Bucket) {
        bucketMediaModel.reload(bucket, PickerSpec.capture)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bucketMediaModel.onDestroy()
        binding = null
    }

    override fun onBucketMediaLoad(cursor: Cursor?) {
        mediaAdapter?.swapCursor(cursor)
    }

    override fun onBucketMediaReset() {
        mediaAdapter?.swapCursor(null)
    }

    override fun onCheckStateUpdate() {
        activityCheckListener.onCheckStateUpdate()
    }

    override fun onMediaClick(bucket: Bucket?, media: Media) {
        activityMediaClickListener.onMediaClick(arguments?.getParcelable(EXTRA_BUCKET), media)
    }

    companion object {
        const val EXTRA_BUCKET = "extra_bucket"

        fun instance(bucket: Bucket): PickerFragment {
            val fragment = PickerFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_BUCKET, bucket)
            fragment.arguments = args
            return fragment
        }
    }

    fun refreshMediaGrid() {
        mediaAdapter?.notifyDataSetChanged()
    }

    override fun onCaptureClick() {
        activityCaptureClickListener.onCaptureClick()
    }
}