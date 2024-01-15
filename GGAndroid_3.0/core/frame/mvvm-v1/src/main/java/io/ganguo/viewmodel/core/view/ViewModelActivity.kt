package io.ganguo.viewmodel.core.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import io.ganguo.core.ui.activity.BaseActivity
import io.ganguo.rx.ActivityResult
import io.ganguo.rx.ActivityResultOwner
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.BaseViewModel.OnViewAttachListener
import io.ganguo.viewmodel.core.ViewModelHelper
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/29
 *     desc   : Base ViewModelActivity
 * </pre>
 * @param T : ViewDataBinding
 * @param B : BaseViewModel<*>
 * @property binding T
 * @property context Context
 * @property activity FragmentActivity
 * @property bundle Bundle?
 * @property viewModel B
 * @property resultEmitter Subject<ActivityResult>?
 */
abstract class ViewModelActivity<T : ViewDataBinding, B : BaseViewModel<*>> :
        BaseActivity(), ActivityInterface<T>, OnViewAttachListener<B>, ActivityResultOwner {
    private var resultEmitter: Subject<ActivityResult>? = null
    override val binding: T by lazy {
        DataBindingUtil.setContentView(this, viewModel.layoutId) as T
    }
    override val context: Context by lazy {
        this
    }
    override val activity: FragmentActivity by lazy {
        this
    }
    val viewModel: B by lazy {
        createViewModel()
    }
    override val bundle: Bundle? by lazy {
        if (intent == null) {
            null
        } else {
            intent.extras
        }
    }

    init {
        bindLifecycle(viewModel.lifecycleHelper)
    }

    override fun beforeInitView() {
    }

    override fun initView() {}
    override fun initListener() {
        viewModel.onViewAttachListener = this as OnViewAttachListener<BaseViewModel<*>>
    }

    override fun initData() {
        ViewModelHelper.bind(this, viewModel)
    }

    override fun getActivityResult(): Subject<ActivityResult> {
        resultEmitter = PublishSubject.create<ActivityResult>().toSerialized()
        resultEmitter!!.hide()
        return resultEmitter!!
    }


    /**
     * Forward activity Result to viewModel
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultEmitter != null) {
            resultEmitter!!.onNext(ActivityResult(requestCode, resultCode, data))
            resultEmitter!!.onComplete()
        }
    }

    /**
     * Create ViewModel here
     * if you want to getViewModel instance use [.getViewModel] instead this
     */
    abstract fun createViewModel(): B

    override fun onDestroy() {
        super.onDestroy()
        resultEmitter = null
    }
}