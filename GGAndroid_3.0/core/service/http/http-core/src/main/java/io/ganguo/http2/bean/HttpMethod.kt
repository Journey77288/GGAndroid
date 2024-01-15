package io.ganguo.http2.bean

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/04
 *     desc   : Http请求方法
 * </pre>
 */
enum class HttpMethod(var value: String) {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    PATCH("PATCH"),
    HEAD("HEAD"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
}