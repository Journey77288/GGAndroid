package io.ganguo.library.ui.adapter.v7;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.library.core.event.extend.OnSingleClickListener;
import io.ganguo.library.ui.adapter.v7.ViewHolder.BaseViewHolder;


/**
 * Created by rick on 10/20/15.
 */
public abstract class BaseAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<B>> {

    private Context mContext;
    private LayoutInflater mInflater;

    public BaseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder<B> onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(getInflater(), viewType, parent, false);
        BaseViewHolder<B> vh = new BaseViewHolder<>(binding);
        return vh;
    }

    public abstract void onBindViewBinding(BaseViewHolder<B> vh, int position);


    @Override
    public void onBindViewHolder(BaseViewHolder<B> holder, int position) {
        onBindViewBinding(holder, position);
        T itemObj = get(position);
        if (itemObj instanceof Adaptable) {
            ((Adaptable) itemObj).setAdapter(BaseAdapter.this);
        }
        holder.bindTo(itemObj);
    }


    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    protected LayoutInflater getInflater() {
        return mInflater;
    }

    public final String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    public final String getString(int resId, Object... formatArgs) {
        return getContext().getResources().getString(resId, formatArgs);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayoutId(position);
    }

    protected abstract int getItemLayoutId(int position);

    protected abstract T get(int position);

    protected abstract int size();


    @BindingAdapter(value = {"itemClick", "position"}, requireAll = false)
    public static void setOnClick(final BaseAdapter adapter, final View view, final Command command, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.execute(adapter, view, position);
            }
        });
    }

    @BindingAdapter(value = {"itemSingleClick", "position"}, requireAll = false)
    public static void setOnSingleClick(final BaseAdapter adapter, final View view, final Command command, final int position) {
        view.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                command.execute(adapter, view, position);
            }
        });
    }
}
