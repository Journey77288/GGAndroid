package io.ganguo.core.context.initializer

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : Default ApplicationInitializer
 * </pre>
 */
interface ApplicationInitializerCreator <T : ApplicationInitializer> {


    /**
     * create ApplicationInitializer
     * @return  Pair<String, T>
     */
    fun create(): Pair<String, T> = create(emptyMap())

    /**
     * create ApplicationInitializer
     * @param parameter Carry configuration parameters
     * @return  Pair<String, T>
     */
    fun create(parameter: Map<String, Any>): Pair<String, T> = Pair(javaClass.name, createInitializer(parameter))

    /**
     * create ApplicationInitializer
     * @param parameter Carry configuration parameters
     * @return  Pair<String, T>
     */
    fun createInitializer(parameter: Map<String, Any>): T
}