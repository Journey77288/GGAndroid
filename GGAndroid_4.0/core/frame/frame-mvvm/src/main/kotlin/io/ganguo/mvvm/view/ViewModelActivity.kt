@file:Suppress("KDocUnresolvedReference")

package io.ganguo.mvvm.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import io.ganguo.app.ui.activity.BaseActivity
import io.ganguo.mvvm.viewinterface.ActivityInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.mvvm.viewmodel.ViewModel.OnViewAttachListener
import io.ganguo.mvvm.viewmodel.ViewModelHelper
import io.ganguo.rxresult.ActivityResult
import io.ganguo.rxresult.ActivityResultOwner
import io.ganguo.rxresult.GGActivityResultLauncher
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

/**
 * <pre>
 *     @author : leo
 *     time   : 2020/05/29
 *     desc   : Base ViewModelActivity
 * </pre>
 * @param T : ViewDataBinding
 * @param B : ViewModel<*>
 * @property binding T
 * @property viewContext Context
 * @property activity FragmentActivity
 * @property bundle Bundle?
 * @property viewModel B
 */
@Suppress("UNCHECKED_CAST", "LeakingThis", "KDocUnresolvedReference", "KDocUnresolvedReference")
abstract class ViewModelActivity<T : ViewDataBinding, B : ViewModel<*>> :
    BaseActivity(), ActivityInterface<T>, OnViewAttachListener<B>, ActivityResultOwner {

    override val activityResult: Subject<ActivityResult> by lazy {
        PublishSubject.create<ActivityResult>().toSerialized()
    }

    override val bundle: Bundle? by lazy {
        if (intent == null) {
            null
        } else {
            intent.extras
        }
    }
    override val binding: T by lazy {
        DataBindingUtil.setContentView(this, viewModel.layoutId) as T
    }
    override val viewContext: Context by lazy {
        this
    }
    override val activity: FragmentActivity by lazy {
        this
    }
    override val resultLauncher: GGActivityResultLauncher<Intent, androidx.activity.result.ActivityResult> =
        GGActivityResultLauncher(activity as ComponentActivity, ActivityResultContracts.StartActivityForResult())
    protected open val viewModel: B by lazy {
        createViewModel()
    }

    override fun initView() {
    }

    override fun beforeInitView() {
    }

    override fun initListener() {
        viewModel.onViewAttachListener = this as OnViewAttachListener<ViewModel<*>>
    }

    override fun initData() {
        viewModel.bindLifecycle(this)
        ViewModelHelper.bind(this as ActivityInterface<*>, viewModel)
    }

    /**
     * Forward activity Result to viewModel
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activityResult.onNext(ActivityResult(requestCode, resultCode, data))
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Create ViewModel here
     * if you want to getViewModel instance use [.getViewModel] instead this
     */
    abstract fun createViewModel(): B


    /**
     * Release resources
     * Release [activityResult]
     */
    override fun onDestroy() {
        activityResult.onComplete()
        super.onDestroy()
    }
}
