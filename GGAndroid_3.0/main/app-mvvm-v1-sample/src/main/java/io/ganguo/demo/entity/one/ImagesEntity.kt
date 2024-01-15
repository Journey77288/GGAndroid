package io.ganguo.demo.entity.one

import com.google.gson.annotations.SerializedName


/**
 * <pre>
 * author : leo
 * time   : 2019/01/03
 * desc   : 图片数据
</pre> *
 */
class ImagesEntity {
    @SerializedName("large")
    internal var large: String? = null
    @SerializedName("small")
    internal var small: String? = null
    @SerializedName("medium")
    internal var medium: String? = null
}
