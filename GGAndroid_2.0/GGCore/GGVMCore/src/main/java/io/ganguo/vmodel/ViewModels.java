package io.ganguo.vmodel;

import android.databinding.ViewDataBinding;

import io.ganguo.utils.util.Validates;
import io.ganguo.vmodel.view.AdapterInterface;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * ViewModel工具类
 * Created by Roger on 7/26/16.
 */
public class ViewModels {
    /**
     * 转换普通 ViewInterface 为 AdapterViewInterface
     */
    @SuppressWarnings("unchecked raw type")
    public static <T extends ViewDataBinding> AdapterInterface<T> toAdapterView(ViewInterface viewInterface) {
        Validates.notNull(viewInterface);

        AdapterInterface adapterView = null;
        if (viewInterface instanceof AdapterInterface) {
            adapterView = (AdapterInterface) viewInterface;
        }
        return adapterView;
    }
}
