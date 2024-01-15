package io.ganguo.demo.http.response

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * 《豆瓣》开源API
 * Created by leo on 2018/7/30.
 */

class DouBanHttpResponse<T>(@SerializedName("msg")
                            var msg: String? = null,
                            @SerializedName("code")
                            var code: Int = 0,
                            @SerializedName("subjects")
                            var data: T? = null)
