package io.ganguo.rx;


import io.reactivex.subjects.Subject;

/**
 * Created by Roger on 25/07/2017.
 */

public interface ActivityResultOwner {
    Subject<ActivityResult> getActivityResult();
}
