package io.ganguo.rx;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import io.reactivex.Observable;

/**
 * Created by Roger on 8/10/16.
 */
public class RxActivityResult {
    /**
     * StartActivity in rx way
     *
     * @param activity    a activity implement {@link ActivityResultOwner}
     * @param intent      The intent to start
     * @param requestCode If >= 0, this code will be returned in
     *                    {@link ActivityResult} when the activity exits.
     * @return a Observable emit {@link ActivityResult}
     */
    @SuppressWarnings("unchecked raw type")
    public static Observable<ActivityResult> startIntent(Activity activity, Intent intent, int requestCode) {
        if (activity instanceof ActivityResultOwner) {
            activity.startActivityForResult(intent, requestCode);
            return ((ActivityResultOwner) activity).getActivityResult().hide();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked raw type")
    public static Observable<ActivityResult> startIntent(Activity activity, Intent intent) {
        if (activity instanceof ActivityResultOwner) {
            activity.startActivityForResult(intent, 0);
            return ((ActivityResultOwner) activity).getActivityResult().hide();
        } else {
            return null;
        }
    }

    /**
     * StartActivity in rx way
     *
     * @param fragment    a fragment implement {@link ActivityResultOwner}
     * @param intent      The intent to start
     * @param requestCode If >= 0, this code will be returned in
     *                    {@link ActivityResult} when the activity exits.
     * @return a Observable emit {@link ActivityResult}
     */
    @SuppressWarnings("unchecked raw type")
    public static Observable<ActivityResult> startIntent(Fragment fragment, Intent intent, int requestCode) {
        if (fragment instanceof ActivityResultOwner) {
            fragment.startActivityForResult(intent, requestCode);
            return ((ActivityResultOwner) fragment).getActivityResult().hide();
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked raw type")
    public static Observable<ActivityResult> startIntent(Fragment fragment, Intent intent) {
        if (fragment instanceof ActivityResultOwner) {
            fragment.startActivityForResult(intent, 0);
            return ((ActivityResultOwner) fragment).getActivityResult().hide();
        } else {
            return null;
        }
    }
}