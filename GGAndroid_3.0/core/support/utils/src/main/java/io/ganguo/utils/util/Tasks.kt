package io.ganguo.utils.util

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import io.ganguo.utils.util.BackgroundExecutor.executor
import io.ganguo.utils.util.ContextHelper.handler
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/19
 *     desc   : 任务处理工具，基于Anko源码修改
 * </pre>
 */

/**
 * Execute [f] on the application UI thread.
 * @receiver Context
 * @param f [@kotlin.ExtensionFunctionType] Function1<Context, Unit>
 */
fun runOnUiThread(f: () -> Unit) {
    if (Looper.getMainLooper() === Looper.myLooper()) {
        f()
    } else {
        handler.post { f() }
    }
}

/**
 * Execute [task] asynchronously.
 * @param task Function0<Unit>
 */
fun runOnThreadPool(task: () -> Unit) {
    BackgroundExecutor.submit { task.invoke() }
}

/**
 * Execute [f] on the application UI thread.
 * @receiver Context
 * @param f [@kotlin.ExtensionFunctionType] Function1<Context, Unit>
 */
fun Context.runOnUiThread(f: Context.() -> Unit) {
    if (Looper.getMainLooper() === Looper.myLooper()) {
        f()
    } else {
        handler.post { f() }
    }
}

/**
 * Execute [f] on the application UI thread.
 * @receiver Context
 * @param f [@kotlin.ExtensionFunctionType] Function1<Context, Unit>
 */
fun Context.runOnUiThread(delayMillis: Long, f: Context.() -> Unit) {
    postDelayed(delayMillis, f)
}

/**
 * Execute [f] on the application UI thread.
 * @receiver Context
 * @param f [@kotlin.ExtensionFunctionType] Function1<Context, Unit>
 */
fun Context.postDelayed(delayMillis: Long, f: Context.() -> Unit) {
    handler.postDelayed({ f.invoke(this) }, delayMillis)
}

/**
 * android  Handler
 * @return Handler
 */
fun handler(): Handler {
    return handler
}


/**
 * Execute [f] on the application UI thread.
 * If the [doAsync] receiver still exists (was not collected by GC),
 *  [f] gets it as a parameter ([f] gets null if the receiver does not exist anymore).
 */
fun <T> AsyncContext<T>.onComplete(f: (T?) -> Unit) {
    val ref = weakRef.get()
    if (Looper.getMainLooper() === Looper.myLooper()) {
        f(ref)
    } else {
        handler.post { f(ref) }
    }
}

/**
 * Execute [f] on the application UI thread.
 * [doAsync] receiver will be passed to [f].
 * If the receiver does not exist anymore (it was collected by GC), [f] will not be executed.
 */
fun <T> AsyncContext<T>.uiThread(f: (T) -> Unit): Boolean {
    val ref = weakRef.get() ?: return false
    if (Looper.getMainLooper() === Looper.myLooper()) {
        f(ref)
    } else {
        handler.post { f(ref) }
    }
    return true
}

/**
 * Execute [f] on the application UI thread if the underlying [Activity] still exists and is not finished.
 * The receiver [Activity] will be passed to [f].
 *  If it is not exist anymore or if it was finished, [f] will not be called.
 */
fun <T : Activity> AsyncContext<T>.activityUiThread(f: (T) -> Unit): Boolean {
    val activity = weakRef.get() ?: return false
    if (activity.isFinishing) return false
    activity.runOnUiThread { f(activity) }
    return true
}

fun <T : Activity> AsyncContext<T>.activityUiThreadWithContext(f: Context.(T) -> Unit): Boolean {
    val activity = weakRef.get() ?: return false
    if (activity.isFinishing) return false
    activity.runOnUiThread { activity.f(activity) }
    return true
}

@JvmName("activityContextUiThread")
fun <T : Activity> AsyncContext<WrapContext<T>>.activityUiThread(f: (T) -> Unit): Boolean {
    val activity = weakRef.get()?.owner ?: return false
    if (activity.isFinishing) return false
    activity.runOnUiThread { f(activity) }
    return true
}

@JvmName("activityContextUiThreadWithContext")
fun <T : Activity> AsyncContext<WrapContext<T>>.activityUiThreadWithContext(f: Context.(T) -> Unit): Boolean {
    val activity = weakRef.get()?.owner ?: return false
    if (activity.isFinishing) return false
    activity.runOnUiThread { activity.f(activity) }
    return true
}


/**
 * Execute [task] asynchronously.
 *
 * @param exceptionHandler optional exception handler.
 *  If defined, any exceptions thrown inside [task] will be passed to it. If not, exceptions will be ignored.
 * @param task the code to execute asynchronously.
 */
fun <T> T.doAsync(
        exceptionHandler: ((Throwable) -> Unit)? = crashLogger,
        task: AsyncContext<T>.() -> Unit
): Future<Unit> {
    val context = AsyncContext(WeakReference(this))
    return BackgroundExecutor.submit {
        return@submit try {
            context.task()
        } catch (thr: Throwable) {
            val result = exceptionHandler?.invoke(thr)
            if (result != null) {
                result
            } else {
                Unit
            }
        }
    }
}

fun <T> T.doAsync(
        exceptionHandler: ((Throwable) -> Unit)? = crashLogger,
        executorService: ExecutorService,
        task: AsyncContext<T>.() -> Unit
): Future<Unit> {
    val context = AsyncContext(WeakReference(this))
    return executorService.submit<Unit> {
        try {
            context.task()
        } catch (thr: Throwable) {
            exceptionHandler?.invoke(thr)
        }
    }
}

/**
 *
 * Execute [task] asynchronously and return the corresponding Future<R>
 * @receiver T
 * @param exceptionHandler Function1<Throwable, Unit>?
 * @param task [@kotlin.ExtensionFunctionType] Function1<AsyncContext<T>, R>
 * @return Future<R>
 */
fun <T, R> T.asyncResult(
        exceptionHandler: ((Throwable) -> Unit)? = crashLogger,
        task: AsyncContext<T>.() -> R
): Future<R> {
    val context = AsyncContext(WeakReference(this))
    return BackgroundExecutor.submit {
        try {
            context.task()
        } catch (thr: Throwable) {
            exceptionHandler?.invoke(thr)
            throw thr
        }
    }
}

fun <T, R> T.asyncResult(
        exceptionHandler: ((Throwable) -> Unit)? = crashLogger,
        executorService: ExecutorService,
        task: AsyncContext<T>.() -> R
): Future<R> {
    val context = AsyncContext(WeakReference(this))
    return executorService.submit<R> {
        try {
            context.task()
        } catch (thr: Throwable) {
            exceptionHandler?.invoke(thr)
            throw thr
        }
    }
}


/**
 * Throwable print
 */
private val crashLogger = { throwable: Throwable ->
    throwable.printStackTrace()
}


/**
 * AsyncContext
 * @param T
 * @property weakRef WeakReference<T>
 * @constructor
 */
class AsyncContext<T>(val weakRef: WeakReference<T>)

/**
 * Background Thread
 * @property [executor] The thread pool object
 */
internal object BackgroundExecutor {
    private var executor: ExecutorService =
            Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors())

    fun <T> submit(task: () -> T): Future<T> = executor.submit(task)
}

/**
 * Context Helper
 * @property [handler] MainLooper Handler object
 */
private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
}

/**
 * implementation ViewManager
 * @param out T
 * @property owner T
 */
interface WrapContext<out T> : ViewManager {
    val owner: T

    override fun updateViewLayout(view: View, params: ViewGroup.LayoutParams) {
        throw UnsupportedOperationException()
    }

    override fun removeView(view: View) {
        throw UnsupportedOperationException()
    }
}