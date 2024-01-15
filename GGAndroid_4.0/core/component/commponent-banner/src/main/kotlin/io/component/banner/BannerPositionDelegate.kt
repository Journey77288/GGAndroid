package io.component.banner

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/05
 *   @desc   : Banner Position Delegate
 * </pre>
 */
interface BannerPositionDelegate {
    /**
     * current page count
     * @return Int
     */
    fun getPageCount(): Int {
        return if (getRealCount() > 1) getRealCount() + getIncreaseCount() else getRealCount()
    }

    /**
     * The real number
     * @return Int
     */
    fun getRealCount(): Int

    /**
     * The real position
     * @param position Int
     * @return Int
     */
    fun getRealPosition(position: Int): Int {
        return BannerHelper.getRealPosition(getIncreaseCount() == 4, position, getRealCount())
    }

    /**
     * Page Increase Count
     * @return Int
     */
    fun getIncreaseCount(): Int

    /**
     * Set Page Increase Count
     * @param count Int
     */
    fun setIncreaseCount(count: Int)
}
