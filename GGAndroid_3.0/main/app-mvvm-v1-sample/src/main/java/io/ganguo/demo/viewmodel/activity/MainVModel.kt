package io.ganguo.demo.viewmodel.activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chuckerteam.chucker.api.Chucker
import io.ganguo.app.core.Config
import io.ganguo.appcompat.widget.GridWrapperLayoutManager
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.demo.R
import io.ganguo.demo.bean.Constants
import io.ganguo.demo.bean.ConstantsEvent
import io.ganguo.demo.view.activity.*
import io.ganguo.demo.view.dialog.LanguageDialog
import io.ganguo.log.core.Logger
import io.ganguo.rx.ActivityResult
import io.ganguo.rx.RxActions
import io.ganguo.rx.RxActivityResult
import io.ganguo.rx.bus.RxBus
import io.ganguo.rximagepicker2.RxImagePicker
import io.ganguo.rximagepicker2.RxImagePickerMode
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.requestCameraPermissions
import io.ganguo.utils.util.requestStoragePermissions
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.pack.RxVMLifecycle
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.databinding.IncludeRecyclerBinding
import io.ganguo.viewmodel.pack.common.HFRecyclerViewModel
import io.ganguo.viewmodel.pack.common.RecyclerViewModel
import io.ganguo.viewmodel.pack.common.item.ItemSampleVModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.internal.functions.Functions
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * <pre>
 * author : leo
 * time   : 2018/10/20
 * desc   : Demo MainViewModel
</pre> *
 */
open class MainVModel : HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>>() {
    private var demoViewModel = ArrayList<BaseViewModel<*>>()

    init {
        //RxBus订阅事件，放构造函数，尤其是ItemViewModel，否则可能出现内存泄露，导致程序线程OOM
        subRxBusDemo()
    }

    override fun initRecyclerViewModel(): RecyclerViewModel<IncludeRecyclerBinding, ViewInterface<IncludeRecyclerBinding>> {
        var vModel = super.initRecyclerViewModel()
        vModel.layoutManager(GridWrapperLayoutManager(context, 3, GridLayoutManager.VERTICAL, false))
        return vModel
    }


    override fun onViewAttached(view: View) {
        initLanguageHelperDemo()
        initDatabaseKotlinDemo()
        initDiffUtilDemo()
        initRxBusDemo()
        initViewModelDialogDemo()
        initHttpDemo()
        initChuckDemo()
        initCoordinatorAppBarDemo()
        initCoordinatorCollapsingToolbarDemo()
        initBannerViewModelDemo()
        initActivityResultDemo()
        initHFSRecyclerViewDemo()
        initWebViewModelDemo()
        initViewPagerGalleryDemo()
        initRxJavaDemo()
        initPermissionDemo()
        initOpenCameraDemo()
        initOpenGalleryDemo()
        initTabLayoutDemo()
        initSelectManagerDemo()
        initSideBarDemo()
        initLazyLoadDemo()
        initLoadingHelperDemo()
        initHtmlTextViewDemo()
        initCropDemo()
        initKeyBoardWatchDemo()
        initSaveViewDemo()
        initPushDemo()
        initSwitchButtonDemo()
        initPageStateSwitchDemo()
        initAppUpdateDemo()
        adapter.addAll(demoViewModel)
        adapter.notifyDataSetChanged()
        toggleEmptyView()

        Logger.e("initRecyclerView:Main:" + adapter.data.size)
        Logger.e("initRecyclerView:Main:adapter:" + adapter)
        Logger.e("initRecyclerView:Main:childCount:" + recyclerView.childCount)

    }

    /**
     * 语言切换工具 Demo
     */
    private fun initLanguageHelperDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_language_switch), Action {
                    val dialog = LanguageDialog(context)
                    dialog.show()
                }))
    }


    /**
     * ObjectBox 数据库 Kotlin Version Demo
     */
    private fun initDatabaseKotlinDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_database_kotlin),
                        RxActions.startActivity(viewInterface.activity,
                                DatabaseKotlinDemoActivity.intentFor(viewInterface.activity))))
    }


    /**
     * 订阅RxBus
     */
    private fun subRxBusDemo() {
        RxBus.getDefault()
                .receiveEvent(String::class.java, ConstantsEvent.BUS_DEMO_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { s ->
                    ToastHelper.showMessage("接收到RxBus数据：$s")
                    adapter.add(0, ItemSampleVModel.newItemViewModel(R.color.orange_light, "RxBus:Data:$s", null))
                    adapter.notifyDataSetChanged()
                }
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER)
    }


    /**
     * DiffUtil Demo
     */
    private fun initDiffUtilDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_diff_util),
                        RxActions.startActivity(viewInterface.activity,
                                DiffUtilDemoActivity.intentFor(viewInterface.activity))))
    }

    /**
     * RxBus Demo
     */
    private fun initRxBusDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel("RxBus Demo",
                        RxActions.startActivity(viewInterface.activity,
                                RxBusDemoActivity.intentFor(viewInterface.activity))))
    }


    /**
     * Dialog Demo
     */
    private fun initViewModelDialogDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_dialog),
                        RxActions.startActivity(viewInterface.activity,
                                DialogDemoActivity.intentFor(viewInterface.activity))))
    }

    /**
     * Http Chuck
     */
    private fun initChuckDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_http_chuck),
                        RxActions.startActivity(viewInterface.activity,
                                Chucker.getLaunchIntent(viewInterface.activity, Chucker.SCREEN_HTTP))))
    }

    /**
     * Http Demo
     */
    private fun initHttpDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_http),
                        RxActions.startActivity(viewInterface.activity,
                                HttpDemoActivity.intentFor(viewInterface.activity))))
    }

    /**
     * CoordinatorAppBarVModel Demo
     */
    private fun initCoordinatorAppBarDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_folding_linkage),
                        RxActions.startActivity(viewInterface.activity,
                                CoordinatorAppBarDemoActivity.intentFor(viewInterface.activity))))
    }


    /**
     * CoordinatorCollapsingToolbar Demo
     */
    private fun initCoordinatorCollapsingToolbarDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_folding_linkage_toolbar),
                        RxActions.startActivity(viewInterface.activity,
                                CollapsingToolbarDemoActivity.intentFor(viewInterface.activity))))
    }


    /**
     * BannerViewModel Demo
     */
    private fun initBannerViewModelDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_banner),
                        RxActions.startActivity(viewInterface.activity,
                                BannerDemoActivity.intentFor(viewInterface.activity))))
    }


    /**
     * ActivityResult Demo
     */
    private fun initActivityResultDemo() {
        demoViewModel.add(ItemSampleVModel.newItemViewModel(getString(R.string.str_home_rx_activity_result), Action {
            RxActivityResult
                    .startIntent(viewInterface.activity, ResultDemoActivity.intentFor(viewInterface.activity), 1000)
                    .subscribeOn(Schedulers.io())
                    .filter { result -> result.requestCode == 1000 }
                    .filter { result -> result.isResultOK }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { result ->
                        val data = result.data.getStringExtra(Constants.DATA)
                        ToastHelper.showMessage("ActivityResult:data:$data")
                    }
                    .compose<ActivityResult>(RxVMLifecycle.bindViewModel<ActivityResult>(this@MainVModel))
                    .subscribe(Functions.emptyConsumer<ActivityResult>(), RxActions.printThrowable())
        }))
    }


    /**
     * HFSRecyclerView Demo
     */
    private fun initHFSRecyclerViewDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_refresh_load_more),
                        RxActions.startActivity(viewInterface.activity,
                                HFSRDemoActivity.intentFor(context))))
    }


    /**
     * WebViewModel Demo
     */
    private fun initWebViewModelDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_webView),
                        RxActions.startActivity(viewInterface.activity,
                                WebViewDemoActivity.intentFor(context))))
    }


    /**
     * ViewPager 简单画廊效果 Demo
     */
    private fun initViewPagerGalleryDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_gallery),
                        RxActions.startActivity(viewInterface.activity,
                                ViewPagerDemoActivity.intentFor(context))))
    }

    /**
     * TabLayout Demo
     */
    private fun initTabLayoutDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_tab_layout),
                        RxActions.startActivity(viewInterface.activity,
                                TabHorizontalLayoutDemoActivity.intentFor(context))))
    }


    /**
     * RxJava DEMO
     */
    private fun initRxJavaDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_rx_java),
                        RxActions.startActivity(viewInterface.activity,
                                RxDemoActivity.intentFor(context))))
    }


    /**
     * 选择管理器Demo
     */
    private fun initSelectManagerDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_select_manager),
                        RxActions.startActivity(viewInterface.activity,
                                SelectManagerDemoActivity.intentFor(context))))
    }


    /**
     * 获取权限
     */
    private fun initPermissionDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_permission),
                        onReqPermissionAction()))
    }


    /**
     * 打开相机Demo
     */
    private fun initOpenCameraDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_camera), Action {
                    viewInterface.activity.requestCameraPermissions {
                        if (it) {
                            onRxPickerImgFile(RxImagePickerMode.PHOTO_TAKE)
                        } else {
                            ToastHelper.showMessage(R.string.str_home_have_no_legal_power)
                        }
                    }
                }))
    }

    /**
     * 打开相册Demo
     */
    private fun initOpenGalleryDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_photo), Action {
                    viewInterface.activity.requestStoragePermissions {
                        if (it) {
                            onRxPickerImgFile(RxImagePickerMode.PHOTO_PICK)
                        } else {
                            ToastHelper.showMessage(R.string.str_home_have_no_legal_power)
                        }
                    }
                }))
    }


    /**
     * 快速索引
     */
    private fun initSideBarDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_sidebar),
                        RxActions.startActivity(viewInterface.activity,
                                SideBarActivity.intentFor(context))))
    }

    /**
     * 页面数据懒加载
     */
    private fun initLazyLoadDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_lazy_load),
                        RxActions.startActivity(viewInterface.activity,
                                LazyLoadDemoActivity.intentFor(context))))
    }

    /**
     * LoadingHelper 工具类
     */
    private fun initLoadingHelperDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_loading_helper),
                        RxActions.startActivity(viewInterface.activity,
                                LoadingHelperDemoActivity.intentFor(context))))
    }

    /**
     * TextView加载Html
     */
    private fun initHtmlTextViewDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_text_load_html),
                        RxActions.startActivity(viewInterface.activity,
                                HtmlTextViewActivity.intentFor(context))))
    }


    /**
     * 选择图片
     *
     * @param mode CAMERA/GALLERY -> 相机/相册
     */
    private fun onRxPickerImgFile(mode: RxImagePickerMode) {
        RxImagePicker
                .get()
                .pickerMode(mode)
                .setSaveImagePath(Config.imagePath.absolutePath)
                .start(context)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { file ->
                    val modeStr = if (mode == RxImagePickerMode.PHOTO_TAKE) "CAMERA" else "GALLERY"
                    ToastHelper.showMessage(file.absolutePath)
                    Logger.e("RxImagePicker " + modeStr + " " + file.absolutePath)
                }
                .compose(RxVMLifecycle.bindViewModel(this@MainVModel))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(javaClass.simpleName + ""))
                .addTo(lifecycleComposite)
    }


    /**
     * 请求权限
     *
     * @return action
     */
    private fun onReqPermissionAction(): Action {
        return Action {
            viewInterface.activity.requestCameraPermissions {
                if (it) {
                    ToastHelper.showMessage(R.string.str_home_get_permissions_success)
                } else {
                    ToastHelper.showMessage(R.string.str_home_access_failed)
                }
            }
        }
    }

    /**
     * 裁剪 Demo
     */
    private fun initCropDemo() {
        var cropFunc = {
            RxImagePicker
                    .get()
                    .pickerMode(RxImagePickerMode.PHOTO_PICK)
                    .setSaveImagePath(Config.imagePath.absolutePath)
                    .start(context)
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap {
                        var intent = Intent(context, CropActivity::class.java)
                        intent.putExtra(Constants.DATA, it.absolutePath)
                        RxActivityResult.startIntent(viewInterface.activity, intent)
                    }
                    .compose(RxVMLifecycle.bindViewModel(this@MainVModel))
                    .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(javaClass.simpleName + ""))
                    .addTo(lifecycleComposite)
        }
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_crop_photo), Action {
                    viewInterface.activity.requestStoragePermissions {
                        if (it) {
                            cropFunc.invoke()
                        } else {
                            ToastHelper.showMessage(R.string.str_home_have_no_legal_power)
                        }
                    }
                }))
    }

    /**
     * 键盘状态监听
     */
    private fun initKeyBoardWatchDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_keyboard_state), Action {
                    KeyboardStateDemoActivity.startActivity(context)
                })
        )
    }

    /**
     * 保存页面到本地
     */
    private fun initSaveViewDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_save_view_2_photo), Action {
                    viewInterface.activity.requestStoragePermissions {
                        if (it) {
                            viewInterface.activity.startActivity(Intent(context, SaveViewActivity::class.java))
                        } else {
                            ToastHelper.showMessage(R.string.str_home_have_no_legal_power)
                        }
                    }
                })
        )

    }

    /**
     * 推送
     */
    private fun initPushDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_push), Action {
                    viewInterface.activity.startActivity(Intent(context, PushDemoActivity::class.java))
                })
        )
    }


    /**
     * Switch Button Demo
     */
    private fun initSwitchButtonDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_switch_btn), Action {
                    SwitchButtonActivity.startActivity(context)
                })
        )
    }

    /**
     * Page State Switch Demo
     */
    private fun initPageStateSwitchDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_page_state_helper), Action {
                    PageStateDemoActivity.startActivity(context)
                })
        )
    }

    /**
     * Page State Switch Demo
     */
    private fun initAppUpdateDemo() {
        demoViewModel.add(
                ItemSampleVModel.newItemViewModel(getString(R.string.str_home_app_update), Action {
                    AppUpdateActivity.start(context)
                })
        )
    }

}
