package io.ganguo.http2.core.use.response.paging

/**
 * <pre>
 *     author : leo
 *     time   : 2019/05/28
 *     desc   : Page工具类
 * </pre>
 */
class PagingHelper : IPaging {

    var page: Int = 0
    var lastPage: Int = 0
    var pageSize: Int = 15
    var total: Int = 0
    override fun currentPage(): Int {
        return page
    }

    override fun pageSize(): Int {
        return pageSize
    }

    override fun lastPage(): Int {
        return lastPage
    }

    override fun total(): Int {
        return total
    }

    override fun isLastPage(): Boolean {
        return lastPage <= page || lastPage == 0
    }

    override fun isFirstPage(): Boolean {
        return page == 1
    }


    override fun pageReset() {
        page = 0
    }

    override fun nextPage(): Int {
        return page + 1
    }
}