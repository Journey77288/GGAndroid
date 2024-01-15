package io.ganguo.screen.adapter

import io.ganguo.screen.pattern.AdapterPattern
import io.ganguo.screen.port.IScreenConfig


/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/23
 *     desc   : Screen adaptation parameters
 * </pre>
 */
class ScreenConfig private constructor(private var builder: Builder) :
        IScreenConfig by builder {


    /**
     * Screen configuration parameters interface implementation
     * @constructor
     */
    class Builder constructor(override var adapterPattern: AdapterPattern, override var designSize: Float = 375.0f) :
            IScreenConfig {

        /**
         * create ScreenConfig
         * @return T
         */
        fun build(): ScreenConfig {
            return ScreenConfig(this)
        }
    }

}
