package io.ganguo.demo.viewmodel.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.bean.Constants;
import io.ganguo.demo.databinding.ActivityResultBinding;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.utils.util.Randoms;

/**
 * Activity ResultVModel
 * Created by Roger on 6/7/16.
 */
public class ResultVModel extends BaseViewModel<ActivityInterface<ActivityResultBinding>> {

    public String randomLetter = Randoms.getRandomCapitalLetters(20);

    @Override
    public void onViewAttached(View view) {
    }


    /**
     * function:create Random Click
     *
     * @return
     */
    public View.OnClickListener onCrateRandomClick() {
        return v -> {
            Intent intent = getView().getActivity().getIntent();
            intent.putExtra(Constants.DATA, randomLetter);
            getView().getActivity().setResult(Activity.RESULT_OK, intent);
            getView().getActivity().finish();
        };
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_result;
    }

}
