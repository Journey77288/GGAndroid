package io.ganguo.library.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.ganguo.library.R;
import io.ganguo.library.core.drawable.MaterialProgressDrawable;
import io.ganguo.library.databinding.IncludeLoadingBinding;
import io.ganguo.library.ui.view.loading.ILoadMoreViewInterface;
import io.ganguo.utils.util.Views;

/**
 * <p>
 * Adapter  LoadMoreLoadingView
 * </p>
 * Created by leo on 2018/9/14.
 */
public class LoadMoreLoadingView implements ILoadMoreViewInterface<IncludeLoadingBinding> {
    private Context context;
    private IncludeLoadingBinding loadBinding;
    private MaterialProgressDrawable loadingProgress;

    /**
     * 判断View状态
     *
     * @return
     */
    protected boolean isAttach() {
        if (loadBinding == null || loadBinding.imageView == null) {
            return false;
        }
        return true;
    }


    @Override
    public void initView(Context context, ViewGroup parentView) {
        this.context = context;
        loadBinding = IncludeLoadingBinding.inflate(LayoutInflater.from(context), parentView, false);
    }

    @Override
    public IncludeLoadingBinding getViewBinding() {
        return loadBinding;
    }


    private MaterialProgressDrawable getLoadDrawable() {
        if (loadingProgress == null) {
            loadingProgress = onCreateLoadDrawable();
        }
        return loadingProgress;
    }

    /**
     * function: create MaterialProgressDrawable
     *
     * @return
     */
    private MaterialProgressDrawable onCreateLoadDrawable() {
        MaterialProgressDrawable drawable = new MaterialProgressDrawable(context, this.loadBinding.getRoot());
        drawable.setAlpha(255);
        drawable.setBackgroundColor(Color.TRANSPARENT);
        Resources resources = context.getResources();
        int color = resources.getColor(R.color.colorAccent);
        int blue = resources.getColor(R.color.colorAccent);
        int green = resources.getColor(R.color.colorAccent);
        drawable.setColorSchemeColors(color, blue, green);
        return drawable;
    }

    @Override
    public void onStartLoading() {
        if (!isAttach()) {
            return;
        }
        Views.visible(loadBinding.getRoot());
        Views.visible(loadBinding.imageView);
        loadBinding.imageView.setImageDrawable(getLoadDrawable());
        getLoadDrawable().start();
    }

    @Override
    public void onStopLoading() {
        if (!isAttach()) {
            return;
        }
        getLoadDrawable().stop();
        Views.gone(loadBinding.getRoot());
        Views.gone(loadBinding.imageView);
        loadBinding.imageView.setImageDrawable(null);
    }

    @Override
    public void onDestroy() {
        context = null;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.include_loading;
    }
}
