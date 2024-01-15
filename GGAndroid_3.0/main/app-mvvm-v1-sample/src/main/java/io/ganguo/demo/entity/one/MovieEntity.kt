package io.ganguo.demo.entity.one

import com.google.gson.annotations.SerializedName

/**
 * <pre>
 * author : leo
 * time   : 2019/01/03
 * desc   : 影片数据
</pre> *
 */
//添加get、set方法，并添加、equals、canEquals、hashCode、toString方法
class MovieEntity {
    @SerializedName("pubdate")
    internal var pubdate: String? = null
    @SerializedName("title")
    internal var title: String? = null
    @SerializedName("wish")
    internal var wish: Int = 0
    @SerializedName("original_title")
    internal var originalTitle: String? = null
    @SerializedName("collection")
    internal var collection: Int = 0
    @SerializedName("orignal_title")
    internal var orignalTitle: String? = null
    @SerializedName("stars")
    internal var stars: String? = null
    @SerializedName("images")
    internal var images: ImagesEntity? = null
    @SerializedName("id")
    internal var id: String? = null
}
