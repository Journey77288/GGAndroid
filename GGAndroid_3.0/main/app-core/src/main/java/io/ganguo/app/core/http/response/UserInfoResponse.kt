package io.ganguo.app.core.http.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * <pre>
 * author : Leo
 * time   : 2018/12/14
 * desc   : 用户数据Entity类
 *</pre>
 */
@Parcelize
data class UserInfoResponse(
        var id: Int = 0,
        var name: String? = null,
        @SerializedName("api_token")
        var apiToken: String = ""
) : Parcelable
