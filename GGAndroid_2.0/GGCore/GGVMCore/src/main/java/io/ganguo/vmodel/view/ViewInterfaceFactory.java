package io.ganguo.vmodel.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import io.ganguo.library.ui.adapter.v7.hodler.BaseViewHolder;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.library.ui.view.DialogInterface;
import io.ganguo.library.ui.view.PopupWindowInterface;
import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.library.ui.widget.BasePopupWindow;
import io.ganguo.vmodel.adapter.ViewModelAdapter;

/**
 * ViewInterface工厂,用于创建ViewInterface
 * Created by Roger on 6/21/16.
 */

public class ViewInterfaceFactory {

    public static ViewInterface viewFactory(Context context, int layoutId) {
        ViewDataBinding binding = inflate(context, layoutId);
        return ViewInterfaceFactory.viewFactory(binding);
    }

    public static ViewInterface viewFactoryForActivity(Activity activity, int layoutId) {
        ViewDataBinding binding = DataBindingUtil.setContentView(activity, layoutId);
        return ViewInterfaceFactory.activityViewFactory(activity, binding);
    }

    public static <V extends ViewDataBinding> ViewInterface<V> viewFactory(V binding) {
        return new ViewInterface<V>() {
            @Override
            public Context getContext() {
                return binding.getRoot().getContext();
            }

            @Override
            public V getBinding() {
                return binding;
            }
        };
    }

    public static <V extends ViewDataBinding> AdapterInterface<V> adapterViewFactory(final ViewModelAdapter<V> adapter, final BaseViewHolder<V> vh) {
        return new AdapterInterface<V>() {
            @Override
            public ViewModelAdapter<V> getAdapter() {
                return adapter;
            }

            @Override
            public BaseViewHolder<V> getViewHolder() {
                return vh;
            }

            @Override
            public Context getContext() {
                return adapter.getContext();
            }

            @Override
            public V getBinding() {
                return vh.getBinding();
            }
        };
    }

    public static <V extends ViewDataBinding> DialogInterface<V> dialogViewFactory(Dialog dialog, V binding) {
        if (dialog == null) {
            throw new NullPointerException("dialog is null");
        }
        return new DialogInterface<V>() {
            @Override
            public Dialog getDialog() {
                return dialog;
            }

            @Override
            public Context getContext() {
                return dialog.getContext();
            }

            @Override
            public V getBinding() {
                return binding;
            }
        };
    }

    public static ActivityInterface activityViewFactory(Activity activity, ViewDataBinding binding) {
        return new ActivityInterface() {

            @Override
            public Activity getActivity() {
                return activity;
            }

            @Override
            public Bundle getBundle() {
                if (activity.getIntent() == null) {
                    return null;
                }
                return activity.getIntent().getExtras();
            }

            @Override
            public Context getContext() {
                return activity;
            }

            @Override
            public ViewDataBinding getBinding() {
                return binding;
            }
        };
    }

    private static ViewDataBinding inflate(Context context, int LayoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(context), LayoutId, null, false);
    }


    public static <V extends ViewDataBinding> PopupWindowInterface<V> popupWindowViewFactory(BasePopupWindow popupWindow, V binding) {
        if (popupWindow == null) {
            throw new NullPointerException("dialog is null");
        }
        return new PopupWindowInterface<V>() {
            @Override
            public BasePopupWindow getPopupWindow() {
                return popupWindow;
            }


            @Override
            public Context getContext() {
                return popupWindow.getContext();
            }

            @Override
            public V getBinding() {
                return binding;
            }
        };
    }
}
