package io.ganguo.open

import android.content.Intent
import io.ganguo.factory.GGFactory
import io.ganguo.open.viewmodel.OpenSampleViewModel
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding
import io.ganguo.viewmodel.core.view.ViewModelActivity


/**
 * <pre>
 * author : leo
 * time   : 2018/8/6
 * desc   : GanGuo Open Sdk Demo
</pre> *
 */

class OpenSampleActivity : ViewModelActivity<IncludeHfRecyclerBinding, OpenSampleViewModel>() {

    override fun createViewModel(): OpenSampleViewModel {
        return OpenSampleViewModel()
    }

    override fun onViewAttached(viewModel: BaseViewModel<Any>) {
    }

    /**
     * QQ 分享、登录以及微博登录需要重写 onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        GGFactory.registerActivityResult(requestCode, resultCode, data)
    }

}
