package io.image

import android.graphics.drawable.Drawable
import io.image.engine.ImageEngine
import org.jetbrains.annotations.NotNull

/**
 * <pre>
 *   @author: leo
 *   time   : 2019/03/21
 *   desc   : 图片加载工具类
 * </pre>
 */
class ImageLoader private constructor(private var builder: Builder) : ImageEngine by builder.engine {
    val errorPlaceholder: Drawable? = builder.errorPlaceholder
    val placeholder: Drawable? = builder.placeholder

    /**
     * 图片加载框架配置
     * @property engine ImageEngine?
     * @constructor
     */
    class Builder(internal var engine: ImageEngine) {
        var errorPlaceholder: Drawable? = null
        var placeholder: Drawable? = null

        fun build(): ImageLoader {
            return ImageLoader(this)
        }
    }

    companion object {
        private var INSTANCE: ImageLoader? = null

        /**
         * ImageHelper 初始化
         *
         * @param builder
         */
        @JvmStatic
        fun init(@NotNull builder: Builder): ImageLoader {
            INSTANCE = builder.build()
            return INSTANCE!!
        }

        /**
         * 单例
         */
        @JvmStatic
        fun get(): ImageLoader {
            checkNotNull(INSTANCE) {
                "Please initialize ImageHelper in Application"
            }
            return INSTANCE!!
        }
    }
}
