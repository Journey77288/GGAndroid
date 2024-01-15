package io.ganguo.viewmodel.common.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.ganguo.viewmodel.common.RecyclerViewModel;
import io.ganguo.viewmodel.common.base.BaseDialogVModel;
import io.ganguo.viewmodel.databinding.DialogGgBinding;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.adapter.ViewModelAdapter;

/**
 * Dialog RecyclerViewModel
 * Created by leo on 2018/11/9.
 */
public abstract class DialogRecyclerVModel<T extends DialogGgBinding> extends BaseDialogVModel<T> {
    private RecyclerViewModel recyclerViewModel;


    @Override
    public void initContent(ViewGroup container) {
        initRecyclerView(container);
    }


    protected void initRecyclerView(ViewGroup container) {
        ViewModelHelper.bind(container, this, getRecyclerViewModel());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getRecyclerView().getLayoutParams();
        layoutParams.weight = 1;
        getRecyclerView().setLayoutParams(layoutParams);
    }


    protected ViewModelAdapter getAdapter() {
        return getRecyclerViewModel().getAdapter();
    }

    protected RecyclerViewModel getRecyclerViewModel() {
        if (recyclerViewModel == null) {
            recyclerViewModel = createRecyclerViewModel();
        }
        return recyclerViewModel;
    }

    protected RecyclerView getRecyclerView() {
        return getRecyclerViewModel().getRecyclerView();
    }

    /**
     * function: create RecyclerViewModel
     *
     * @return
     */
    protected RecyclerViewModel createRecyclerViewModel() {
        return RecyclerViewModel.linerLayout(getContext(), LinearLayoutManager.VERTICAL);
    }
}
