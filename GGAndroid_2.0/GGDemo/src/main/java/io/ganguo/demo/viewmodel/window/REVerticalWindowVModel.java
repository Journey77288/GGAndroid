package io.ganguo.demo.viewmodel.window;


import android.support.v7.widget.RecyclerView;

/**
 * <p>
 * Vertical 方向 - REWindowVModel
 * 注：Demo的原因，直接继承了REHorizontalWindowDemoVModel了，实际项目应用请继承REWindowVModel
 * </p>
 * Created by leo on 2018/10/21.
 */
public class REVerticalWindowVModel extends REHorizontalWindowVModel {

    @Override
    public int getOrientation() {
        return RecyclerView.VERTICAL;
    }
}
