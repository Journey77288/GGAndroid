package io.ganguo.incubator.ui.fragment;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import io.ganguo.incubator.R;
import io.ganguo.library.ui.adapter.LoopPageAdapter;
import io.ganguo.library.ui.extend.BaseFragment;
import ui.AutoScrollViewPager;

/**
 * Created by Tony on 4/7/15.
 */
public class ViewPagerFragment extends BaseFragment {

    private AutoScrollViewPager as_view_pager;
    private LoopPageAdapter mLoopPageAdapter = new LoopPageAdapter();

    /**
     * 加载layout xml
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_view_pager;
    }

    /**
     * 加载UI
     */
    @Override
    protected void initView() {
        as_view_pager = (AutoScrollViewPager) getView().findViewById(R.id.as_view_pager);
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
        for (int i = 0; i < 4; i++) {
            SimpleDraweeView view = new SimpleDraweeView(getActivity());
            String url = "http://ganguo.io/images/case_0" + (i + 1) + ".png";
            view.setImageURI(Uri.parse(url));
            mLoopPageAdapter.add(view);
        }
        as_view_pager.setAdapter(mLoopPageAdapter);
        as_view_pager.setCurrentItem(mLoopPageAdapter.getStartPosition(), false);
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
}
