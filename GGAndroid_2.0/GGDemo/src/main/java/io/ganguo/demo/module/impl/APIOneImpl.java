package io.ganguo.demo.module.impl;


import android.support.annotation.NonNull;

import io.ganguo.demo.BuildConfig;
import io.ganguo.demo.entity.one.OneListEntity;
import io.ganguo.demo.http.one.OnesHttpResponse;
import io.ganguo.demo.http.one.OnesResponseHandler;
import io.ganguo.demo.module.OneModule;
import io.ganguo.http.base.BaseApiImpl;
import io.ganguo.http.base.BaseInternal;
import io.reactivex.Observable;

/**
 * <p>
 * 《一个》 - 相关API请求
 * </p>
 * Created by leo on 2018/7/30.
 */
public class APIOneImpl extends BaseApiImpl<APIOneImpl.InternalModule> {

    /**
     * function: 单例
     * <p>
     * 懒加载方式实现 - 推荐
     * <p/>
     *
     * @return
     */
    @NonNull
    public static APIOneImpl get() {
        return InternalModule.INSTANCE;
    }

    /**
     * function: 获取OneList接口数据
     *
     * @return
     */
    public Observable<OneListEntity> getOneList(String id) {
        return getApiModule()
                .getOneList(id)
                .compose(new OnesResponseHandler<>());
    }


    @Override
    protected InternalModule createApiModule() {
        return new InternalModule();
    }

    static class InternalModule extends BaseInternal<OneModule> implements OneModule {
        static APIOneImpl INSTANCE = new APIOneImpl();

        @Override
        public Observable<OnesHttpResponse<OneListEntity>> getOneList(String id) {
            return getModule().getOneList(id);
        }

        @Override
        protected Class<OneModule> getModuleClass() {
            return OneModule.class;
        }

        //BASE_URL - 项目中涉及到多BaseUrl，才需要在这里配置，否则在API.init()函数中配置即可
        @Override
        protected String getBaseUrl() {
            return BuildConfig.ONE_BASE_URL;
        }
    }
}
