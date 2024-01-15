package io.ganguo.image.core.imageget

import android.graphics.drawable.Drawable
import android.text.Html.ImageGetter
import android.widget.TextView
import io.ganguo.image.core.ImageHelper.Companion.get
import io.ganguo.image.core.drawable.URLDrawable
import io.ganguo.image.core.entity.ImageParam

/**
 * <pre>
 * author : leo
 * time   : 2019/07/18
 * desc   : TextView Html 图片加载器
 * </pre>
 */
class URLImageGetter(private val textView: TextView, private val matchParentWidth: Boolean) : ImageGetter {
    override fun getDrawable(source: String): Drawable {
        val urlDrawable = URLDrawable()
        textView.post {
            var param = ImageParam.Builder()
                    .url(source)
                    .build()
            get().getEngine().displayImageDrawable(textView, param, urlDrawable, matchParentWidth)
        }
        return urlDrawable
    }

}