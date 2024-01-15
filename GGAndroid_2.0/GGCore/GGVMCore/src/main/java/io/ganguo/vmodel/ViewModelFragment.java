package io.ganguo.vmodel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.library.ui.fragment.BaseFragment;
import io.ganguo.rx.ActivityResult;
import io.ganguo.rx.ActivityResultOwner;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.library.ui.view.ActivityInterface;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Base ViewModelFragment
 * Created by Roger on 6/7/16.
 *
 * @param <V> Fragment ViewLayout Binding
 * @param <B> The ViewModel control the Fragments behaviour
 */
public abstract class ViewModelFragment<V extends ViewDataBinding, B extends BaseViewModel>
        extends BaseFragment
        implements ActivityInterface<V>, BaseViewModel.OnViewAttachListener<B>, ActivityResultOwner {

    private V mBinding;
    private B mViewModel;
    private Subject<ActivityResult> resultEmitter;

    public boolean checkViewModel() {
        boolean isNotNull = getViewModel() != null;
        if (!isNotNull) {
            Logger.e("viewModel is null");
        }
        return isNotNull;
    }

    /**
     * get ViewModel here
     */
    public B getViewModel() {
        if (mViewModel == null) {
            mViewModel = createViewModel();
        }
        return mViewModel;
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @Override
    public V getBinding() {
        return mBinding;
    }

    @Override
    public int getLayoutResourceId() {
        return getViewModel().getItemLayoutId();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void initListener() {
        getViewModel().setOnViewAttachListener(this);
    }

    @Override
    public void initData() {
        ViewModelHelper.bind(this, getViewModel());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resultEmitter = null;
        if (checkViewModel()) {
            getViewModel().getLifecycleHelper().onDestroy();
        }
    }

    @Override
    public Subject<ActivityResult> getActivityResult() {
        resultEmitter = PublishSubject.create();
        return resultEmitter;
    }

    /**
     * Forward activity Result to viewModel
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultEmitter != null) {
            resultEmitter.onNext(new ActivityResult(requestCode, resultCode, data));
            resultEmitter.onComplete();
        }
    }

    /**
     * Create ViewModel here
     */
    @NonNull
    public abstract B createViewModel();
}
