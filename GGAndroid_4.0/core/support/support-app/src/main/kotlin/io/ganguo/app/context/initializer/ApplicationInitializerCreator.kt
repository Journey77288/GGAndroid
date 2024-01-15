package io.ganguo.app.context.initializer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : ApplicationInitializer Creator
 * </pre>
 */
interface ApplicationInitializerCreator<T : ApplicationInitializer> {

    /**
     * create ApplicationInitializer
     * @return  Pair<String, T>
     */
    fun create(): Pair<String, T> {
        return create(emptyMap())
    }

    /**
     * create ApplicationInitializer
     * @param parameter Carry configuration parameters
     * @return  Pair<String, T>
     */
    fun create(parameter: Map<String, Any>): Pair<String, T> {
        return Pair(javaClass.name, createInitializer(parameter))
    }

    /**
     * create ApplicationInitializer
     * @param parameter Carry configuration parameters
     * @return  Pair<String, T>
     */
    fun createInitializer(parameter: Map<String, Any>): T
}
