package io.ganguo.incubator.viewmodel.base;

import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * <p>
 * 基于HFRecyclerViewModel封装基类，用于处理一些通用属性或者后期扩展
 * 注意：App内要用到HFRecyclerViewModel的地方，都直接继承APPHFRecyclerViewModel
 * </p>
 * Created by leo on 2018/9/15.
 */
public abstract class APPHFRecyclerViewModel<T extends ViewInterface<IncludeHfRecyclerBinding>> extends HFRecyclerViewModel<T> {
}
