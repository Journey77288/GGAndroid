package io.ganguo.incubator.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

import io.ganguo.incubator.R;
import io.ganguo.incubator.ui.fragment.FrescoFragment;
import io.ganguo.incubator.ui.fragment.SwiperFragment;
import io.ganguo.incubator.ui.fragment.ViewPagerFragment;
import io.ganguo.library.ui.extend.BaseFragmentActivity;
import io.ganguo.library.util.AndroidUtils;
import io.ganguo.library.util.ExitUtil;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * 主界面 - 实现XXX功能
 * <p/>
 * Created by Tony on 3/4/15.
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private SlidingPaneLayout drawer_layout;
    private View action_swipe_refresh;
    private View action_fresco;
    private View action_view_pager;
    private View btn_titleRight;

    @Override
    protected void beforeInitView() {
        setContentView(R.layout.activity_main);
        logger.d("main started!");
        // 设置状态栏颜色
        AndroidUtils.setStatusColor(this, getResources().getColor(R.color.bg_title));
    }

    @Override
    protected void initView() {
        drawer_layout = (SlidingPaneLayout) findViewById(R.id.drawer_layout);
        action_swipe_refresh = findViewById(R.id.action_swipe_refresh);
        action_fresco = findViewById(R.id.action_fresco);
        action_view_pager = findViewById(R.id.action_view_pager);
        btn_titleRight = findViewById(R.id.btn_titleRight);
    }

    @Override
    protected void initListener() {
        btn_titleRight.setOnClickListener(this);
        action_swipe_refresh.setOnClickListener(this);
        action_fresco.setOnClickListener(this);
        action_view_pager.setOnClickListener(this);
        drawer_layout.setSliderFadeColor(Color.TRANSPARENT);
        drawer_layout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
    }

    @Override
    protected void initData() {
        showFragment(R.id.fly_content, SwiperFragment.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_swipe_refresh:
                showFragment(R.id.fly_content, SwiperFragment.class);
                break;
            case R.id.action_fresco:
                showFragment(R.id.fly_content, FrescoFragment.class);
                break;
            case R.id.action_view_pager:
                showFragment(R.id.fly_content, ViewPagerFragment.class);
                break;
            case R.id.btn_titleRight:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        drawer_layout.closePane();
    }

    @Override
    public void onBackPressed() {
        ExitUtil.exitByDoublePressed(getAppContext());
    }

}
