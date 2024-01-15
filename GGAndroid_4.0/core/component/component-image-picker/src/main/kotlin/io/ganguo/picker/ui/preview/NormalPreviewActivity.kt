package io.ganguo.picker.ui.preview

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.core.model.BucketMediaModel

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/07/22
 *     desc   : TODO
 * </pre>
 */
/**
 * TODO
 * @param
 * @see
 * @author Raynor
 * @property
 */
class NormalPreviewActivity : BasePreviewActivity(), BucketMediaModel.BucketMediaModelCallbacks {
    private val bucketMediaModel = BucketMediaModel()
    private var isAlreadySetPosition = false
    lateinit var selectedMedias: ArrayList<Media>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bucketMediaModel.onCreate(this, this)
        val bucket = intent.getParcelableExtra<Bucket>(EXTRA_BUCKET)!!
        bucketMediaModel.load(bucket)

        val media = intent.getParcelableExtra<Media>(EXTRA_MEDIA)!!
        if (PickerSpec.countable) {
            binding.checkView.setCheckedNum(selectedMediaModel.checkedNum(media))
        } else {
            binding.checkView.setChecked(selectedMediaModel.isSelected(media))
        }
        selectedMedias = intent.getParcelableArrayListExtra<Media>(EXTRA_SELECTED_MEDIAS) ?: arrayListOf()
    }

    override fun onDestroy() {
        super.onDestroy()
        bucketMediaModel.onDestroy()
    }

    override fun onBucketMediaLoad(cursor: Cursor?) {
        if (selectedMedias.isEmpty()) {
            return
        }

        val adapter = binding.pager.adapter as PreviewPagerAdapter
        adapter.addAll(selectedMedias)
        adapter.notifyDataSetChanged()
        if (isAlreadySetPosition.not()) {
            isAlreadySetPosition = true
            val media = intent.getParcelableExtra<Media>(EXTRA_MEDIA)!!
            val selectedIndex = selectedMedias.indexOf(media)
            binding.pager.setCurrentItem(selectedIndex, false)
            previousPosition = selectedIndex
        }
    }

    override fun onBucketMediaReset() {
    }

    companion object {
        const val EXTRA_BUCKET = "extra_bucket"
        const val EXTRA_MEDIA = "extra_item"
        const val EXTRA_SELECTED_MEDIAS = "extra_selected_medias"

        @JvmStatic
        fun intentFor(
            context: Context,
            bucket: Bucket?,
            media: Media,
            bundle: Bundle,
            mediaList: ArrayList<Media>
        ): Intent {
            val intent = Intent(context, NormalPreviewActivity::class.java)
            intent.putExtra(EXTRA_BUCKET, bucket)
            intent.putExtra(EXTRA_MEDIA, media)
            intent.putExtra(EXTRA_DEFAULT_BUNDLE, bundle)
            intent.putParcelableArrayListExtra(EXTRA_SELECTED_MEDIAS, mediaList)
            return intent
        }
    }
}