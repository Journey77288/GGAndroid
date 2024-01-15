package io.ganguo.google;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * <p>
 * Google API 工厂类
 * </p>
 * Created by leo on 2018/9/4.
 */
public class GoogleAPIFactory {

    /**
     * function：create sign in options
     *
     * @return
     */
    public static GoogleSignInOptions createSignInOptions() {
        return new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }
}
