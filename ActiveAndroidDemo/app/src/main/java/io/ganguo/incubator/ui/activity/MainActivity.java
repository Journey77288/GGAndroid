package io.ganguo.incubator.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import io.ganguo.incubator.R;
import io.ganguo.incubator.databinding.ActivityMainBinding;
import io.ganguo.incubator.entity.Contributor;
import io.ganguo.incubator.entity.Issue;
import io.ganguo.incubator.http.API;
import io.ganguo.incubator.service.GitHubService;
import io.ganguo.library.ui.activity.BaseActivity;
import io.ganguo.library.ui.adapter.v7.LoadMoreListener;
import io.ganguo.library.ui.adapter.v7.SimpleAdapter;
import io.ganguo.library.util.Exits;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;
import io.ganguo.opensdk.login.LoginDemoActivity;
import io.ganguo.opensdk.share.ShareDemoActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private View action_share_demo;
    private View action_login_demo;
    private View action_network;

    private GitHubService gitHubService = API.of(GitHubService.class);
    private ActivityMainBinding binding;
    private SimpleAdapter adapter;

    @Override
    public void beforeInitView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void initView() {
        action_share_demo = findViewById(R.id.action_share_demo);
        action_login_demo = findViewById(R.id.action_login_demo);
        action_network = findViewById(R.id.action_network);
        adapter = new SimpleAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {

        adapter.setLoadMoreListener(new LoadMoreListener() {
            @Override
            protected void onLoadMore() {
                gitHubService.contributors("bluelinelabs", "LoganSquare").enqueue(new Callback<List<Contributor>>() {
                    @Override
                    public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
                        adapter.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        gitHubService.issues().enqueue(new Callback<List<Issue>>() {
                            @Override
                            public void onResponse(Response<List<Issue>> response, Retrofit retrofit) {
                                adapter.addAll(response.body());
                                adapter.notifyDataSetChanged();
                                adapter.onFinishLoadMore(true);
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }
        });
        action_share_demo.setOnClickListener(this);
        action_login_demo.setOnClickListener(this);
        action_network.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onBackPressed() {
        Exits.exitByDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_share_demo:
                startActivity(new Intent(this, ShareDemoActivity.class));
                break;
            case R.id.action_login_demo:
                startActivity(new Intent(this, LoginDemoActivity.class));
                break;
            case R.id.action_network:
                actionNetwork();
                break;
        }
    }

    private void actionNetwork() {
        Call<List<Issue>> call = gitHubService.issues();
        call.enqueue(new Callback<List<Issue>>() {

            @Override
            public void onResponse(Response<List<Issue>> response, Retrofit retrofit) {
                logger.w("actionNetwork " + response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                logger.w("actionNetwork ", t);
            }

        });
    }

}
