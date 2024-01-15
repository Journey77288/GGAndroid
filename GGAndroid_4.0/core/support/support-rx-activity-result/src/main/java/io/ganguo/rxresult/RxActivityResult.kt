package io.ganguo.rxresult

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.core.Observable

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/14
 *   @desc   : Activity Result based on RxJava implementation
 * </pre>
 */
class RxActivityResult {

    companion object {
        /**
         * StartActivity in rx way
         * @param activity Activity
         * @param intent Intent?
         * @param requestCode Int
         * @return Observable<ActivityResult?>?
         */
        @JvmStatic
        fun startIntent(activity: Activity, intent: Intent?, requestCode: Int = 0, takeCount: ActivityResultOwner.TakeCount = ActivityResultOwner.TakeCount.ONCE): Observable<ActivityResult> = let {
            check(activity is ActivityResultOwner) {
                "Activity must be implementation ActivityResultOwner!!"
            }
            activity.startActivityForResult(intent, requestCode)
            activity.toObservable(takeCount)
        }


        /**
         * StartActivity in rx way
         *
         * @param fragment    a fragment implement [ActivityResultOwner]
         * @param intent      The intent to start
         * @param requestCode If >= 0, this code will be returned in
         * [ActivityResult] when the activity exits.
         * @return a Observable emit [ActivityResult]
         */
        @JvmStatic
        fun startIntent(fragment: Fragment, intent: Intent?, requestCode: Int = 0, takeCount: ActivityResultOwner.TakeCount = ActivityResultOwner.TakeCount.ONCE): Observable<ActivityResult> = let {
            check(fragment is ActivityResultOwner) {
                "Activity must be implementation ActivityResultOwner!!"
            }
            fragment.startActivityForResult(intent, requestCode)
            fragment.toObservable(takeCount)
        }

    }
}


