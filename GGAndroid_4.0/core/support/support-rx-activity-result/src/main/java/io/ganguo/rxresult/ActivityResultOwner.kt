package io.ganguo.rxresult

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject


/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/29
 *     desc   : ActivityResultOwner
 * </pre>
 */
interface ActivityResultOwner {
    val activityResult: Subject<ActivityResult>


    /**
     * Subject toObservable
     * The default is to observe only once
     * @return Observable<ActivityResult>
     */
    fun toObservable(takeCount: TakeCount = TakeCount.ONCE): Observable<ActivityResult> = let {
        val observable = activityResult
                .hide()
                .distinctUntilChanged()
        when (takeCount) {
            TakeCount.UNLIMITED -> {
                observable
            }
            else -> {
                observable.take(takeCount.value)
            }
        }
    }

    /**
     * to observe the number of
     * @property value Long
     * @constructor
     */
    enum class TakeCount(var value: Long) {
        ONCE(1),
        UNLIMITED(-1);

        companion object {

            /**
             * Custom observation times
             * @param count Long
             * @return TakeCount
             */
            @JvmStatic
            fun parse(count: Long): TakeCount = let {
                values().singleOrNull {
                    it.value == count
                } ?: ONCE
            }

        }

    }
}
