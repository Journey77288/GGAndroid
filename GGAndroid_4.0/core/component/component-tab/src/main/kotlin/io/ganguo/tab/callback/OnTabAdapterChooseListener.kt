package io.ganguo.tab.callback

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/29
 *   @desc   : OnTabChooseListener adapter
 * </pre>
 */
abstract class OnTabAdapterChooseListener : OnTabChooseListener {
    override fun isSwitchTab(position: Int): Boolean {
        return true
    }
}