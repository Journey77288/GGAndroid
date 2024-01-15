package io.ganguo.sticky_header

import android.content.Context
import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict

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
            val result = Pinyin.toPinyin(value, ",")
            //如果输入的字符串是空字符串对其调用first()会抛异常 所以要在使用前检查
            return if (result.isEmpty()) {
                ""
            } else {
                result.first().toString().toUpperCase()
            }
        }


    }
}