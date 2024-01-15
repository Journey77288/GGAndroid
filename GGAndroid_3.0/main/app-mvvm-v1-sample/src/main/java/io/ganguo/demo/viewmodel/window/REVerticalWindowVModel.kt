package io.ganguo.demo.viewmodel.window


import androidx.recyclerview.widget.RecyclerView

/**
 *
 *
 * Vertical 方向 - REWindowVModel
 * 注：Demo的原因，直接继承了REHorizontalWindowDemoVModel了，实际项目应用请继承REWindowVModel
 *
 * Created by leo on 2018/10/21.
 */
class REVerticalWindowVModel : REHorizontalWindowVModel() {

    override fun getOrientation(): Int {
        return RecyclerView.VERTICAL
    }
}
