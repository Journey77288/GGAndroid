package io.ganguo.sticky_header

import android.graphics.Color
import android.graphics.Typeface
import androidx.annotation.ColorInt
import androidx.annotation.Dimension

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/09
 *     desc   : 粘性头部配置
 * </pre>
 * @property content header内容
 * @property height header高度
 * @property bgColor header背景颜色
 * @property textColor 字体颜色
 * @property textSize 文字大小
 * @property textStyle 文字风格
 * @property left 左边距
 */
class StickyHeaderConfig(
        var content: String = "",
        var height: Int = 0,
        @ColorInt
        var bgColor: Int = Color.TRANSPARENT,
        var textColor: Int = Color.TRANSPARENT,
        @Dimension
        var textSize: Float = 10f,
        @Dimension
        var left: Int = 0,
        var textStyle: Typeface = Typeface.DEFAULT
)