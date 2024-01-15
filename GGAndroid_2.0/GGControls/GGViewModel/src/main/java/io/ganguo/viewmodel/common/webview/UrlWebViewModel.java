package io.ganguo.viewmodel.common.webview;

import android.app.Application;
import android.view.View;

import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.utils.util.Strings;
import io.ganguo.viewmodel.common.base.BaseWebViewModel;
import io.ganguo.viewmodel.databinding.IncludeWebviewBinding;

/**
 * 加载Url - WebViewModel
 * <p>
 * 如果是多场景的情况，应该是继承它去实现自己的处理逻辑，而不是在这里面增加新的代码逻辑
 * <p/>
 * Created by leo on 2018/11/12.
 */
public class UrlWebViewModel extends BaseWebViewModel<ViewInterface<IncludeWebviewBinding>> {
    private String url;

    public UrlWebViewModel(Application application, String url) {
        super(application);
        this.url = url;
    }

    @Override
    public void onViewAttached(View view) {
        loadUrlContent();
    }

    /**
     * 加载Url
     */
    protected void loadUrlContent() {
        if (Strings.isNotEmpty(url)) {
            getWebView().loadUrl(url);
        }
    }


}
