package io.ganguo.incubator.ui.fragment;

import android.text.Html;

import io.ganguo.incubator.R;
import io.ganguo.library.ui.extend.BaseFragment;
import ui.XTextView;

/**
 * Created by Tony on 4/8/15.
 */
public class XTextViewFragment extends BaseFragment {
    private XTextView textView;

    /**
     * 加载layout xml
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_xtext_view;
    }

    /**
     * 加载UI
     */
    @Override
    protected void initView() {
        textView = (XTextView) getView().findViewById(R.id.xtext_view);
    }

    /**
     * 监听控件
     */
    @Override
    protected void initListener() {

    }

    /**
     * 加载网络数据
     */
    @Override
    protected void initData() {

    }
}
