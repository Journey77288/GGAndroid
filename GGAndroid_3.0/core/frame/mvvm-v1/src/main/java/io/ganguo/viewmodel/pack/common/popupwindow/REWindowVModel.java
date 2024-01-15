package io.ganguo.viewmodel.pack.common.popupwindow;

import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.core.viewinterface.WindowInterface;
import io.ganguo.viewmodel.R;
import io.ganguo.viewmodel.pack.common.RecyclerViewModel;
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding;
import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.ViewModelHelper;
import io.ganguo.viewmodel.core.adapter.ViewModelAdapter;


/**
 * <p>
 * 支持RecyclerViewModel 的 PopupWindowViewModel
 * </p>
 * Created by leo on 2018/10/20.
 */
public abstract class REWindowVModel<V extends WindowInterface<IncludeRecyclerPopupwindowBinding>> extends BaseViewModel<V> {
    @ColorInt
    private int cardBackgroundColor = Color.WHITE;
    @Dimension
    private int cardMargin = 0;
    @Dimension
    private int cardCornerRadius = 0;
    @Dimension
    private int cardElevation = 0;
    @Dimension
    private int cardMaxElevation = 0;
    @Dimension
    private int contentPadding = 0;
    protected RecyclerViewModel reViewModel;


    @Override
    public void onAttach() {
        super.onAttach();
        initViewModel();
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_recycler_popupwindow;
    }


    /**
     * function: init ViewModel
     *
     * @return
     */
    protected void initViewModel() {
        ViewModelHelper.INSTANCE.bind(getViewInterface().getBinding().includeRecycler, this, getReViewModel());
    }


    /**
     * function: init RecyclerViewModel
     *
     * @return
     */
    public RecyclerViewModel<ViewDataBinding, ViewInterface<ViewDataBinding>> getReViewModel() {
        if (reViewModel == null) {
            reViewModel = RecyclerViewModel
                    .linerLayout(getContext(), getOrientation())
                    .setViewWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return reViewModel;
    }


    /**
     * function: get adapter
     *
     * @return
     */
    public ViewModelAdapter getAdapter() {
        if (reViewModel == null || !reViewModel.isAttach()) {
            return null;
        }
        return reViewModel.getAdapter();
    }

    /**
     * Get RecyclerView after bind to ViewInterface
     */
    @NonNull
    public RecyclerView getRecyclerView() {
        return getReViewModel().getRecyclerView();
    }

    /**
     * RecyclerView 列表布局 方向
     *
     * @return
     */
    @RecyclerView.Orientation
    public abstract int getOrientation();


    public REWindowVModel setCardBackgroundColor(@ColorInt int cardBackgroundColor) {
        this.cardBackgroundColor = cardBackgroundColor;
        return this;
    }

    public REWindowVModel setCardMargin(@Dimension int cardMargin) {
        this.cardMargin = cardMargin;
        return this;
    }

    public REWindowVModel setCardCornerRadius(@Dimension int cardCornerRadius) {
        this.cardCornerRadius = cardCornerRadius;
        return this;
    }

    public REWindowVModel setCardElevation(@Dimension int cardElevation) {
        this.cardElevation = cardElevation;
        return this;
    }

    public REWindowVModel setCardMaxElevation(@Dimension int cardMaxElevation) {
        this.cardMaxElevation = cardMaxElevation;
        return this;
    }

    public REWindowVModel setContentPadding(@Dimension int contentPadding) {
        this.contentPadding = contentPadding;
        return this;
    }

    public int getCardBackgroundColor() {
        return cardBackgroundColor;
    }

    public int getCardMargin() {
        return cardMargin;
    }

    public int getCardCornerRadius() {
        return cardCornerRadius;
    }

    public int getCardElevation() {
        return cardElevation;
    }

    public int getCardMaxElevation() {
        return cardMaxElevation;
    }

    public int getContentPadding() {
        return contentPadding;
    }
}
