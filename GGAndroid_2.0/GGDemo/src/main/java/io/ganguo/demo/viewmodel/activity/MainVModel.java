package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import com.mlsdev.rximagepicker.RxImagePicker;
import com.readystatesoftware.chuck.Chuck;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.demo.R;
import io.ganguo.demo.bean.Constants;
import io.ganguo.demo.bean.ConstantsBus;
import io.ganguo.demo.entity.CommonDemoEntity;
import io.ganguo.demo.view.activity.BannerDemoActivity;
import io.ganguo.demo.view.activity.CollapsingToolbarDemoActivity;
import io.ganguo.demo.view.activity.CoordinatorAppBarDemoActivity;
import io.ganguo.demo.view.activity.CustomLoadMoreStyleActivity;
import io.ganguo.demo.view.activity.DialogDemoActivity;
import io.ganguo.demo.view.activity.DiffUtilDemoActivity;
import io.ganguo.demo.view.activity.HFSRDemoActivity;
import io.ganguo.demo.view.activity.HttpDemoActivity;
import io.ganguo.demo.view.activity.ObjectBoxDemoActivity;
import io.ganguo.demo.view.activity.ResultDemoActivity;
import io.ganguo.demo.view.activity.RxBusDemoActivity;
import io.ganguo.demo.view.activity.RxDemoActivity;
import io.ganguo.demo.view.activity.RxQiniuDemoActivity;
import io.ganguo.demo.view.activity.SelectManagerDemoActivity;
import io.ganguo.demo.view.activity.TabHorizontalLayoutDemoActivity;
import io.ganguo.demo.view.activity.ViewPagerDemoActivity;
import io.ganguo.demo.view.activity.WebViewDemoActivity;
import io.ganguo.library.Config;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxActivityResult;
import io.ganguo.rx.RxFilter;
import io.ganguo.rx.bus.RxBus;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.Permissions;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.item.ItemSampleVModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Roger on 7/13/16.
 * MainVModel
 */
public class MainVModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {
    protected ArrayList<BaseViewModel> demoViewModel = new ArrayList<>();


    public MainVModel() {
        //RxBus订阅事件，放构造函数，尤其是ItemViewModel，否则可能出现内存泄露，导致程序线程OOM
        subRxBusDemo();
    }


    @Override
    @SuppressWarnings("unchecked")
    public void onViewAttached(View view) {
        getAdapter().onFinishLoadMore(true);
        initObjectBoxDemo();
        initDiffUtilDemo();
        initRxBusDemo();
        initCustomAdapterLoadMoreLoadingDemo();
        initViewModelDialogDemo();
        initHttpDemo();
        initChuckDemo();
        initCoordinatorAppBarDemo();
        initCoordinatorCollapsingToolbarDemo();
        initBannerViewModelDemo();
        initActivityResultDemo();
        initHFSRecyclerViewDemo();
        initWebViewModelDemo();
        initViewPagerGalleryDemo();
        initRxJavaDemo();
        initRxQiniuDemo();
        initPermissionDemo();
        initOpenCameraDemo();
        initOpenGalleryDemo();
        initTabLayoutDemo();
        initSelectManagerDemo();

        getAdapter().addAll(demoViewModel);
        getAdapter().notifyDataSetChanged();
        toggleEmptyView();

    }


    /**
     * function：ObjectBox 数据库 Demo
     */
    protected void initObjectBoxDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("ObjectBox 数据库",
                        RxActions.startActivity(getView().getActivity(),
                                ObjectBoxDemoActivity.intentFor(getView().getActivity()))));
    }

    /**
     * function：Custom LoadMoreLoading Style Demo
     */
    protected void initCustomAdapterLoadMoreLoadingDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("Custom LoadMoreLoading Style",
                        RxActions.startActivity(getView().getActivity(),
                                CustomLoadMoreStyleActivity.intentFor(getView().getActivity()))));
    }

    /**
     * function：订阅RxBus
     */
    protected void subRxBusDemo() {
        RxBus.getDefault()
                .receiveEvent(String.class, ConstantsBus.BUS_DEMO_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxFilter.filterNotNull())
                .doOnNext(s -> {
                    ToastHelper.showMessage("接收到RxBus数据：" + s);
                    getAdapter().add(0, ItemSampleVModel.onCrateItemViewModel(R.color.orange_light, "RxBus:Data:" + s, null));
                    getAdapter().notifyDataSetChanged();
                })
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER);
    }


    /**
     * function：DiffUtil Demo
     */
    protected void initDiffUtilDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("DiffUtil Demo",
                        RxActions.startActivity(getView().getActivity(),
                                DiffUtilDemoActivity.intentFor(getView().getActivity()))));
    }

    /**
     * function：RxBus Demo
     */
    protected void initRxBusDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("RxBus Demo",
                        RxActions.startActivity(getView().getActivity(),
                                RxBusDemoActivity.intentFor(getView().getActivity()))));
    }


    /**
     * function：Dialog Demo
     */
    protected void initViewModelDialogDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("Dialog Demo",
                        RxActions.startActivity(getView().getActivity(),
                                DialogDemoActivity.intentFor(getView().getActivity()))));
    }

    /**
     * function：Http Chuck
     */
    protected void initChuckDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("Chuck Http抓包调试 Demo",
                        RxActions.startActivity(getView().getActivity(),
                                Chuck.getLaunchIntent(getView().getActivity()))));
    }

    /**
     * function：Http Demo
     */
    protected void initHttpDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("Http Demo",
                        RxActions.startActivity(getView().getActivity(),
                                HttpDemoActivity.intentFor(getView().getActivity(), new CommonDemoEntity().setText("测试---哈哈哈")))));
    }

    /**
     * function：CoordinatorAppBarVModel Demo
     */
    protected void initCoordinatorAppBarDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("CoordinatorAppBarVModel Demo",
                        RxActions.startActivity(getView().getActivity(),
                                CoordinatorAppBarDemoActivity.intentFor(getView().getActivity()))));
    }


    /**
     * function： CoordinatorCollapsingToolbar Demo
     */
    protected void initCoordinatorCollapsingToolbarDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("CollapsingToolbarViewModel Demo",
                        RxActions.startActivity(getView().getActivity(),
                                CollapsingToolbarDemoActivity.intentFor(getView().getActivity()))));
    }


    /**
     * function：BannerViewModel Demo
     */
    protected void initBannerViewModelDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("BannerViewModel Demo",
                        RxActions.startActivity(getView().getActivity(),
                                BannerDemoActivity.intentFor(getView().getActivity()))));
    }


    /**
     * function：ActivityResult Demo
     */
    protected void initActivityResultDemo() {
        demoViewModel.add(ItemSampleVModel.onCrateItemViewModel("ActivityResult Demo", new Action() {
            @Override
            public void run() throws Exception {
                RxActivityResult
                        .startIntent(getView().getActivity(), ResultDemoActivity.intentFor(getView().getActivity()), 1000)
                        .subscribeOn(Schedulers.io())
                        .filter(result -> result.getRequestCode() == 1000)
                        .filter(result -> result.isResultOK())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(result -> {
                            String data = result.getData().getStringExtra(Constants.DATA);
                            ToastHelper.showMessage("ActivityResult:data:" + data);
                        })
                        .compose(RxVMLifecycle.bindViewModel(MainVModel.this))
                        .subscribe(Functions.emptyConsumer(), RxActions.printThrowable());
            }
        }));
    }


    /**
     * function：HFSRecyclerView Demo
     */
    protected void initHFSRecyclerViewDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("HFSRecyclerView demo",
                        RxActions.startActivity(getView().getActivity(),
                                HFSRDemoActivity.intentFor(getContext()))));
    }


    /**
     * function：WebViewModel Demo
     */
    protected void initWebViewModelDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("WebView Demo",
                        RxActions.startActivity(getView().getActivity(),
                                WebViewDemoActivity.intentFor(getContext()))));
    }


    /**
     * function：ViewPager 简单画廊效果 Demo
     */
    protected void initViewPagerGalleryDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("ViewPager 简单画廊效果 Demo",
                        RxActions.startActivity(getView().getActivity(),
                                ViewPagerDemoActivity.intentFor(getContext()))));
    }

    /**
     * function：TabLayout Demo
     */
    protected void initTabLayoutDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("TabLayout Demo",
                        RxActions.startActivity(getView().getActivity(),
                                TabHorizontalLayoutDemoActivity.intentFor(getContext()))));
    }


    /**
     * function：RxJava DEMO
     */
    protected void initRxJavaDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("RxJava Demo",
                        RxActions.startActivity(getView().getActivity(),
                                RxDemoActivity.intentFor(getContext()))));
    }


    /**
     * function：七牛上传图片 Demo
     */
    protected void initRxQiniuDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("七牛上传图片 Demo",
                        RxActions.startActivity(getView().getActivity(),
                                RxQiniuDemoActivity.intentFor(getContext()))));
    }

    /**
     * function：选择管理器Demo
     */
    protected void initSelectManagerDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("选择管理器 Demo",
                        RxActions.startActivity(getView().getActivity(),
                                SelectManagerDemoActivity.intentFor(getContext()))));
    }


    /**
     * function：获取权限
     */
    protected void initPermissionDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("权限获取 Demo",
                        onReqPermissionAction()));
    }


    /**
     * function：打开相册Demo
     */
    protected void initOpenCameraDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("打开相机 Demo",
                        () -> rxPickerImgFile(RxImagePicker.ChooseMode.CAMERA)));
    }

    /**
     * function：打开相册Demo
     */
    protected void initOpenGalleryDemo() {
        demoViewModel.add(
                ItemSampleVModel.onCrateItemViewModel("打开相册 Demo",
                        () -> rxPickerImgFile(RxImagePicker.ChooseMode.GALLERY)));
    }


    /**
     * function：选择图片
     *
     * @param mode CAMERA/GALLERY -> 相机/相册
     * @return
     */
    protected void rxPickerImgFile(@RxImagePicker.ChooseMode final int mode) {
        RxImagePicker
                .get()
                .setChooseMode(mode)
                .setSaveImagePath(Config.getImagePath().getAbsolutePath())
                .start(getContext())
                .compose(RxFilter.<File>filterNotNull())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(file -> {
                    String modeStr = (mode == RxImagePicker.ChooseMode.CAMERA) ? "CAMERA" : "GALLERY";
                    Logger.e("RxImagePicker " + modeStr + " " + file.getAbsolutePath());
                })
                .compose(RxVMLifecycle.bindViewModel(MainVModel.this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + ""));
    }


    /**
     * function：请求权限
     *
     * @return
     */
    private Action onReqPermissionAction() {
        return () -> Permissions.requestPermission(getContext(), "需要授予内存读取权限，App XX功能才能正常使用", new Permissions.onRequestPermissionListener() {
            @Override
            public void onRequestSuccess(List<String> successPermissions) {
                ToastHelper.showMessage("请求：" + successPermissions.toString() + "权限成功");
            }

            @Override
            public void onRequestFailure(List<String> failurePermissions) {
                ToastHelper.showMessage("请求：" + failurePermissions.toString() + "权限失败");
            }
        }, Permission.Group.STORAGE);
    }


}
