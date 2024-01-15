package io.ganguo.demo.http.domain

import io.ganguo.demo.BuildConfig

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/30
 *     desc   : 域名通用常量配置
 * </pre>
 */
class DomainConstant private constructor() {

    /**
     * 域名参数key
     */
    object Domain {
        const val DOMAIN_HEADER_KEY = "DOMAIN_HEADER_DEFAULT_KEY"


        /**
         * 域名Key
         */
        object DomainKey {
            const val GANGUO = "ganguo"
            const val GANK = "gank"
            const val DOUBAN = "douban"
        }

        /**
         * 具体的域名链接
         */
        object DomainURL {
            const val DOUBAN = BuildConfig.DOUBAN
            const val GANK = "https://gank.io"
            const val GANGUO = "https://gitlab.ganguomob.com"
        }
    }
}