package io.ganguo.http.base;

import io.ganguo.http.callback.InitHttpRes;
import io.ganguo.http.core.ApiManager;

/**
 * <p>
 * BaseInternal基类 - 用于后期扩展，可能出现其他通用的函数
 * <p>
 * M - Http对应Module
 * </p>
 * Created by leo on 2018/7/17.
 */
public abstract class BaseInternal<M> implements InitHttpRes<M> {
    protected M module;

    /**
     * function：get  Module
     *
     * @return
     */
    protected M getModule() {
        if (module == null) {
            module = createModule(getModuleClass());
        }
        return module;
    }

    @Override
    public M createModule(Class<M> mClass) {
        return ApiManager.create(mClass, getBaseUrl());
    }

    /**
     * function：BaseUrl
     * <p>
     * 1、适用于项目中有用到多BaseUrl的情况
     * 2、涉及到多BaseUrl的项目，需要在对应的API impl中的Internal类，重写该函数，并返回对应的Url即可。
     * 注：注意涉及到多BaseUrl的项目，都必须重写该函数，并在创建对应的API Module时，同时设置BaseUrl。可参考七牛上传图片Demo
     * <p/>
     *
     * @return
     */
    protected String getBaseUrl() {
        return null;
    }

    protected abstract Class<M> getModuleClass();
}
