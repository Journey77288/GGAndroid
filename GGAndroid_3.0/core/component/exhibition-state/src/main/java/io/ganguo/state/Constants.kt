package io.ganguo.state

/**
 * <pre>
 *     author : leo
 *     time   : 2019/12/04
 *     desc   : 通用常量
 * </pre>
 * @property LOADING 加载View key
 * @property ERROR  页面错误View key
 * @property NETWORK_ERROR 网络错误View key
 * @property EMPTY 空数据View key
 */
class Constants {

    companion object {
        const val LOADING = "loading"
        const val ERROR = "error"
        const val NETWORK_ERROR = "network_error"
        const val EMPTY = "empty"
    }
}