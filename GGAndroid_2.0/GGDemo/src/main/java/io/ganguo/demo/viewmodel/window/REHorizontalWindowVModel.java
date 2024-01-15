package io.ganguo.demo.viewmodel.window;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.library.ui.view.PopupWindowInterface;
import io.ganguo.utils.common.ResHelper;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.viewmodel.common.TextViewModel;
import io.ganguo.viewmodel.common.popupwindow.REWindowVModel;
import io.ganguo.viewmodel.databinding.IncludeRecyclerPopupwindowBinding;
import io.ganguo.vmodel.BaseViewModel;

/**
 * <p>
 * Horizontal 方向 - REWindowVModel
 * </p>
 * Created by leo on 2018/10/21.
 */
public class REHorizontalWindowVModel extends REWindowVModel<PopupWindowInterface<IncludeRecyclerPopupwindowBinding>> {
    public REHorizontalWindowVModel() {
        int dp5 = ResHelper.getDimensionPixelOffsets(R.dimen.dp_5);
        setCardBackgroundColor(Color.WHITE);
        setCardCornerRadius(dp5);
        setCardElevation(dp5);
        setCardMaxElevation(dp5 * 2);
        setCardMargin(dp5 * 2);
        setContentPadding(dp5);
    }

    @Override
    public void onViewAttached(View view) {
        getAdapter().add(getCopyMenuVModel());
        getAdapter().add(getAddCommentMenuVModel());
        getAdapter().add(getShareMenuVModel());
        getAdapter().notifyDataSetChanged();
    }

    /**
     * function: 複製
     *
     * @return
     */
    private BaseViewModel getCopyMenuVModel() {
        return new TextViewModel
                .Builder()
                .textColorRes(R.color.black)
                .textSizeRes(R.dimen.font_15)
                .paddingLeftRes(R.dimen.dp_15)
                .paddingRightRes(R.dimen.dp_15)
                .paddingBottomRes(R.dimen.dp_18)
                .paddingTopRes(R.dimen.dp_18)
                .backgroundRes(R.drawable.ripple_default)
                .content("编辑")
                .onClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastHelper.showMessage( "编辑");
                    }
                })
                .build();
    }

    /**
     * function: 分享
     *
     * @return
     */
    private BaseViewModel getShareMenuVModel() {
        return new TextViewModel
                .Builder()
                .textColorRes(R.color.black)
                .textSizeRes(R.dimen.font_15)
                .paddingLeftRes(R.dimen.dp_15)
                .paddingRightRes(R.dimen.dp_15)
                .paddingBottomRes(R.dimen.dp_18)
                .paddingTopRes(R.dimen.dp_18)
                .backgroundRes(R.drawable.ripple_default)
                .content("分享")
                .onClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastHelper.showMessage( "分享");
                    }
                })
                .build();
    }

    /**
     * function: 添加評論
     *
     * @return
     */
    private BaseViewModel getAddCommentMenuVModel() {
        return new TextViewModel
                .Builder()
                .textColorRes(R.color.black)
                .textSizeRes(R.dimen.font_15)
                .paddingLeftRes(R.dimen.dp_15)
                .paddingRightRes(R.dimen.dp_15)
                .paddingBottomRes(R.dimen.dp_18)
                .paddingTopRes(R.dimen.dp_18)
                .backgroundRes(R.drawable.ripple_default)
                .content("复制")
                .onClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastHelper.showMessage( "复制");
                    }
                })
                .build();
    }

    @Override
    public int getOrientation() {
        return RecyclerView.HORIZONTAL;
    }
}
