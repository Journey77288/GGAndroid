package io.ganguo.incubator.view.general.activity

import android.content.Intent
import androidx.databinding.ViewDataBinding
import io.ganguo.log.core.Logger
import io.ganguo.utils.util.Numbers
import io.ganguo.viewmodel.pack.base.activity.BaseVModelActivity
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/09/27
 *     desc   : Schema 跳转: schema link 可参考 "ganguo://rider" 或者 `ganguo://rider?type=xxx&id=xxx'
 *              1、schema "ganguo" 可更改，取与项目相关的名字即可，调整同步到 AndroidManifest 对应的 SchemeActivity schema 参数
 *              2、rider 仅作为描述可更更改
 *              3、type & id 为参数，根据场景与 H5 协定好即可。
 *              4、测试方式: adb shell am start  -W -a "android.intent.action.VIEW" -d 'ganguo://rider?type=121\&id=3434' xxx.xxx.xxx（包名，自行修改，\为转义字符，adb调试时必须要(实际使用不需要)，否则导致转义失败，只能拿到第一个参数）
 * </pre>
 */
class SchemeActivity : BaseVModelActivity<ViewDataBinding, BaseViewModel<*>>() {

    override fun createViewModel(): BaseViewModel<*>? {
        return null
    }

    override fun onViewAttached(viewModel: BaseViewModel<Any>) {
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    /**
     * 可根据 uri 的参数用于跳转到对应的页面，不带参数则跳转主页或者启动页；
     * 如 `ganguo://rider?type=xxx&id=xxx'  type 以及 id
     */
    override fun beforeInitView() {
        val uri = intent.data
        if (uri != null) {
            val type = uri.getQueryParameter("type")
            val id = Numbers.toInt(uri.getQueryParameter("id"))
            Logger.e("SchemeActivity $uri $type $id")
            startActivity(Intent(this, ApkInfoActivity::class.java))
            finish()
        }
    }
}