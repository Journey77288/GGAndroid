package io.ganguo.vmodel;


import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.rx.RxActions;
import io.ganguo.utils.util.Collections;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ViewModel生命周期辅助类
 * Created by Roger on 7/4/16.
 */
public class VMLifecycleHelper {
    private BaseViewModel viewModel;
    private List<VMLifecycleHelper> childLifecycleHelperList;
    public static final String EVENT_VIEW_MODEL_DETACH = "View Model Detached";
    public static final String EVENT_VIEW_MODEL_RESUME = "View Model Resume";
    public static final String EVENT_VIEW_MODEL_PAUSE = "View Model Pause";
    public static final String EVENT_VIEW_MODEL_STOP = "View Model Stop";

    private BehaviorRelay<String> onResumeBehavior = BehaviorRelay.create();
    private BehaviorRelay<String> onPauseBehavior = BehaviorRelay.create();
    private BehaviorRelay<String> onStopBehavior = BehaviorRelay.create();
    private BehaviorRelay<String> onDestroyBehavior = BehaviorRelay.create();

    private CompositeDisposable disposables = new CompositeDisposable();

    private VMLifecycleHelper() {
    }

    private VMLifecycleHelper(final BaseViewModel viewModel) {
        this.viewModel = viewModel;
        bindOnResume();
        bindOnPause();
        bindOnStop();
        bindOnDestroy();
    }

    public static VMLifecycleHelper register(BaseViewModel viewModel) {
        return new VMLifecycleHelper(viewModel);
    }

    public void bindParent(BaseViewModel parent) {
        parent.getLifecycleHelper().addChild(this);
    }

    public void unRegister() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        this.viewModel = null;
    }

    private void bindOnPause() {
        Disposable s = getOnPauseBehavior().subscribe(onPauseAction, RxActions.printThrowable());
        disposables.add(s);
    }

    private void bindOnResume() {
        Disposable s = getOnResumeBehavior().subscribe(onResumeAction, RxActions.printThrowable());
        disposables.add(s);
    }

    private void bindOnStop() {
        Disposable s = getOnStopBehavior().subscribe(onStopAction, RxActions.printThrowable());
        disposables.add(s);
    }

    private void bindOnDestroy() {
        Disposable s = getOnDestroyBehavior().subscribe(onDestroyAction, RxActions.printThrowable());
        disposables.add(s);
    }

    private Consumer<String> onPauseAction = new Consumer<String>() {
        @Override
        public void accept(String s) throws Exception {
            if (viewModel != null) {
//            logger.d(String.format(Locale.US, "%s: %s", viewModel.getClass().getSimpleName(), message));
                if (Collections.isNotEmpty(childLifecycleHelperList)) {
                    Observable.fromIterable(childLifecycleHelperList)
                            .subscribe(new Consumer<VMLifecycleHelper>() {
                                @Override
                                public void accept(VMLifecycleHelper helper) {
                                    helper.getOnPauseBehavior().accept(EVENT_VIEW_MODEL_PAUSE);
                                }
                            });
                }
                viewModel.onPause();
            }
        }
    };

    private Consumer<String> onResumeAction = new Consumer<String>() {
        @Override
        public void accept(String message) {
            if (viewModel != null) {
//                logger.d(String.format(Locale.US, "%s: %s", viewModel.getClass().getSimpleName(), message));
                if (Collections.isNotEmpty(childLifecycleHelperList)) {
                    Observable.fromIterable(childLifecycleHelperList)
                            .subscribe(new Consumer<VMLifecycleHelper>() {
                                @Override
                                public void accept(VMLifecycleHelper helper) {
                                    helper.getOnResumeBehavior().accept(EVENT_VIEW_MODEL_RESUME);
                                }
                            });
                }
                viewModel.onResume();
            }
        }
    };

    private Consumer<String> onStopAction = new Consumer<String>() {
        @Override
        public void accept(String message) {
            if (viewModel != null) {
//            logger.d(String.format(Locale.US, "%s: %s", viewModel.getClass().getSimpleName(), message));
                if (Collections.isNotEmpty(childLifecycleHelperList)) {
                    // call child view model destroy
                    Observable.fromIterable(childLifecycleHelperList)
                            .subscribe(new Consumer<VMLifecycleHelper>() {
                                @Override
                                public void accept(VMLifecycleHelper helper) {
                                    helper.getOnStopBehavior().accept(EVENT_VIEW_MODEL_STOP);
                                }
                            });
                }
                viewModel.onStop();
            }
        }
    };

    private Consumer<String> onDestroyAction = new Consumer<String>() {
        @Override
        public void accept(String message) {
            if (viewModel != null) {
//                logger.d(String.format(Locale.US, "%s: %s", viewModel.getClass().getSimpleName(), message));
                if (Collections.isNotEmpty(childLifecycleHelperList)) {
                    // call child view model destroy
                    Observable.fromIterable(childLifecycleHelperList)
                            .subscribe(new Consumer<VMLifecycleHelper>() {
                                @Override
                                public void accept(VMLifecycleHelper helper) {
                                    helper.getOnDestroyBehavior().accept(EVENT_VIEW_MODEL_DETACH);
                                }
                            });
                }
                // destroy self
                viewModel.onDestroy();
            }
        }
    };

    public void addChild(VMLifecycleHelper helper) {
        if (childLifecycleHelperList == null) {
            childLifecycleHelperList = new ArrayList<>();
        }
        childLifecycleHelperList.add(helper);
    }

    public List<VMLifecycleHelper> getChildList() {
        return childLifecycleHelperList;
    }


    /**
     * 在ViewModel外部注入生命周期额外的操作,
     * 只需给对应的 Behavior subscribe 一个Action或Subscriber
     * 可订阅多个Action或Subscriber
     * 当触发对应Behavior,全部Subscriber都会触发
     *
     * @return
     */
    @NonNull
    public BehaviorRelay<String> getOnPauseBehavior() {
        return onPauseBehavior;
    }

    @NonNull
    public BehaviorRelay<String> getOnResumeBehavior() {
        return onResumeBehavior;
    }

    @NonNull
    public BehaviorRelay<String> getOnStopBehavior() {
        return onStopBehavior;
    }

    @NonNull
    public BehaviorRelay<String> getOnDestroyBehavior() {
        return onDestroyBehavior;
    }

    public void onDestroy() {
        getOnDestroyBehavior().accept(VMLifecycleHelper.EVENT_VIEW_MODEL_DETACH);
    }

    public void onStop() {
        getOnStopBehavior().accept(VMLifecycleHelper.EVENT_VIEW_MODEL_STOP);
    }

    public void onResume() {
        getOnResumeBehavior().accept(VMLifecycleHelper.EVENT_VIEW_MODEL_RESUME);
    }

    public void onPause() {
        getOnPauseBehavior().accept(VMLifecycleHelper.EVENT_VIEW_MODEL_RESUME);
    }

    /**
     * 生命周期Event
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({EVENT_VIEW_MODEL_DETACH, EVENT_VIEW_MODEL_PAUSE, EVENT_VIEW_MODEL_RESUME, EVENT_VIEW_MODEL_STOP})
    public @interface Lifecycle {

    }
}
