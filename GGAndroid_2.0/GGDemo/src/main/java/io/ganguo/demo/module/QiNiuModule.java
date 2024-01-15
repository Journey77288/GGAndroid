package io.ganguo.demo.module;

import io.ganguo.demo.entity.UserEntity;
import io.ganguo.demo.entity.UploadToken;
import io.ganguo.http.entity.response.HttpResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 七牛相关API-Module
 * Created by Roger on 7/28/16.
 */
public interface QiNiuModule {
    //interface 中变量实际会默认加上public static final，也就是默认为常量
    //登录
    String API_AUTH_LOGIN = "api/auth/login";
    //七牛认证
    String API_HELPER_QINIU = "api/helper/qiniu";

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(API_AUTH_LOGIN)
    Observable<HttpResponse<UserEntity>> login(@Field("phone") String phone,
                                               @Field("password") String password);

    /**
     * 获取七牛上传Token
     *
     * @return
     */
    @GET(API_HELPER_QINIU)
    Observable<HttpResponse<UploadToken>> getToken(@Header("Authorization") String header);
}
