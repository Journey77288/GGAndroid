package io.ganguo.demo.viewmodel.activity

import android.app.Activity
import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.bean.Constants
import io.ganguo.demo.databinding.ActivityResultBinding
import io.ganguo.viewmodel.core.BaseViewModel
import io.ganguo.viewmodel.core.viewinterface.ActivityInterface
import io.ganguo.utils.util.Randoms

/**
 * Activity ResultVModel
 * Created by Roger on 6/7/16.
 */
class ResultVModel : BaseViewModel<ActivityInterface<ActivityResultBinding>>() {
    override val layoutId: Int by lazy { R.layout.activity_result }
    var randomLetter: String = Randoms.getRandomCapitalLetters(20)


    override fun onViewAttached(view: View) {}


    /**
     * Random Click
     *
     * @return
     */
    fun actionRandomClick(): View.OnClickListener {
        return View.OnClickListener {
            val intent = viewInterface.activity.intent
            intent.putExtra(Constants.DATA, randomLetter)
            viewInterface.activity.setResult(Activity.RESULT_OK, intent)
            viewInterface.activity.finish()
        }
    }


}
