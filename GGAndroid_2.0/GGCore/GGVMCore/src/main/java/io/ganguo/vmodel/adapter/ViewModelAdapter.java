package io.ganguo.vmodel.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.library.ui.adapter.v7.SimpleAdapter;
import io.ganguo.library.ui.adapter.v7.callback.IDiffComparator;
import io.ganguo.library.ui.adapter.v7.hodler.BaseViewHolder;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxHelper;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.adapter.callback.ViewModelDiffCallback;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;

/**
 * Created by Roger on 5/29/16.
 */
public class ViewModelAdapter<B extends ViewDataBinding> extends SimpleAdapter<BaseViewModel, B> {
    private List<BaseViewModel> oldViewModels = new ArrayList<>();
    private BaseViewModel parent;
    private Disposable diffDisposable;
    private OnNotifyDiffUtilDataChangedListener dataChangedListener;

    @Deprecated
    public ViewModelAdapter(Context context) {
        super(context);
    }

    public ViewModelAdapter(Context context, BaseViewModel parent) {
        super(context);
        this.parent = parent;
    }

    @Override
    public void onBindViewBinding(BaseViewHolder<B> vh, int position) {
        super.onBindViewBinding(vh, position);
        ViewModelHelper.bind(this, vh, get(position));
    }

    public BaseViewModel getParent() {
        return parent;
    }


    /**
     * 数据刷新变动回到接口
     *
     * @param dataChangedListener
     * @return
     */
    public ViewModelAdapter<B> setDataChangedListener(OnNotifyDiffUtilDataChangedListener dataChangedListener) {
        this.dataChangedListener = dataChangedListener;
        return this;
    }

    /**
     * oldViewModels
     *
     * @return
     */
    public List<BaseViewModel> getOldViewModels() {
        return oldViewModels;
    }


    /**
     * 删除数据
     *
     * @param isRefresh 是否立即刷新Item显示状态
     * @param viewModel
     * @return
     */
    public void remove(BaseViewModel viewModel, boolean isRefresh) {
        int index = -1;
        if (isRefresh) {
            index = indexOf(viewModel);
        }
        remove(viewModel);
        if (index != -1) {
            notifyItemRemoved(index);
        }
    }

    /**
     * 添加数据
     *
     * @param isRefresh 是否立即刷新Item显示状态
     * @param viewModel
     * @return
     */
    public void add(BaseViewModel viewModel, boolean isRefresh) {
        add(viewModel);
        if (isRefresh) {
            notifyItemInserted(getItemCount());
        }
    }


    /**
     * function：通过DiffUtil刷新Adapter，优化性能
     *
     * @link<a href="https://gitlab.cngump.com/ganguo_android/ggandroid/wikis/project-util-diffutil">使用说明文档<a/>
     * 1、@see {@link IDiffComparator} 使用该方法时，ItemViewModel 必须实 IDiffComparator接口，否则会出现刷新动画闪烁、混乱的情况
     */
    public void notifyDiffUtilSetDataChanged() {
        RxHelper.safeDispose(diffDisposable);
        diffDisposable = Observable
                .create((ObservableOnSubscribe<DiffUtil.DiffResult>) emitter -> {
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ViewModelDiffCallback<>(oldViewModels, getData()), false);
                    emitter.onNext(diffResult);
                    emitter.onComplete();
                })
                .doOnNext(diffResult -> {
                    diffResult.dispatchUpdatesTo(ViewModelAdapter.this);
                })
                .doOnComplete(() -> {
                    RxHelper.safeDispose(diffDisposable);
                    oldViewModels.clear();
                    oldViewModels.addAll(getData());
                    if (dataChangedListener != null) {
                        dataChangedListener.onAdapterNotifyComplete();
                    }
                })
                .compose(RxVMLifecycle.<DiffUtil.DiffResult>bindViewModel(parent))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "--notifyDiffUtilSetDataChanged--"));
    }

    /**
     * function：刷新完成回到接口
     */
    public interface OnNotifyDiffUtilDataChangedListener {
        /**
         * 刷新完成回调接口
         */
        void onAdapterNotifyComplete();
    }

}
