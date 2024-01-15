package io.ganguo.demo.module.impl;

import android.support.annotation.NonNull;

import io.ganguo.demo.entity.UserEntity;
import io.ganguo.demo.entity.UploadToken;
import io.ganguo.demo.module.QiNiuModule;
import io.ganguo.http.base.BaseApiImpl;
import io.ganguo.http.base.BaseInternal;
import io.ganguo.http.entity.response.HttpResponse;
import io.ganguo.http.handler.ApiCommonResponseHandler;
import io.ganguo.rx.RxStatement;
import io.ganguo.utils.util.Strings;
import io.reactivex.Observable;

/**
 * 七牛网络请求实现
 * Created by Roger on 7/28/16.
 */
public class APIQiNiuImpl extends BaseApiImpl<APIQiNiuImpl.InternalModule> {
    private static String apiToken = "";

    /**
     * function: 单例
     * <p>
     * 懒加载方式实现 - 推荐
     * <p/>
     *
     * @return
     */
    @NonNull
    public static APIQiNiuImpl get() {
        return InternalModule.INSTANCE;
    }

    @Override
    public InternalModule createApiModule() {
        return new InternalModule();
    }


    /**
     * function: 获取七牛上传Token
     *
     * @return
     */
    public Observable<String> requestQiNiuToken() {
        return Observable
                .just(apiToken)
                .compose(RxStatement.ifThen(
                        apiToken -> Strings.isEmpty(apiToken),
                        apiToken -> {
                            return login();
                        }))
                .flatMap(apiToken -> getApiModule().getToken("Bearer " + apiToken)
                        .compose(new ApiCommonResponseHandler<>()))
                .map(uploadToken -> uploadToken.getToken());
    }

    /**
     * function: 登录七牛，用户获取上传图片Token
     *
     * @return
     */
    public Observable<String> login() {
        return getApiModule()
                .login("15627818675", "123456")
                .compose(new ApiCommonResponseHandler<>())
                .map(user -> {
                    apiToken = user.getApiToken();
                    return apiToken;
                });
    }


    static class InternalModule extends BaseInternal<QiNiuModule> implements QiNiuModule {
        //BASE_URL - 项目中涉及到多BaseUrl，才需要在这里配置，否则在API.init()函数中配置即可
        static final String QI_NIU_BASE_URL = "http://121.201.28.159/";
        static APIQiNiuImpl INSTANCE = new APIQiNiuImpl();

        @Override
        public Observable<HttpResponse<UserEntity>> login(String phone, String password) {
            return getModule().login(phone, password);
        }

        @Override
        public Observable<HttpResponse<UploadToken>> getToken(String header) {
            return getModule().getToken(header);
        }

        /**
         * function：get BaseUrl
         * 涉及到多BaseUrl的项目时，需要重写getBaseUrl()，并返回对应的BaseUrl
         *
         * @return
         */
        @Override
        protected String getBaseUrl() {
            return QI_NIU_BASE_URL;
        }

        /**
         * function：返回创建Module的Class类型
         *
         * @return
         */
        @Override
        protected Class<QiNiuModule> getModuleClass() {
            return QiNiuModule.class;
        }
    }

}
