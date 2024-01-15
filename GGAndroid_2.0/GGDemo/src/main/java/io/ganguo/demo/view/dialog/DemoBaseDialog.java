package io.ganguo.demo.view.dialog;

import android.content.Context;

import io.ganguo.demo.databinding.ItemDemoBinding;
import io.ganguo.demo.viewmodel.item.ItemDemoVModel;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelDialog;
import io.ganguo.utils.util.Randoms;

/**
 * Created by Roger on 7/13/16.
 */
public class DemoBaseDialog extends ViewModelDialog<ItemDemoBinding, ItemDemoVModel> {

    public DemoBaseDialog(Context context) {
        super(context);
    }

    @Override
    public ItemDemoVModel<String> createViewModel() {
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
    public ItemDemoVModel<String> createItemDemoVModel() {
        String content = Randoms.getRandomCapitalLetters(12);
        return new ItemDemoVModel<String>()
                .setBtnText("click")
                .setData(content)
                .setContent(content)
                .setClickAction(s -> dismiss());
    }
}
