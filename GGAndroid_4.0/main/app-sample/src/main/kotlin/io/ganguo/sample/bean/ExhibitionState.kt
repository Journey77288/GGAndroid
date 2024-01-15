package io.ganguo.sample.bean

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/02/19
 *     desc   : Exhibition State Enum
 * </pre>
 * @property [LOADING] 加载中
 * @property [COMPLETE] 加载完成
 * @property [EMPTY] 空白数据
 * @property [ERROR] 加载异常
 * @property [NETWORK_ERROR] 网络异常
 */
enum class ExhibitionState {
    LOADING, COMPLETE, EMPTY, ERROR, NETWORK_ERROR
}