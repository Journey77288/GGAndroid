package io.ganguo.demo.http.api.service

import io.ganguo.demo.entity.UploadToken
import io.ganguo.demo.entity.UserEntity
import io.ganguo.http2.core.use.response.HttpResponse
import io.reactivex.Observable
import retrofit2.http.*

/**
 * 七牛相关API-Module
 * Created by Roger on 7/28/16.
 */
interface QiNiuApiService {

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(API_AUTH_LOGIN)
    fun login(@Field("phone") phone: String,
              @Field("password") password: String): Observable<HttpResponse<UserEntity>>

    /**
     * 获取七牛上传Token
     *
     * @return
     */
    @GET(API_HELPER_QINIU)
    fun getToken(@Header("Authorization") header: String): Observable<HttpResponse<UploadToken>>

    companion object {
        //interface 中变量实际会默认加上public static final，也就是默认为常量
        //登录
        const val API_AUTH_LOGIN = "api/auth/login"
        //七牛认证
        const val API_HELPER_QINIU = "api/helper/qiniu"
    }
}
