package io.ganguo.demo.viewmodel.activity;


import android.content.Intent;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivitySplashBinding;
import io.ganguo.demo.view.activity.MainActivity;
import io.ganguo.rx.RxActions;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.library.ui.view.ActivityInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 * Splash ViewModel
 * </p>
 * Created by leo on 2018/8/31.
 */
public class SplashVModel extends BaseViewModel<ActivityInterface<ActivitySplashBinding>> {
    @Override
    public void onViewAttached(View view) {
        Observable
                .create((ObservableOnSubscribe<Intent>) emitter -> {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    emitter.onNext(intent);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(intent -> {
                    getContext().startActivity(intent);
                    getView().getActivity().finish();
                })
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName()));
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_splash;
    }
}
