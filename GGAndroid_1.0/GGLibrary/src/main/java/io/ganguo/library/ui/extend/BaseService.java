package io.ganguo.library.ui.extend;

import android.app.Service;

import io.ganguo.library.BaseContext;
import io.ganguo.library.core.event.BusProvider;

/**
 * 后台服务 - 基类
 * <p/>
 * Created by zhihui_chen on 14-9-9.
 */
public abstract class BaseService extends Service implements IContext {


    @Override
    public void onCreate() {
        super.onCreate();

        BusProvider.getInstance().register(this);
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
}
