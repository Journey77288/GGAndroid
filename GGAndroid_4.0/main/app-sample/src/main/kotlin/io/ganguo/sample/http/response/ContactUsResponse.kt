package io.ganguo.sample.http.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 联系我们 data 实体类
 * @property contactUs String?
 * @constructor
 */
@Parcelize
@Serializable
data class ContactUsResponse(
        @SerialName("contact_us") var contactUs: String? = ""
) : Parcelable
