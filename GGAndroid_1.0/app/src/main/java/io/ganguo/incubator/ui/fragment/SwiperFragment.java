package io.ganguo.incubator.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import io.ganguo.incubator.R;
import io.ganguo.library.common.UIHelper;
import io.ganguo.library.ui.adapter.CreatorAdapter;
import io.ganguo.library.ui.adapter.IViewCreator;
import io.ganguo.library.ui.adapter.ViewHolder;
import io.ganguo.library.ui.extend.BaseFragment;
import io.ganguo.library.util.StringUtils;
import ui.SwipeRefreshView;

/**
 * 上拉下拉实现
 * <p/>
 * Created by Tony on 4/3/15.
 */
public class SwiperFragment extends BaseFragment implements SwipeRefreshView.OnRefreshListener {

    private SwipeRefreshView swipe_container;
    private ListView lv_demo;
    private CreatorAdapter mAdapter;

    /**
     * 加载layout xml
     */
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_swipe_refresh;
    }

    /**
     * 加载UI
     */
    @Override
    protected void initView() {
        lv_demo = (ListView) getView().findViewById(R.id.lv_demo);
        swipe_container = (SwipeRefreshView) getView().findViewById(R.id.swipe_container);
        swipe_container.setColorSchemeColors(Color.parseColor("#009688"), Color.parseColor("#4CAF50"), Color.parseColor("#00BCD4"));
    }

    /**
     * 监听控件
     */
    @Override
    protected void initListener() {
        swipe_container.setOnRefreshListener(this);
        lv_demo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.toastMessageMiddle(getAppContext(), "Hello World " + position);
            }
        });
    }

    /**
     * 加载网络数据
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void initData() {
        mAdapter = new CreatorAdapter(getActivity(), new IViewCreator<String>() {

            @Override
            public ViewHolder createView(Context context, int position, String item) {
                return new ViewHolder(View.inflate(context, R.layout.item_demo, null));
            }

            @Override
            public void updateView(ViewHolder viewHolder, int position, final String item) {
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
                tv_name.setText(item);
            }

        });
        mAdapter.setList(StringUtils.testToList(10));
        lv_demo.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        swipe_container.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                swipe_container.onRefreshComplete();
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        swipe_container.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.addAll(StringUtils.testToList(5));
                mAdapter.notifyDataSetChanged();
                swipe_container.onRefreshComplete();
            }
        }, 3000);
    }
}
