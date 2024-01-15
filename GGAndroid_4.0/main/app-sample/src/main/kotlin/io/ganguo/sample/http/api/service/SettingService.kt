package io.ganguo.sample.http.api.service

import io.ganguo.http.gg.response.HttpResponse
import io.ganguo.sample.http.response.ContactUsResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/13
 *   @desc   : 设置模块接口
 * </pre>
 */
interface SettingService {


    /**
     * 联系我们
     * @return Observable<ContactUsResponse>
     */
    @GET(CONTACT_US)
    fun getContactUs(): Observable<HttpResponse<ContactUsResponse>>

    /**
     * @see CONTACT_US 联系我们
     */
    companion object {
        private const val CONTACT_US = "api/user/setting/contact-us"
    }
}

