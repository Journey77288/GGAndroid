package io.ganguo.library.ui.extend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.ganguo.library.BaseContext;
import io.ganguo.library.R;
import io.ganguo.library.core.event.BusProvider;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * 分片 - 基类
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public abstract class BaseFragment extends Fragment implements IContext {

    private final Logger logger = LoggerFactory.getLogger(BaseFragment.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.i("onCreateView");
        return inflater.inflate(getLayoutResourceId(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.i("onActivityCreated");
        if (getView() != null) {
            // 返回通过Action
            View actionBack = getView().findViewById(R.id.action_back);
            if (actionBack != null) {
                actionBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            }
        }

        BusProvider.getInstance().register(this);

        initView();
        initListener();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        BusProvider.getInstance().unregister(this);
    }

    /**
     * 获取上下文环境
     *
     * @return
     */
    @Override
    public <T extends BaseContext> T getAppContext() {
        return BaseContext.getInstance();
    }

    /**
     * 加载layout xml
     */
    protected abstract int getLayoutResourceId();

    /**
     * 加载UI
     */
    protected abstract void initView();

    /**
     * 监听控件
     */
    protected abstract void initListener();

    /**
     * 加载网络数据
     */
    protected abstract void initData();

}
