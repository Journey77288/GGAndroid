package io.ganguo.picker.core.model

import android.content.Context
import android.net.Uri
import android.os.Bundle
import io.ganguo.picker.core.entity.IncapableCause
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.util.PathUtils

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/24
 *     desc   : Selected Media Data Model
 * </pre>
 *
 * Selected Media Data Model
 * 在PickerActivity/PreViewActivity中 创建/获取此对象保存选择的媒体资源项目
 * @param
 * @see
 * @author Raynor
 * @property [context] 上下文
 * @property [selectedMedias] 使用Set存储选中的资源对象
 * @property [selectionType] 选中的媒体类型
 */
class SelectedMediaModel(private val context: Context) {
    val selectedMedias = mutableSetOf<Media>()
    private var selectionType = SELECTION_TYPE_UNDEFINED


    /**
     * 初始化选中状态和媒体类型
     * 在Activity的OnCreate方法中使用
     */
    fun onCreate(bundle: Bundle?) {
        if (bundle == null) {
            selectedMedias.clear()
        } else {
            val savedList: ArrayList<Media>? = bundle.getParcelableArrayList(SAVED_SELECTION)
            savedList?.apply {
                selectedMedias.clear()
                selectedMedias.addAll(this)
                selectionType = bundle.getInt(STATE_SELECTION_TYPE, SELECTION_TYPE_UNDEFINED)
            }
        }
    }

    /**
     * 保存选中状态与媒体类型
     * 在Activity的onSaveInstanceState方法中使用
     */
    fun onSaveInstanceState(bundle: Bundle) {
        bundle.putParcelableArrayList(SAVED_SELECTION, ArrayList(selectedMedias))
        bundle.putInt(STATE_SELECTION_TYPE, selectionType)
    }

    /**
     * 生成Bundle并将选中状态与媒体类型保存其中
     * 在Activity互相跳转或返回选择结果时使用
     */
    fun getBundle(): Bundle {
        val bundle = Bundle()
        bundle.putParcelableArrayList(SAVED_SELECTION, ArrayList(selectedMedias))
        bundle.putInt(STATE_SELECTION_TYPE, selectionType)
        return bundle
    }

    /**
     * 添加新的选中项目
     * @return 是否添加成功
     */
    fun add(media: Media): Boolean {
        check(isTypeConflict(media).not()) { "Can't select images and videos at the same time." }
        val isAdded = selectedMedias.add(media)

        if (isAdded) {
            when (selectionType) {
                SELECTION_TYPE_UNDEFINED -> {
                    selectionType = if (media.isImage()) {
                        SELECTION_TYPE_IMAGE
                    } else {
                        SELECTION_TYPE_VIDEO
                    }
                }
                SELECTION_TYPE_IMAGE -> {
                    if (media.isVideo()) {
                        selectionType = SELECTION_TYPE_MIXED
                    }
                }
                SELECTION_TYPE_VIDEO -> {
                    if (media.isImage()) {
                        selectionType = SELECTION_TYPE_MIXED
                    }
                }
            }
        }

        return isAdded
    }

    /**
     * 删除指定元素
     */
    fun remove(media: Media): Boolean {
        val isRemoved = selectedMedias.remove(media)
        if (isRemoved) {
            if (selectedMedias.isEmpty()) {
                selectionType = SELECTION_TYPE_UNDEFINED
            } else {
                if (selectionType == SELECTION_TYPE_MIXED) {
                    refineSelectionType()
                }
            }
        }

        return isRemoved
    }

    /**
     * 使用传入的数据覆盖之前保留的选中资源与媒体类型
     * 一般是在拍照onActivityResult时使用，这时会清空之前的选中状态并将新拍摄的照片传入
     */
    fun overwrite(selectedMedias: List<Media>, selectionType: Int) {
        if (selectedMedias.isEmpty()) {
            this.selectionType = SELECTION_TYPE_UNDEFINED
        } else {
            this.selectionType = selectionType
        }
        this.selectedMedias.clear()
        this.selectedMedias.addAll(selectedMedias)
    }

    fun asUriList(): List<Uri?> {
        return selectedMedias.map { it.uri }.toList()
    }

    fun asStringList(): List<String?> {
        return selectedMedias.map { PathUtils.getPath(context, it.uri) }.toList()
    }

    fun isEmpty() = selectedMedias.isEmpty()

    fun isSelected(media: Media) = selectedMedias.contains(media)

    fun isNumLimitReached(): Boolean = selectedMedias.size == PickerSpec.selectionNumLimit

    /**
     * 在插入前用来判断是否还能继续插入
     * @return 如果可以插入返回null 如果不能插入则返回IncapableCause描述具体的错误信息以及提示方式
     */
    fun isAcceptable(media: Media): IncapableCause? {

        if (isNumLimitReached()) {
            //TODO 准备错误信息
            return IncapableCause("达到最大可选数量")
        } else {
            if (isTypeConflict(media)) {
                return IncapableCause("不可以同时选择图片或视频")
            }
        }

        return null
    }

    /**
     * 判断传入的媒体资源类型是否与当前[selectionType]冲突
     * @param [media] 待判断的媒体资源对象
     */
    fun isTypeConflict(media: Media): Boolean {
        return ((media.isImage() && ((selectionType == SELECTION_TYPE_VIDEO))) or (media.isVideo() && ((selectionType == SELECTION_TYPE_IMAGE))))
    }

    /**
     * 重新整理媒体类型
     * 在[selectedMedias]发生变动（移除某一元素）时使用 重新计算媒体类型
     */
    private fun refineSelectionType() {
        var hasImage = false
        var hasVideo = false
        selectedMedias.forEach {
            if (it.isImage() and hasImage.not()) {
                hasImage = true
            } else if (it.isVideo() and hasVideo.not()) {
                hasVideo = true
            }
        }

        if (hasImage and hasVideo) {
            selectionType = SELECTION_TYPE_MIXED

        }
    }

    /**
     * 查询给定media的选中序号
     * @param [media] 待检查的MediaItem
     * @return 如果已经选中 返回index+1 如果没有选中 返回[io.ganguo.picker.ui.widget.CheckView.UNCHECKED]
     */
    fun checkedNum(media: Media): Int {
        val index = ArrayList(selectedMedias).indexOf(media)
        return if (index == -1) {
            io.ganguo.picker.ui.widget.CheckView.UNCHECKED
        } else {
            index + 1
        }
    }

    /**
     * 查询已选中的媒体数量
     */
    fun getSelectedCount():Int = selectedMedias.size


    companion object {
        const val SAVED_SELECTION = "state_selection"
        const val STATE_SELECTION_TYPE = "state_collection_type"
        const val SELECTION_TYPE_UNDEFINED = 0x00
        const val SELECTION_TYPE_IMAGE = 0x01
        const val SELECTION_TYPE_VIDEO = 0x01 shl 1
        const val SELECTION_TYPE_MIXED = SELECTION_TYPE_IMAGE or SELECTION_TYPE_VIDEO
    }

    interface SelectedMediaModelProvider {
        fun provideSelectedMediaModel(): SelectedMediaModel
    }
}