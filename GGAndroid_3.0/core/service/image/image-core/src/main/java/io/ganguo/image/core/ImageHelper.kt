package io.ganguo.image.core

import androidx.recyclerview.widget.RecyclerView
import io.ganguo.image.core.engine.ImageEngine
import org.jetbrains.annotations.NotNull

/**
 * <pre>
 * author : leo
 * time   : 2019/03/21
 * desc   : 图片加载工具类
 * </pre>
 */
class ImageHelper private constructor(var mBuilder: Builder) {
    private val imageLoadListener: RecyclerView.OnScrollListener by lazy {
        newImageLoadListener()
    }

    /**
     * 根据RecyclerView滑动状态，配置图片是否加载
     *
     * @return
     */
    private fun newImageLoadListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                //列表停止滑动，恢复加载图片
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getEngine().resumeLoadImage(recyclerView.context)
                } else {
                    getEngine().pauseLoadImage(recyclerView.context)
                }
            }
        }
    }

    /**
     * 获取图片加载器
     *
     * @return
     */
    fun getEngine(): ImageEngine {
        if (mBuilder.engine == null) {
            throw RuntimeException("Please add ImageEngine!!!")
        }
        return mBuilder.engine!!
    }


    /**
     * 参数配置
     */
    class Builder {
        //图片加载器
        internal var engine: ImageEngine? = null

        fun setEngine(engine: ImageEngine): Builder {
            this.engine = engine
            return this
        }

    }

    companion object {
        private var mBuilder: Builder? = null
        private var INSTANCE: ImageHelper? = null

        /**
         * ImageHelper 初始化
         *
         * @param builder
         */
        @JvmStatic
        fun init(@NotNull builder: Builder): ImageHelper {
            mBuilder = builder
            INSTANCE = ImageHelper(builder)
            return INSTANCE!!
        }

        /**
         * 单例
         */
        @JvmStatic
        fun get(): ImageHelper {
            if (INSTANCE == null) {
                throw RuntimeException("Please initialize ImageHelper in Application")
            }
            return INSTANCE!!
        }

        /**
         * 是否初始化
         */
        private fun isInit(): Boolean {
            return INSTANCE != null
        }

        /**
         * 绑定OnScrollListener，处理列表滑动图片加载与否
         * @param recyclerView RecyclerView
         */
        @JvmStatic
        fun bindImageLoadScrollIdle(recyclerView: RecyclerView?) {
            if (recyclerView == null || !isInit()) {
                return
            }
            recyclerView.addOnScrollListener(get().imageLoadListener)
        }

    }
}
