package io.ganguo.push.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * <pre>
 *     @author : zoyen
 *     time   : 2019/09/25
 *     desc   : 推送实体类
 * </pre>
 */
@Parcelize
data class PushEntity(
        @SerializedName("content")
        var content: String? = "",
        @SerializedName("message")
        var message: String? = "",
        @SerializedName("type")
        val type: Int? = 0,
        @SerializedName("id")
        val id: Int? = 0,
        @SerializedName("title")
        val title: String? = ""
) : Parcelable