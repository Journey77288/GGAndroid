package io.ganguo.picker.core.model

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.loader.BucketMediaLoader
import io.ganguo.utils.runOnUiThread
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/23
 *     desc   : Bucket Media Data Model
 * </pre>
 *
 * Bucket Media Data Model
 * 使用关系：PickerFragment -> BucketMediaModel -> BucketMediaLoader
 * Bucket Media Model (初始化) -> (使用BucketMediaLoader查询数据) -> (异步查询完成后通知) PickerFragment
 * @param
 * @see
 * @author Raynor
 * @property
 */
class BucketMediaModel : LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var contextReference: WeakReference<Context>
    private lateinit var loaderManager: LoaderManager
    private var callbacks: BucketMediaModelCallbacks? = null


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val context = contextReference.get()

        check(context != null) { "context must not be null" }

        val bucket: Bucket? = args?.getParcelable(ARGS_BUCKET)

        check(bucket != null) { "bucket must not be null" }

        val enableCapture = args.getBoolean(ARGS_ENABLE_CAPTURE, false)

        return BucketMediaLoader.newInstance(context, bucket, enableCapture)

    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        contextReference.get()?.apply {
            callbacks?.onBucketMediaLoad(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        contextReference.get()?.apply {
            callbacks?.onBucketMediaReset()
        }
    }

    fun onCreate(context: FragmentActivity, callbacks: BucketMediaModelCallbacks) {
        contextReference = WeakReference(context)
        loaderManager = LoaderManager.getInstance(context)
        this.callbacks = callbacks
    }

    fun onDestroy() {
        if (this::loaderManager.isInitialized) {
            loaderManager.destroyLoader(LOADER_ID)
        }
        callbacks = null
    }

    fun load(bucket: Bucket) {
        load(bucket, false)
    }

    fun load(bucket: Bucket, enableCapture: Boolean) {
        val args = Bundle()
        args.putParcelable(ARGS_BUCKET, bucket)
        args.putBoolean(ARGS_ENABLE_CAPTURE, enableCapture)
        loaderManager.initLoader(LOADER_ID, args, this)
    }

    fun reload(bucket: Bucket, enableCapture: Boolean) {
        runOnUiThread {
            val args = Bundle()
            args.putParcelable(ARGS_BUCKET, bucket)
            args.putBoolean(ARGS_ENABLE_CAPTURE, enableCapture)
            loaderManager.restartLoader(LOADER_ID, args, this)
        }
    }

    companion object {
        private const val LOADER_ID = 2
        const val ARGS_BUCKET = "args_bucket"
        const val ARGS_ENABLE_CAPTURE = "args_enable_capture"
    }

    interface BucketMediaModelCallbacks {
        fun onBucketMediaLoad(cursor: Cursor?)

        fun onBucketMediaReset()
    }
}