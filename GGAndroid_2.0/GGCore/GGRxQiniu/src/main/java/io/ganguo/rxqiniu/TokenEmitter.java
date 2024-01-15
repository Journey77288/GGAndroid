package io.ganguo.rxqiniu;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Roger on 8/3/16.
 */
class TokenEmitter {
    private static String TAG = "TokenEmitter";

    private final Subject<String> subject;
    private String token;

    public TokenEmitter() {
        subject = PublishSubject.<String>create().toSerialized();
    }

    public Observable<String> asObservable() {
        return subject;
    }

    public void onNext() {
        subject.onNext(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
