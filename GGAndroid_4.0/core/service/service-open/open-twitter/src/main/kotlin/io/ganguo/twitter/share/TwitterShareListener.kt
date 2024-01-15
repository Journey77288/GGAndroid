package io.ganguo.twitter.share


/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter分享回调
 * </pre>
 */
interface TwitterShareListener {

    fun onComplete()

    fun onFailed(exception: Throwable)
}