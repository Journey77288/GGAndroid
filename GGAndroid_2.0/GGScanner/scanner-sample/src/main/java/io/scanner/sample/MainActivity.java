package io.scanner.sample;

import io.ganguo.utils.util.log.Logger;
import io.ganguo.vmodel.ViewModelActivity;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.scanner.sample.viewmodel.MainViewModel;

/**
 * <p>
 * 扫码Demo首页
 * </p>
 * Created by leo on 2018/8/9.
 */
public class MainActivity extends ViewModelActivity<IncludeHfSwipeRecyclerBinding, MainViewModel> {
    @Override
    public MainViewModel createViewModel() {
        return new MainViewModel();
    }

    @Override
    public void onViewAttached(MainViewModel viewModel) {
        Logger.init(getContext().getApplicationContext());
    }


}
