package io.ganguo.viewmodel.pack.common.dialog;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.ganguo.viewmodel.core.BaseViewModel;
import io.ganguo.viewmodel.core.viewinterface.DialogInterface;
import io.ganguo.viewmodel.core.viewinterface.ViewInterface;
import io.ganguo.viewmodel.pack.common.RecyclerViewModel;
import io.ganguo.viewmodel.pack.common.base.BaseDialogVModel;
import io.ganguo.viewmodel.databinding.DialogGgBinding;
import io.ganguo.viewmodel.core.ViewModelHelper;
import io.ganguo.viewmodel.core.adapter.ViewModelAdapter;

/**
 * Dialog RecyclerViewModel
 * Created by leo on 2018/11/9.
 */
public abstract class DialogRecyclerVModel<T extends DialogGgBinding> extends BaseDialogVModel<T, DialogInterface<T>> {
    private RecyclerViewModel recyclerViewModel;


    @Override
    public void initContent(ViewGroup container) {
        initRecyclerView(container);
    }


    protected void initRecyclerView(ViewGroup container) {
        ViewModelHelper.INSTANCE.bind(container, this, getRecyclerViewModel());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getRecyclerView().getLayoutParams();
        layoutParams.weight = 1;
        getRecyclerView().setLayoutParams(layoutParams);
    }


    protected ViewModelAdapter getAdapter() {
        return getRecyclerViewModel().getAdapter();
    }

    protected RecyclerViewModel<ViewDataBinding, ViewInterface<ViewDataBinding>> getRecyclerViewModel() {
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
