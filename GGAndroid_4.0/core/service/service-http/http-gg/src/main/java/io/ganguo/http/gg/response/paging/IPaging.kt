package io.ganguo.http.gg.response.paging

/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/28
 *     desc   :
 * </pre>
 */
interface IPaging {
    fun currentPage(): Int
    fun pageSize(): Int
    fun lastPage(): Int
    fun total(): Int
    fun isLastPage(): Boolean
    fun pageReset()
    fun nextPage(): Int
    fun isFirstPage(): Boolean
}