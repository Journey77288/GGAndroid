package com.jsrs.user.retrofit;

import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;


import java.util.List;

import com.jsrs.user.ApplicationTest;
import com.jsrs.user.model.Contributor;
import com.jsrs.user.http.API;
import com.jsrs.user.http.APICallback;
import com.jsrs.user.http.SimpleCallback;
import com.jsrs.user.service.GitHubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tony on 10/22/15.
 */
public class RetrofitTest extends ApplicationTest {

    private static final String TAG = RetrofitTest.class.getSimpleName();

    public void testGenerator() {
        GitHubService github = API.of(GitHubService.class);
        Call<List<Contributor>> call = github.contributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                testLooper();

                Log.d(TAG, response.code() + "");
//                try {
//                    Log.d(TAG, response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Log.d(TAG, "Throwable " + t);

                testLooper();
            }
        });

        SystemClock.sleep(2000);
    }

    private void testLooper() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.d(TAG, "main");
        } else {
            Log.d(TAG, "thread");
        }
    }

    public void testSimpleCallback() throws InterruptedException {
        API.of(GitHubService.class).contributors("square", "retrofit").enqueue(new SimpleCallback<List<Contributor>>() {
            @Override
            public void onSuccess(List<Contributor> contributors) {
                Log.i(TAG, "onSuccess, data: " + contributors);
            }
        });

        //sleep 一分钟 等待网络请求回调当前线程（仅用于测试）
        Thread.sleep(1 * 60 * 1000l);
    }

    public void testImplementationCallback() throws InterruptedException {
        API.of(GitHubService.class).contributors("square", "retrofit").enqueue(new APICallback<List<Contributor>>() {
            @Override
            public void onSuccess(List<Contributor> contributors) {
                Log.i(TAG, "onSuccess, data: " + contributors);
            }

            @Override
            public void onFailed(String message) {
                Log.i(TAG, "onLoginFailed, message: " + message);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish ");
            }
        });

        //sleep 一分钟 等待网络请求回调当前线程（仅用于测试）
        Thread.sleep(1 * 60 * 1000l);
    }


}
