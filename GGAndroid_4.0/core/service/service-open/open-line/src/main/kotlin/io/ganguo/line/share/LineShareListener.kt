package io.ganguo.line.share

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/04
 *     desc   : Line分享接口回调
 * </pre>
 */
interface LineShareListener {
    /**
     * 分享完成
     */
    fun onComplete()

    /**
     * 分享失败
     */
    fun onFailed(e: Exception)
}