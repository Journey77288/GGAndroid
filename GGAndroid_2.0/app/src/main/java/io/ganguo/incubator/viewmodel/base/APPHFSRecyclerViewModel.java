package io.ganguo.incubator.viewmodel.base;

import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.ganguo.library.ui.view.ViewInterface;

/**
 * <p>
 * 基于HFRecyclerViewModel封装基类，用于处理一些通用属性或者后期扩展
 * 注意：App内要用到HFRecyclerViewModel的地方，都直接继承APPHFRecyclerViewModel
 * </p>
 * Created by leo on 2018/9/15.
 */
public abstract class APPHFSRecyclerViewModel<T extends ViewInterface<IncludeHfSwipeRecyclerBinding>> extends HFSRecyclerViewModel<T> {
}
