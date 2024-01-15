package io.ganguo.incubator.retrofit;

import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;

import io.ganguo.incubator.ApplicationTest;
import io.ganguo.incubator.entity.Contributor;
import io.ganguo.incubator.http.HttpCallback;
import io.ganguo.incubator.http.API;
import io.ganguo.incubator.service.GitHubService;
import io.ganguo.library.exception.NetworkException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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
            public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
                testLooper();

                Log.d(TAG, response.code() + "");
//                try {
//                    Log.d(TAG, response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure(Throwable t) {
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

    public void testError() {
        GitHubService github = API.of(GitHubService.class);
        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        call.enqueue(new HttpCallback<List<Contributor>, Error>() {
            @Override
            public void onSuccess(List<Contributor> data) {
                Log.i(TAG, "data: " + data);
            }

            @Override
            public void onError(Error error) {
                Log.i(TAG, "error: " + error);
            }

            @Override
            public void onException(NetworkException e) {
                Log.i(TAG, "exception: " + e);
            }
        });
    }

}
