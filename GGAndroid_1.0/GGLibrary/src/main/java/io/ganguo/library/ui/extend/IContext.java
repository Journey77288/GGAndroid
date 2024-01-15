package io.ganguo.library.ui.extend;

import io.ganguo.library.BaseContext;

/**
 * AppContext 接口
 * <p/>
 * Created by tony on 9/28/14.
 */
public interface IContext {

    /**
     * 获取上下文环境
     *
     * @return
     */
    public <T extends BaseContext> T getAppContext();

}
