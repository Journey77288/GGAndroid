package io.ganguo.picker.core.model

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import io.ganguo.picker.core.loader.BucketLoader
import java.lang.ref.WeakReference

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/04
 *     desc   : Bucket Data Model
 * </pre>
 *
 * Bucket Data Model
 *
 * 使用关系：PickerActivity -> BucketModel -? BucketLoader
 * @param
 * @see
 * @author Raynor
 * @property
 */
class BucketModel : LoaderManager.LoaderCallbacks<Cursor>, DefaultLifecycleObserver {
    private lateinit var contextReference: WeakReference<Context>
    private lateinit var loaderManager: LoaderManager
    private var isLoadFinished = false
    lateinit var callbacks: BucketModelCallbacks
    var currentSelection = 0


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val context = contextReference.get()
                ?: throw IllegalArgumentException("context must not be null")

        isLoadFinished = false
        return BucketLoader.newInstance(context)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val context = contextReference.get()
                ?: return

        if (!isLoadFinished) {
            isLoadFinished = true
            callbacks.onBucketLoad(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        val context = contextReference.get()
                ?: return

        callbacks.onBucketReset()
    }

    fun onCreate(activity: AppCompatActivity, callback: BucketModelCallbacks) {
        contextReference = WeakReference(activity)
        loaderManager = LoaderManager.getInstance(activity)
        callbacks = callback
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        //TODO 读取保存 选中的Bucket
        currentSelection = 0
    }

    fun onSaveInstanceState(state: Bundle) {
        //存储选中状态
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        if (this::loaderManager.isInitialized) {
            loaderManager.destroyLoader(LOADER_ID)
        }
    }

    fun load() {
        loaderManager.initLoader(LOADER_ID, null, this)
    }

    companion object {
        private const val LOADER_ID = 1
    }
}

interface BucketModelCallbacks {
    fun onBucketLoad(cursor: Cursor?)

    fun onBucketReset()
}