package io.ganguo.vmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import io.ganguo.library.ui.activity.BaseFragmentActivity;
import io.ganguo.library.ui.base.FragmentNavigator;
import io.ganguo.rx.ActivityResult;
import io.ganguo.rx.ActivityResultOwner;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.library.ui.view.FragmentActivityInterface;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Base ViewModelActivity
 * Created by Roger on 6/7/16.
 *
 * @param <T> Activity ViewLayout Binding
 * @param <B> The ViewModel control the Activity
 */
public abstract class ViewModelActivity<T extends ViewDataBinding, B extends BaseViewModel>
        extends BaseFragmentActivity
        implements FragmentActivityInterface<T>, BaseViewModel.OnViewAttachListener<B>, ActivityResultOwner {
    private B mViewModel;
    private T mBinding;
    private Subject<ActivityResult> resultEmitter;

    public boolean checkViewModel() {
        boolean isNotNull = getViewModel() != null;
        if (!isNotNull) {
            Logger.e("viewModel is null");
        }
        return isNotNull;
    }

    @Override
    public void beforeInitView() {
        mBinding = DataBindingUtil.setContentView(this, getViewModel().getItemLayoutId());
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        getViewModel().setOnViewAttachListener(this);
    }

    @Override
    public void initData() {
        ViewModelHelper.bind((ActivityInterface) this, getViewModel());
    }

    @Override
    public Subject<ActivityResult> getActivityResult() {
        resultEmitter = PublishSubject.<ActivityResult>create().toSerialized();
        resultEmitter.hide();
        return resultEmitter;
    }

    /**
     * get ViewModel here
     *
     * @see #createViewModel()
     */
    public B getViewModel() {
        if (mViewModel == null) {
            mViewModel = createViewModel();
        }
        return mViewModel;
    }

    @Override
    public T getBinding() {
        return mBinding;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Bundle getBundle() {
        if (getIntent() == null) {
            return null;
        }
        return getIntent().getExtras();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resultEmitter = null;
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onDestroy();
        }
    }

    @Override
    public FragmentNavigator getNavigator() {
        return super.getNavigator();
    }

    /**
     * Forward activity Result to viewModel
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultEmitter != null) {
            resultEmitter.onNext(new ActivityResult(requestCode, resultCode, data));
            resultEmitter.onComplete();
        }
    }

    /**
     * Create ViewModel here
     * if you want to getViewModel instance use {@link #getViewModel()} instead this
     */
    public abstract B createViewModel();

}
