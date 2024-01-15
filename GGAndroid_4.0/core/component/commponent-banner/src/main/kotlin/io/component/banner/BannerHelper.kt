package io.component.banner

object BannerHelper {

    /**
     * Calculate the real position
     * @param isIncrease Boolean Whether there is additional data
     * @param position Int The current position
     * @param realCount Int The real number
     * @return Int
     */
    @JvmStatic
    fun getRealPosition(isIncrease: Boolean, position: Int, realCount: Int): Int {
        if (!isIncrease || realCount == 1) {
            return position
        }
        return when (position) {
            0 -> {
                realCount - 2
            }
            1 -> {
                realCount - 1
            }
            realCount + 2 -> {
                0
            }
            realCount + 3 -> {
                1
            }
            else -> {
                position - 2
            }
        }
    }

}
