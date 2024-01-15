package io.image.glide

import io.image.engine.ImageEngine
import jp.wasabeef.glide.transformations.RoundedCornersTransformation.CornerType

/**
 * <pre>
 * author : leo
 * time   : 2019/03/21
 * desc   : 图片引擎工具类
</pre> *
 */
object EngineHelper {
    /**
     * Glide圆角类型转换
     *
     * @param cornerType 圆角范围
     */
    fun transitionGlideCornerType(cornerType: ImageEngine.CornerType?): CornerType {
        if (cornerType == null) {
            return CornerType.ALL
        }
        return when (cornerType) {
            ImageEngine.CornerType.ALL -> CornerType.ALL
            ImageEngine.CornerType.TOP -> CornerType.TOP
            ImageEngine.CornerType.TOP_LEFT -> CornerType.TOP_LEFT
            ImageEngine.CornerType.TOP_RIGHT -> CornerType.TOP_RIGHT
            ImageEngine.CornerType.BOTTOM -> CornerType.BOTTOM
            ImageEngine.CornerType.LEFT -> CornerType.LEFT
            ImageEngine.CornerType.RIGHT -> CornerType.RIGHT
            ImageEngine.CornerType.BOTTOM_LEFT -> CornerType.BOTTOM_LEFT
            ImageEngine.CornerType.BOTTOM_RIGHT -> CornerType.BOTTOM_RIGHT
        }
    }

}
