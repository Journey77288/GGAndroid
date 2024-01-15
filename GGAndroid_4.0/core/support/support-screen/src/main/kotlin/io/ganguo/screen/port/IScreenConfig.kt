package io.ganguo.screen.port

import io.ganguo.screen.pattern.AdapterPattern

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/06
 *     desc   : Screen configuration parameters interface
 * Screen configuration parameters interface
 * @property designSize Int Design height dimensions
 * @property adapterPattern AdapterMode
 * [adapterPattern] equal to [AdapterPattern.DESIGN_HEIGHT]，When fitting, go to the designHeight field. [designSize] == designHeight
 * [adapterPattern] equal to [AdapterPattern.DESIGN_WIDTH]，When fitting, go to the designWidth field. [designSize] == designWidth
 */
internal interface IScreenConfig {
    var designSize: Float
    var adapterPattern: AdapterPattern
}