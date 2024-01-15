package io.ganguo.library.ui.widget.layoutmanager;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * <p>
 * 修复使用DiffUtil异步比较数据后，切换到UI线程中刷新，出现Inconsistency detected. Invalid view holder adapter positionViewHolder错误问题
 * StaggeredGridLayoutManager包装类，重写@see {@link #supportsPredictiveItemAnimations()}函数， return false。
 * </p>
 * Created by leo on 2018/8/28.
 */
public class StaggeredGridLayoutManagerWrapper extends StaggeredGridLayoutManager {
    public StaggeredGridLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public StaggeredGridLayoutManagerWrapper(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
