package io.ganguo.http.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.ganguo.http2.config.BaseHttpGlobalConfig
import io.ganguo.http2.config.HttpGlobalConfig
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

/**
 * <pre>
 *     author : leo
 *     time   : 2020/04/28
 *     desc   : Retrofit 服务配置
 * </pre>
 */
class RetrofitService private constructor(
        private var serviceBuilder: ConfigBuilder,
        private var clientBuilder: OkHttpClient.Builder
) {
    private val client: OkHttpClient by lazy {
        clientBuilder
                .build()
    }
    private val retrofit: Retrofit by lazy {
        serviceBuilder
                .retrofitBuilder
                .baseUrl(serviceBuilder.getMainBaseUrl())
                .client(client)
                .build()
    }


    /**
     * 是否支持多域名
     * @return Boolean
     */
    fun isSupportMultiBaseUrl() = let {
        serviceBuilder.isSupportMultiBaseUrl()
    }


    /**
     * 多BaseUrl适配器
     * @return AbstractBaseUrlAdapter?
     */
    fun getMultiUrlAdapter() = let {
        serviceBuilder.getMultiBaseUrlAdapter()
    }

    /**
     * 获取全局Header配置
     * @return Map<String, String>
     */
    fun getGlobalHeaders() = let {
        serviceBuilder.getGlobalHeaders()
    }


    /**
     * 获取全局参数配置
     * @return HashMap<String,Any>
     */
    fun getGlobalParameters() = let {
        serviceBuilder.getGlobalParameters()
    }


    /**
     * 网络请求参数配置
     * @constructor
     */
    class ConfigBuilder(
            private val globalConfig: HttpGlobalConfig = createServiceBuilder(),
            internal var retrofitBuilder: Retrofit.Builder = createRetrofitBuilder()
    ) : HttpGlobalConfig by globalConfig {

        /**
         * Add a call adapter factory for supporting service method return types other than [ ].
         */
        fun addCallAdapterFactory(factory: CallAdapter.Factory) = apply {
            retrofitBuilder.addCallAdapterFactory(factory)
        }

        /**
         * Add converter factory for serialization and deserialization of objects.
         * */
        fun addConverterFactory(factory: Converter.Factory) = apply {
            retrofitBuilder.addConverterFactory(factory)
        }

        /**
         * When calling [.create] on the resulting [Retrofit] instance, eagerly validate
         * the configuration of all methods in the supplied interface.
         */
        fun validateEagerly(validateEagerly: Boolean) = apply {
            retrofitBuilder.validateEagerly(validateEagerly)
        }


        /**
         * config init
         * @param clientBuilder OkHttpClient.Builder
         * @return RetrofitService
         */
        fun build(clientBuilder: OkHttpClient.Builder) = RetrofitService(this, clientBuilder)

    }


    /**
     * RetrofitService 伴生对象：静态实例实现
     * @property [retrofitService] Retrofit服务实例
     */
    companion object {
        private lateinit var retrofitService: RetrofitService

        /**
         * 初始化 RetrofitService
         * @param service RetrofitService
         * @return RetrofitService
         */
        @JvmStatic
        fun init(service: RetrofitService) {
            check(!::retrofitService.isInitialized) {
                "RetrofitService cannot be initialized repeatedly"
            }
            this.retrofitService = service
        }


        /**
         * get RetrofitService
         * @return RetrofitService
         */
        @JvmStatic
        internal fun get() = let {
            check(::retrofitService.isInitialized) {
                "Initialize RetrofitService with RetrofitService.Builder"
            }
            retrofitService
        }


        /**
         * RetrofitService 默认构造器实现类
         * @return ConfigBuilder
         */
        @JvmStatic
        private fun createServiceBuilder() = BaseHttpGlobalConfig()

        /**
         * 默认Retrofit.Builder
         * @return Retrofit.Builder
         */
        @JvmStatic
        fun createRetrofitBuilder() = let {
            Retrofit.Builder().apply {
                val json = Json { ignoreUnknownKeys = true }
                addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            }
        }


        /**
         * 创建Api接口服务
         * @param clazz Class<T>
         * @return T
         */
        @JvmStatic
        fun <T> createApiService(clazz: Class<T>): T = let {
            get().run {
                retrofit.create(clazz)
            }
        }
    }
}