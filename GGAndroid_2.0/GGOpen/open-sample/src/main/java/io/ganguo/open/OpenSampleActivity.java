package io.ganguo.open;

import android.content.Intent;

import io.ganguo.facebook.FaceBookManager;
import io.ganguo.line.LineManager;
import io.ganguo.open.viewmodel.OpenSampleViewModel;

import io.ganguo.qq.QQManager;
import io.ganguo.sina.SinaManager;
import io.ganguo.twitter.TwitterManager;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.vmodel.ViewModelActivity;

/**
 * <p>
 * GanGuo Open Sdk Demo
 * </p>
 * Created by leo on 2018/8/6.
 */
public class OpenSampleActivity extends ViewModelActivity<IncludeHfRecyclerBinding, OpenSampleViewModel> {

    @Override
    public OpenSampleViewModel createViewModel() {
        return new OpenSampleViewModel();
    }

    @Override
    public void onViewAttached(OpenSampleViewModel viewModel) {
    }

    /**
     * QQ 分享、登录以及微博登录需要重写 onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TwitterManager.registerActivityResultListener(requestCode, resultCode, data);
        FaceBookManager.registerActivityResultListener(requestCode, resultCode, data);
        SinaManager.registerActivityResultListener(requestCode, resultCode, data);
        QQManager.registerActivityResultListener(requestCode, resultCode, data);
        LineManager.registerActivityResultListener(requestCode, resultCode, data);
        Logger.e("onActivityResult:resultCode:" + resultCode);
        Logger.e("onActivityResult:requestCode:" + requestCode);
    }

    /**
     * 微博分享回调需要重写 onNewIntent
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SinaManager.registerShareActivityNewIntent(intent);
    }
}
