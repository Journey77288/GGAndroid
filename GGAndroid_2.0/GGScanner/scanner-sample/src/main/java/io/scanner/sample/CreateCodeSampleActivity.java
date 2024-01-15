package io.scanner.sample;

import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.scanner.sample.viewmodel.CreateCodeSampleVModel;

/**
 * <p>
 * 生成二维码&&条形码Demo
 * </p>
 * Created by leo on 2018/8/9.
 */
public class CreateCodeSampleActivity extends ViewModelActivity<IncludeHfRecyclerBinding, CreateCodeSampleVModel> {
    @Override
    public CreateCodeSampleVModel createViewModel() {
        return new CreateCodeSampleVModel();
    }

    @Override
    public void onViewAttached(CreateCodeSampleVModel viewModel) {

    }
}
