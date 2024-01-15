package io.ganguo.demo.viewmodel.activity

import android.view.Gravity
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import io.ganguo.demo.R
import io.ganguo.demo.databinding.ActivityPushBinding
import io.ganguo.ggcache.sp.SharedPreferences
import io.ganguo.push.helper.NotificationHelper
import io.ganguo.push.manager.PushManager
import io.ganguo.rx.RxActions
import io.ganguo.rx.utils.safeDispose
import io.ganguo.utils.helper.ToastHelper
import io.ganguo.utils.util.Strings
import io.ganguo.viewmodel.pack.base.viewmodel.BaseActivityVModel
import io.ganguo.viewmodel.pack.common.RecyclerViewModel
import io.ganguo.viewmodel.pack.common.TextViewModel
import io.ganguo.viewmodel.pack.helper.LoadingDialogHelper
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/10/10
 *     desc   : Push Demo
 * </pre>
 */

class PushDemoViewModel : BaseActivityVModel<ActivityPushBinding>() {
    override val layoutId: Int by lazy {
        R.layout.activity_push
    }
    var alias = ObservableField<String>("")
    private val KEY_ALIAS = "key_alias"
    private lateinit var recyclerViewModel: RecyclerViewModel<IncludeHfRecyclerBinding, ViewInterface<IncludeHfRecyclerBinding>>
    private var registerDisposable: Disposable? = null
    private var unregisterDisposable: Disposable? = null
    private var pushDisposable: Disposable? = null
    var pushCount = ObservableInt(0)
    var hasAlias = ObservableBoolean(false)
    override fun onViewAttached(viewInterface: View) {
        initRecyclerViewModel()
        initAlias()
    }

    /**
     * function:初始化别名
     */
    private fun initAlias() {
        var value = getAlias()
        alias.set(value)
        registerAlias(value)
    }

    /**
     * function:初始化 RecyclerViewModel
     */
    private fun initRecyclerViewModel() {
        recyclerViewModel = RecyclerViewModel.linerLayout(context, LinearLayoutManager.VERTICAL)
        ViewModelHelper.bind(viewInterface.binding.flyContent, this, recyclerViewModel)
    }

    /**
     * function:更新推送消息
     * @param value String
     */
    private fun updateMessage(value: String) {
        var viewModel = TextViewModel.Builder()
                .content("content:$value")
                .gravity(Gravity.CENTER_VERTICAL)
                .paddingLeftRes(R.dimen.dp_20)
                .paddingTopRes(R.dimen.dp_10)
                .paddingBottomRes(R.dimen.dp_10)
                .build()
        var index = recyclerViewModel.adapter.itemCount
        recyclerViewModel.adapter.add(viewModel)
        recyclerViewModel.adapter.notifyItemInserted(index)

    }

    /**
     * function:切换别名状态
     */
    fun actionAlias() {
        var value = alias.get()
        if (Strings.isEmpty(value)) {
            initAlias()
        } else {
            deleteAlias()
        }
    }

    /**
     * function:获取别名;
     *          从缓存中获取别名，如果不存在则新建别名并缓存
     * @return String
     */
    private fun getAlias(): String {
        var value = SharedPreferences.getString(KEY_ALIAS)
        if (value.isNullOrEmpty()) {
            value = (Math.random() * 100).toInt().toString()
            SharedPreferences.putString(KEY_ALIAS, value)
        }
        return value
    }

    /**
     * function:注册别名
     * @param value String
     */
    private fun registerAlias(value: String) {
        safeDispose(registerDisposable)
        LoadingDialogHelper.showMaterLoading(context, R.string.str_push_setting_alias_ing)
        registerDisposable = PushManager.get().setAlias(value)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    LoadingDialogHelper.hideMaterLoading()
                }
                .subscribe(Consumer {
                    ToastHelper.showMessage(R.string.str_push_setting_alias_ing)
                    observablePush()
                    hasAlias.set(true)
                }, RxActions.printThrowable())
    }

    /**
     * function:删除别名
     */
    private fun deleteAlias() {
        safeDispose(unregisterDisposable)
        LoadingDialogHelper.showMaterLoading(context, R.string.str_push_delete_alias_ing)
        unregisterDisposable = PushManager.get().deleteAlias()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { LoadingDialogHelper.hideMaterLoading() }
                .subscribe(Consumer {
                    if (it) {
                        ToastHelper.showMessage(R.string.str_push_delete_alias_success)
                        safeDispose(pushDisposable)
                        hasAlias.set(false)
                        SharedPreferences.remove(KEY_ALIAS)
                        alias.set("")
                    }
                }, RxActions.printThrowable())
    }

    /**
     * function:接受推送消息
     */
    private fun observablePush() {
        safeDispose(pushDisposable)
        pushDisposable = PushManager.get().observePushEvent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    pushCount.set(pushCount.get() + 1)
                    NotificationHelper.showNotification(context, entity = it)
                    updateMessage(Strings.nullToEmpty(it.title))
                }, RxActions.printThrowable())
    }

    override fun onDestroy() {
        safeDispose(pushDisposable)
        safeDispose(registerDisposable)
        safeDispose(unregisterDisposable)
        super.onDestroy()
    }

}