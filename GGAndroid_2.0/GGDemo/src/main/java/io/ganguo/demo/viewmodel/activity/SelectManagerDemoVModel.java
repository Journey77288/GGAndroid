package io.ganguo.demo.viewmodel.activity;

import android.view.View;
import android.view.ViewGroup;

import io.ganguo.demo.AppContext;
import io.ganguo.demo.viewmodel.item.ItemSelectVModel;
import io.ganguo.demo.viewmodel.item.SelectDemoFooterVModel;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.rx.selector.MultiSelectManager;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * SelectManagerDemoVModel
 * Created by Roger on 12/05/2017.
 */
public class SelectManagerDemoVModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {
    private MultiSelectManager<ItemSelectVModel> selectManager;

    public SelectManagerDemoVModel() {
        this.selectManager = new MultiSelectManager<>();
        // 设置最大选择个数
//        this.selectManager.setMaxSelected(5);
        // 设置最小选择个数
//        this.selectManager.setMinSelected(2);
//         设置当超出最大选中数量时，自动选中最新的 item, 并取消选中最旧的 item
//        this.selectManager.setAutoSelect(true);
    }


    @Override
    public void initFooter(ViewGroup container) {
        super.initFooter(container);
        SelectDemoFooterVModel footerVModel = new SelectDemoFooterVModel(
                selectManager.getIsAllSelectedProperty(),
                getSelectAllAction(),
                getReverseSelectAction());
        ViewModelHelper.bind(container, this, footerVModel);
    }

    @Override
    public void onViewAttached(View view) {
        observeOnMaxSelected();
        initItemVModel();
    }

    /**
     * function: 订阅最大选择数量
     */
    private void observeOnMaxSelected() {
        selectManager.getOnMaxSelectedEvent()
                .compose(RxVMLifecycle.<Integer>bindViewModel(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(maxCount -> ToastHelper.showMessage("已选中最大数量" + maxCount));
    }

    /**
     * function: init Item ViewModel
     */
    private void initItemVModel() {
        for (int i = 0; i < 7; i++) {
            ItemSelectVModel viewModel = new ItemSelectVModel(onDeleteAction);
            selectManager.add(viewModel);
            getAdapter().add(viewModel);
        }
        getAdapter().notifyDataSetChanged();
        toggleEmptyView();
    }

    /**
     * function: delete 事件
     */
    private Consumer<ItemSelectVModel> onDeleteAction = new Consumer<ItemSelectVModel>() {
        @Override
        public void accept(ItemSelectVModel itemSelectVModel) throws Exception {
            int index = getAdapter().indexOf(itemSelectVModel);
            getAdapter().remove(itemSelectVModel);
            getAdapter().notifyItemRemoved(index);
            selectManager.remove(itemSelectVModel);
        }
    };

    /**
     * function: 选中全部
     */
    private Action getSelectAllAction() {
        return () -> {
            if (selectManager.getIsAllSelectedProperty().get()) {
                selectManager.cancelSelected();
            } else {
                selectManager.selectAll();
            }
        };
    }

    /**
     * function: 反选
     *
     * @return
     */
    private Action getReverseSelectAction() {
        return () -> selectManager.reverseSelect();
    }
}
