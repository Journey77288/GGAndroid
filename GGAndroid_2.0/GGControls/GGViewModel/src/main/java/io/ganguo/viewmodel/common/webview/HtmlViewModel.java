package io.ganguo.viewmodel.common.webview;

import android.app.Application;
import android.view.View;

import io.ganguo.library.ui.view.ViewInterface;
import io.ganguo.utils.util.Strings;
import io.ganguo.viewmodel.common.base.BaseWebViewModel;
import io.ganguo.viewmodel.databinding.IncludeWebviewBinding;

/**
 * 加载Html - WebViewModel
 * Created by leo on 2018/11/12.
 */
public abstract class HtmlViewModel extends BaseWebViewModel<ViewInterface<IncludeWebviewBinding>> {
    private String html;

    public HtmlViewModel(Application application, String html) {
        super(application);
        this.html = html;
    }


    @Override
    public void onAttach() {
        super.onAttach();
        loadHtmlContent();
    }


    @Override
    public void onViewAttached(View view) {

    }

    /**
     * 加载Html内容
     */
    protected void loadHtmlContent() {
        if (Strings.isNotEmpty(html)) {
            getWebView().loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }
}
