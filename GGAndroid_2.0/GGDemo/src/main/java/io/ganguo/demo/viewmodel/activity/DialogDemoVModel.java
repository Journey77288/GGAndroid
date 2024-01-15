package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import io.ganguo.demo.view.dialog.DemoBottomDialog;
import io.ganguo.demo.view.dialog.DemoBottomRecyclerDialog;
import io.ganguo.demo.view.window.REHorizontalWindow;
import io.ganguo.demo.view.window.REVerticalWindow;
import io.ganguo.demo.view.window.TestPopupWindow;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.item.ItemSampleVModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.reactivex.functions.Consumer;

/**
 * <p>
 * Dialog 弹窗 Demo
 * </p>
 * Created by leo on 2018/8/2.
 */

public class DialogDemoVModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {
    protected TestPopupWindow popupWindow;


    @Override
    public void onViewAttached(View view) {
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel("ViewModelPopupWindow", onCreateWindowAction()));
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel("RecyclerView horizontal PopupWindow", onCreateHTWindowAction()));
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel("RecyclerView vertical PopupWindow", onCreateVTWindowAction()));
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel("BottomDialog", onCreateBtmDialogAction()));
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel("BottomRecyclerDialog", onCreateBtmRVDialogAction()));
        getAdapter().notifyDataSetChanged();
        toggleEmptyView();
    }


    /**
     * function:create horizontal PopupWindow Consumer
     *
     * @return
     */
    private Consumer<View> onCreateHTWindowAction() {
        return view -> new REHorizontalWindow(getContext()).showAsDropDown(view);
    }

    /**
     * function: create vertical PopupWindow Consumer
     *
     * @return
     */
    private Consumer<View> onCreateVTWindowAction() {
        return view -> new REVerticalWindow(getContext()).showAsDropDown(view);
    }


    /**
     * function: create PopupWindow Consumer
     *
     * @return
     */
    protected Consumer<View> onCreateBtmRVDialogAction() {
        return view -> {
            new DemoBottomRecyclerDialog(getContext()).show();
        };
    }

    /**
     * function:create PopupWindow Consumer
     *
     * @return
     */
    protected Consumer<View> onCreateBtmDialogAction() {
        return view -> {
            new DemoBottomDialog(getContext()).show();
        };
    }

    /**
     * function:create PopupWindow Consumer
     *
     * @return
     */
    protected Consumer<View> onCreateWindowAction() {
        return view -> {
            if (getPopupWindow().isShowing()) {
                getPopupWindow().dismiss();
                popupWindow = null;
            } else {
                getPopupWindow().showAsDropDown(view);
            }
        };
    }


    /**
     * create TestPopupWindow
     *
     * @return
     */
    public TestPopupWindow getPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new TestPopupWindow(getContext());
        }
        return popupWindow;
    }
}
