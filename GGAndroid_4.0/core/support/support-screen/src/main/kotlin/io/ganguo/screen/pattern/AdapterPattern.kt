package io.ganguo.screen.pattern

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/06
 *     desc   : Adapter pattern
 * @property value String
 * @property DESIGN_HEIGHT According to the design size of the height of the adaptation
 * @property DESIGN_WIDTH According to the design size of the width of the adaptation
 * @constructor
 */
enum class AdapterPattern(var value: String) {
    DESIGN_HEIGHT("design_height"),
    DESIGN_WIDTH("design_width");
}