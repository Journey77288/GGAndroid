package io.ganguo.demo.http.api.proxy

import io.ganguo.demo.entity.one.MovieEntity
import io.ganguo.demo.http.api.service.DouBanApiService
import io.ganguo.demo.http.response.DouBanHttpResponse
import io.ganguo.http.use.proxy.IProxyCreator
import io.ganguo.log.core.Logger
import io.reactivex.Observable

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/26
 *     desc   : DouBanApiService静态代理对象
 * </pre>
 */
class DouBanServiceStaticProxyCreator(override var target: DouBanApiService) : IProxyCreator<DouBanApiService> {
    /**
     * 创建代理
     * @return DouBanServiceProxyCreator?
     */
    override fun createProxy(): DouBanApiService? {
        return object : DouBanApiService {

            /**
             * 获取电影列表数据
             * @param apiKey String
             * @return Observable<DouBanHttpResponse<List<MovieEntity>>>
             */
            override fun getDouBanMovies(): Observable<DouBanHttpResponse<List<MovieEntity>>> {
                Logger.e("静态代理对象：请求列表前，我清空一下缓存数据")
                return target.getDouBanMovies()
            }

            override fun getGankBanners(): Observable<Any> {
                return target.getGankBanners()
            }

        }
    }
}