package io.ganguo.library.ui.adapter.v7;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import io.ganguo.library.R;
import io.ganguo.library.ui.adapter.v7.hodler.BaseViewHolder;
import io.ganguo.library.ui.view.loading.ILoadMoreViewInterface;
import io.ganguo.library.ui.widget.LoadMoreLoadingView;
import io.ganguo.utils.util.Tasks;
import io.ganguo.utils.util.log.Logger;

/**
 * <p>
 * 支持上拉加载 LoadMoreAdapter
 * <p/>
 * update by Leo on 09/14/18.
 */
public abstract class LoadMoreAdapter<T, B extends ViewDataBinding> extends BaseAdapter<T, B> {
    //LoadMore item Type
    private int loadingLayoutId = -21;
    //LoadingView Class Type
    private Class<? extends ILoadMoreViewInterface> loadingViewClass = LoadMoreLoadingView.class;
    //LoadingView
    private ILoadMoreViewInterface iLoadingView;

    private LoadMoreListener mLoadMoreListener;
    private boolean enableLoadMore = false;

    public LoadMoreAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == loadingLayoutId) {
            iLoadingView = createLoadingView();
            iLoadingView.initView(getContext(), parent);
            iLoadingView.onStopLoading();
            return new BaseViewHolder<>(iLoadingView.getViewBinding());
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder.getItemViewType() == loadingLayoutId && enableLoadMore) {
            if (iLoadingView != null) {
                iLoadingView.onStartLoading();
                loadMore();
            }
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        int size = size();
        if (enableLoadMore) {
            size = size + 1;
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && enableLoadMore) {
            return loadingLayoutId;
        }
        return super.getItemViewType(position);
    }


    /**
     * function: 结束LoadMore
     */
    public void onFinishLoadMore() {
        if (iLoadingView != null) {
            iLoadingView.onStopLoading();
        }
    }

    /**
     * function: 结束LoadMore
     *
     * @param lastPage 根据该参数，开启/关闭LoadMore功能
     */
    public void onFinishLoadMore(boolean lastPage) {
        if (lastPage) {
            // 最后一个itemViewType为loadMore时才notifyRemove
            disableLoadMore();
        } else {
            enableLoadMore();
        }
        onFinishLoadMore();
        if (size() > 0) {
            notifyItemChanged(getItemCount() - 1);
        }

    }


    /**
     * function:开启上拉加载Loading
     */
    public void enableLoadMore() {
        enableLoadMore = true;
    }


    /**
     * function:禁止上拉加载Loading
     */
    public void disableLoadMore() {
        enableLoadMore = false;
        onFinishLoadMore();
    }

    /**
     * function:onLoadMore 接口回调
     */
    public void loadMore() {
        if (mLoadMoreListener != null) {
            Tasks.handler().postDelayed(() -> mLoadMoreListener.onLoadMore(), 500);
        }
    }


    /**
     * function: 根据type类型，判断隐藏LoadMore Loading
     *
     * @param holder
     */
    @Override
    public void onViewDetachedFromWindow(BaseViewHolder<B> holder) {
        if (iLoadingView != null && holder.getItemViewType() == loadingLayoutId) {
            iLoadingView.onStopLoading();
        }
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * function: destroy LoadMore Loading View
     */
    public void destroy() {
        if (iLoadingView != null) {
            iLoadingView.onDestroy();
        }
    }


    /**
     * function: create Loading view
     *
     * @return
     */
    protected ILoadMoreViewInterface createLoadingView() {
        ILoadMoreViewInterface loadingView = null;
        try {
            loadingView = loadingViewClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return loadingView;
    }

    /**
     * function: 设置自定义LoadingView的Class类型
     *
     * @return
     */
    public LoadMoreAdapter<T, B> setLoadingViewClass(Class<? extends ILoadMoreViewInterface> loadingViewClass) {
        this.loadingViewClass = loadingViewClass;
        return this;
    }


    public LoadMoreAdapter<T, B> setLoadMoreListener(LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
        return this;
    }
}
