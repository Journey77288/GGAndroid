package io.ganguo.demo.view.dialog;

import android.content.Context;

import io.ganguo.demo.databinding.ItemDemoBinding;
import io.ganguo.demo.viewmodel.item.ItemDemoVModel;
import io.ganguo.utils.callback.common.Action1;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelMaterialDialog;
import io.ganguo.utils.util.Randoms;
import io.reactivex.functions.Action;

/**
 * Created by Roger on 7/25/16.
 */
public class DemoMaterialDialog extends ViewModelMaterialDialog<ItemDemoBinding, ItemDemoVModel> {

    public DemoMaterialDialog(Context context) {
        super(context);
    }

    public DemoMaterialDialog(Context context, boolean cancelable) {
        super(context, cancelable);
    }


    @Override
    public ItemDemoVModel createViewModel() {
        return createItemDemoVModel();
    }

    @Override
    public void onViewAttached(ItemDemoVModel viewModel) {

    }


    /**
     * function: create Item ViewModel
     *
     * @return
     */
    public ItemDemoVModel createItemDemoVModel() {
        String content = Randoms.getRandomCapitalLetters(12);
        return new ItemDemoVModel<String>()
                .setBtnText("click")
                .setData(content)
                .setContent(content)
                .setClickAction(s -> getDialog().dismiss());
    }
}

