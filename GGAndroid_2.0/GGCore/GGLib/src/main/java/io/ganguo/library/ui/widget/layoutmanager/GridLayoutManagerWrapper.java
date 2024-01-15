package io.ganguo.library.ui.widget.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * <p>
 * 修复使用DiffUtil异步比较数据后，切换到UI线程中刷新，出现Inconsistency detected. Invalid view holder adapter positionViewHolder错误问题
 * GridLayoutManager包装类，重写@see {@link #supportsPredictiveItemAnimations()}函数， return false。
 * </p>
 * Created by leo on 2018/8/28.
 */
public class GridLayoutManagerWrapper extends GridLayoutManager {
    public GridLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GridLayoutManagerWrapper(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridLayoutManagerWrapper(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
