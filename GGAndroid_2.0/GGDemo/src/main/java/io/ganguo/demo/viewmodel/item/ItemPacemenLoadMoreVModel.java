package io.ganguo.demo.viewmodel.item;

import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ItemPacemenLoadMoreBinding;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * <p>
 * 自定义PacmanLoading ViewModel
 * </p>
 * Created by leo on 2018/9/14.
 */
public class ItemPacemenLoadMoreVModel extends BaseViewModel<ViewInterface<ItemPacemenLoadMoreBinding>> {
    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_pacemen_load_more;
    }

    public void startAnim() {
        if (!isAttach()) {
            return;
        }
        getLoadingView().smoothToShow();
    }

    public void stopAnim() {
        if (!isAttach()) {
            return;
        }
        getLoadingView().smoothToHide();
    }

    private AVLoadingIndicatorView getLoadingView() {
        return getView().getBinding().avi;
    }


}
