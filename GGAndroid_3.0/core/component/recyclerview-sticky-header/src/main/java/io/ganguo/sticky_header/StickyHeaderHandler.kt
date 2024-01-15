package io.ganguo.sticky_header

import android.content.Context
import android.graphics.Typeface
import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict
import io.ganguo.sticky_header.header.R
import io.ganguo.utils.helper.ResHelper

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/02
 *     desc   : StickyHeader 工具类
 * </pre>
 */
interface StickyHeaderHandler : StickyHeaderDecoration.IStickyHeader {
    companion object {
        private var pinyinInit: Boolean = false

        private val headerLeft: Int by lazy {
            ResHelper.getDimensionPixelOffsets(R.dimen.dp_20)
        }
        private val headerHeight: Int by lazy {
            ResHelper.getDimensionPixelOffsets(R.dimen.dp_27)
        }
        private val headerTextSize: Float by lazy {
            ResHelper.getDimensionPixelSize(R.dimen.font_14).toFloat()
        }
        private val headerBgColor: Int by lazy {
            ResHelper.getColor(R.color.color_e3e3e3)
        }
        private val headerTextColor: Int by lazy {
            ResHelper.getColor(R.color.color_333333)
        }


        /**
         * 文章转拼音类库初始化
         * @param context Context
         */
        fun initPinYinLibrary(context: Context) {
            // 添加中文城市词典
            Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(context)))
            pinyinInit = true
        }


        /**
         * 汉字转字符串，并且取结果的第一个字符
         * @param value String
         * @return String
         */
        fun subFirstPinYin(value: String): String {
            check(pinyinInit) {
                "Call the Application function in initPinYinLibrary first"
            }
            return Pinyin.toPinyin(value, ",").first().toString().toUpperCase()
        }

        /**
         * 创建StickyHeader配置
         * @param latter String
         * @return StickyHeaderDecoration.StickyHeaderConfig
         */
        fun newDefaultStickyHeaderConfig(latter: String): StickyHeaderConfig {
            return StickyHeaderConfig(latter, headerHeight, headerBgColor, headerTextColor, headerTextSize, headerLeft, Typeface.DEFAULT_BOLD)
        }

    }
}