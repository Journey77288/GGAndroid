package io.ganguo.incubator.ui.activity;

import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import io.ganguo.incubator.R;
import io.ganguo.library.ui.adapter.LoopPageAdapter;
import io.ganguo.library.ui.extend.BaseFragmentActivity;
import io.ganguo.library.util.AndroidUtils;
import ui.AutoScrollViewPager;

/**
 * 登录界面 - 实现用户登录功能
 * <p/>
 * Created by Tony on 3/4/15.
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener {

    private AutoScrollViewPager as_view_pager;
    private LoopPageAdapter mLoopPageAdapter = new LoopPageAdapter();

    @Override
    protected void beforeInitView() {
        setContentView(R.layout.fragment_view_pager);
        AndroidUtils.setStatusTranslucent(this);
    }

    @Override
    protected void initView() {
        as_view_pager = (AutoScrollViewPager) findViewById(R.id.as_view_pager);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 4; i++) {
            SimpleDraweeView view = new SimpleDraweeView(this);
            String url = "http://ganguo.io/images/case_0" + (i + 1) + ".png";
            view.setImageURI(Uri.parse(url));
            mLoopPageAdapter.add(view);
        }
        as_view_pager.setAdapter(mLoopPageAdapter);
        as_view_pager.setCurrentItem(mLoopPageAdapter.getStartPosition(), false);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onResume() {
        super.onResume();

        if (as_view_pager != null) {
            as_view_pager.startAutoScroll();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (as_view_pager != null) {
            as_view_pager.stopAutoScroll();
        }
    }

    /**
     * 显示页面loading
     */
    private void showPageLoading() {
        if (findViewById(R.id.view_loading) == null) return;
        findViewById(R.id.view_loading).setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏页面loading
     */
    private void hidePageLoading() {
        if (findViewById(R.id.view_loading) == null) return;
        findViewById(R.id.view_loading).setVisibility(View.GONE);
    }
}
