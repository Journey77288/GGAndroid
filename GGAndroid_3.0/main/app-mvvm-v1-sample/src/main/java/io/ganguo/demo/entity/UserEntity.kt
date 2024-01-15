package io.ganguo.demo.entity

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Tony on 10/6/15.
 */
class UserEntity(
        var id: Int = 0,
        var name: String? = null,
        @SerializedName("api_token")
        var apiToken: String? = null
)
