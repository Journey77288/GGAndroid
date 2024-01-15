package io.ganguo.picker.ui.preview

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.ganguo.picker.core.entity.Media

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/07/22
 *     desc   : 图片预览 View Pager Adapter
 * </pre>
 * 图片预览 View Pager Adapter
 * @param
 * @see
 * @author Raynor
 * @property
 */
class PreviewPagerAdapter(manager: FragmentManager, private val listener: OnPrimaryItemSetListener?) : FragmentPagerAdapter(manager) {
    private val mediaList = mutableListOf<Media>()

    override fun getItem(position: Int): Fragment {
        return PreviewItemFragment.newInstance(mediaList[position])
    }

    override fun getCount(): Int {
        return mediaList.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)
        listener?.onPrimaryItemSet(position)
    }

    fun getMedia(position: Int): Media {
        return mediaList[position]
    }

    fun addAll(medias: List<Media>) {
        mediaList.addAll(medias)
    }
}


interface OnPrimaryItemSetListener {
    fun onPrimaryItemSet(position: Int)
}