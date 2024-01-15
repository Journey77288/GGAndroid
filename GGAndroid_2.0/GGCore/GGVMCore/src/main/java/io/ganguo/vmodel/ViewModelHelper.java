package io.ganguo.vmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.library.BR;
import io.ganguo.library.ui.adapter.v7.hodler.BaseViewHolder;
import io.ganguo.vmodel.adapter.ViewModelAdapter;
import io.ganguo.vmodel.view.AdapterInterface;
import io.ganguo.library.ui.view.DialogInterface;
import io.ganguo.library.ui.view.PopupWindowInterface;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.vmodel.view.ViewInterfaceFactory;

/**
 * ViewModel辅助类，用于ViewModel与View绑定
 * Created by Roger on 6/7/16.
 */
public class ViewModelHelper {

    /**
     * check ViewModel LayoutId
     */
    @SuppressLint("ResourceType")
    private static boolean checkLayoutId(BaseViewModel viewModel) {
        if (viewModel.getItemLayoutId() <= 0) {
            throw new IllegalArgumentException("viewModel not implement getItemLayoutId");
        }
        return true;
    }

    /**
     * 用于与ViewInterface(View,Adapter,Activity)绑定
     */
    @SuppressWarnings("unchecked")
    public static <B extends BaseViewModel> B bind(ViewInterface view, BaseViewModel parent, B viewModel, int variableId) {
        checkLayoutId(viewModel);
        try {
            viewModel.attach(view, variableId);
        } catch (ClassCastException exception) {
            Log.e("bind error:", "viewModel no match with layoutId or error viewInterface");
        }
        // 绑定生命周期
        if (parent != null) {
            bindLifecycle(parent, viewModel);
        }
        return viewModel;
    }

    @SuppressWarnings("unchecked")
    public static <B extends BaseViewModel> B bind(ViewInterface view, B viewModel, int variableId) {
        return bind(view, null, viewModel, variableId);
    }

    public static <B extends BaseViewModel> B bind(ViewInterface view, BaseViewModel parent, B viewModel) {
        return bind(view, parent, viewModel, BR.data);
    }

    public static <B extends BaseViewModel> B bind(ViewInterface view, B viewModel) {
        return bind(view, null, viewModel, BR.data);
    }

    /**
     * 用于与ViewModelAdapter绑定
     *
     * @see AdapterInterface
     */
    public static <B extends BaseViewModel> B bind(ViewModelAdapter adapter, BaseViewHolder vh, BaseViewModel viewModel) {
        checkLayoutId(viewModel);
        return (B) bind(ViewInterfaceFactory.adapterViewFactory(adapter, vh), adapter.getParent(), viewModel, BR.data);
    }


    /**
     * 用于与Dialog绑定
     *
     * @see PopupWindowInterface
     */
    public static <B extends BaseViewModel> B bind(ViewModelPopupWindow popupWindow, B viewModel) {
        return bind(popupWindow, viewModel, BR.data);
    }

    public static <B extends BaseViewModel> B bind(ViewModelPopupWindow popupWindow, B viewModel, int variableId) {
        checkLayoutId(viewModel);
        return bind(ViewInterfaceFactory.popupWindowViewFactory(popupWindow, ViewModelHelper.inflate(popupWindow
                .getContext(), viewModel.getItemLayoutId())), viewModel, variableId);
    }


    /**
     * 用于与Dialog绑定
     *
     * @see DialogInterface
     */
    public static <B extends BaseViewModel> B bind(Dialog dialog, B viewModel) {
        return bind(dialog, viewModel, BR.data);
    }

    public static <B extends BaseViewModel> B bind(Dialog dialog, B viewModel, int variableId) {
        checkLayoutId(viewModel);
        return bind(ViewInterfaceFactory.dialogViewFactory(dialog, ViewModelHelper.inflate(dialog.getContext(), viewModel.getItemLayoutId())), viewModel, variableId);
    }

    /**
     * 用于直接与Binding绑定
     */
    public static <B extends BaseViewModel> B bind(ViewDataBinding binding, BaseViewModel parent, B viewModel, int variableId) {
        checkLayoutId(viewModel);
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId);
    }

    public static <B extends BaseViewModel> B bind(ViewDataBinding binding, B viewModel, int variableId) {
        checkLayoutId(viewModel);
        return bind(ViewInterfaceFactory.viewFactory(binding), null, viewModel, variableId);
    }

    public static <B extends BaseViewModel> B bind(ViewDataBinding binding, BaseViewModel parent, B viewModel) {
        return bind(binding, parent, viewModel, BR.data);
    }

    public static <B extends BaseViewModel> B bind(ViewDataBinding binding, B viewModel) {
        return bind(binding, viewModel, BR.data);
    }

    /**
     * 用于create view,并与viewModel绑定
     */
    @Deprecated
    public static <B extends BaseViewModel> B bind(Context context, BaseViewModel parent, B viewModel, int variableId) {
        return bind(ViewInterfaceFactory.viewFactory(context, viewModel.getItemLayoutId()), parent, viewModel, variableId);
    }

    @Deprecated
    public static <B extends BaseViewModel> B bind(Context context, B viewModel, int variableId) {
        return bind(ViewInterfaceFactory.viewFactory(context, viewModel.getItemLayoutId()), viewModel, variableId);
    }

    @Deprecated
    public static <B extends BaseViewModel> B bind(Context context, BaseViewModel parent, B viewModel) {
        return bind(context, parent, viewModel, BR.data);
    }

    @Deprecated
    public static <B extends BaseViewModel> B bind(Context context, B viewModel) {
        return bind(context, viewModel, BR.data);
    }

    @Deprecated
    public static <B extends BaseViewModel> B bind(Activity activity, BaseViewModel parent, B viewModel, int variableId) {
        return bind(ViewInterfaceFactory.viewFactoryForActivity(activity, viewModel.getItemLayoutId()), parent, viewModel, variableId);
    }

    @Deprecated
    public static <B extends BaseViewModel> B bind(Activity activity, B viewModel, int variableId) {
        return bind(ViewInterfaceFactory.viewFactoryForActivity(activity, viewModel.getItemLayoutId()), viewModel, variableId);
    }

    @Deprecated
    public static <B extends BaseViewModel> B bind(Activity activity, BaseViewModel parent, B viewModel) {
        return bind(activity, parent, viewModel, BR.data);
    }

    @Deprecated
    public static <B extends BaseViewModel> B bind(Activity activity, B viewModel) {
        return bind(activity, viewModel, BR.data);
    }


    /**
     * 用于绑定viewModel到view
     */
    public static <B extends BaseViewModel> B bind(View view, BaseViewModel parent, B viewModel, int variableId) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(view.getContext()), viewModel.getItemLayoutId(), null, false);
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId);
    }


    public static <B extends BaseViewModel> B bind(View view, B viewModel, int variableId) {
        return bind(view, null, viewModel, variableId);
    }

    public static <B extends BaseViewModel> B bind(View view, BaseViewModel parent, B viewModel) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(view.getContext()), viewModel.getItemLayoutId(), null, true);
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, BR.data);
    }

    public static <B extends BaseViewModel> B bind(View View, B viewModel) {
        return bind(View, null, viewModel);
    }


    /**
     * 用于绑定viewModel到view group container
     */
    public static <B extends BaseViewModel> B bind(ViewGroup container, BaseViewModel parent, B viewModel, int variableId) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), viewModel.getItemLayoutId(), null, false);
        container.addView(binding.getRoot());
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, variableId);
    }

    public static <B extends BaseViewModel> B bind(ViewGroup container, B viewModel, int variableId) {
        return bind(container, null, viewModel, variableId);
    }

    public static <B extends BaseViewModel> B bind(ViewGroup container, BaseViewModel parent, B viewModel) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), viewModel.getItemLayoutId(), container, true);
        return bind(ViewInterfaceFactory.viewFactory(binding), parent, viewModel, BR.data);
    }

    public static <B extends BaseViewModel> B bind(ViewGroup container, B viewModel) {
        return bind(container, null, viewModel);
    }

    public static ViewDataBinding inflate(Context context, int LayoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(context), LayoutId, null, false);
    }

    /**
     * 将子ViewModel绑定父ViewModel生命周期
     */
    @SuppressWarnings("unchecked")
    public static void bindLifecycle(BaseViewModel parent, final BaseViewModel child) {
        if (parent != null && child != null) {
            child.getLifecycleHelper().bindParent(parent);
        } else {
            throw new NullPointerException((parent == null ? "parent" : "child") + "is null");
        }
    }

}
