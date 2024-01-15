package io.ganguo.state.core

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/15
 *   @desc   : IStateViewGatherCreator 常用状态布局创建接口
 * </pre>
 */
interface IStateViewGatherCreator {

    /**
     * add StateViewService
     * @param stateViewKey String
     * @param service IStateViewCreator
     */
    fun addCreator(stateViewKey: String, service: StateViewService)

    /**
     * add Loading StateViewService
     * @param service StateViewService
     */
    fun addLoadingViewCreator(service: StateViewService)

    /**
     * add Empty StateViewService
     * @param service StateViewService
     */
    fun addEmptyViewCreator(service: StateViewService)


    /**
     * add Error View StateViewService
     * @param service StateViewService
     */
    fun addErrorViewCreator(service: StateViewService)

    /**
     * add Network Error StateViewService
     * @param service StateViewService
     */
    fun addNetworkErrorViewCreator(service: StateViewService)
}
