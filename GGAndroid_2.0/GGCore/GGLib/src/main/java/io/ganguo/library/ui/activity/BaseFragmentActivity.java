package io.ganguo.library.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import io.ganguo.library.ui.base.FragmentNavigator;
import io.ganguo.library.ui.base.FragmentPresenter;
import io.ganguo.library.ui.base.MultiFragmentNavigator;
import io.ganguo.utils.AppManager;
import io.ganguo.utils.common.ScreenAdaptors;

/**
 * FragmentActivity - 基类
 * <p>
 * Created by hulk on 10/20/14.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity implements InitResources, FragmentPresenter {
    private FragmentNavigator mFragmentNavigator = null;

    public BaseFragmentActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // register
        AppManager.addActivity(this);
        ScreenAdaptors.onBindScreenAdapter(this);
        // init resources
        initFragmentNavigator(savedInstanceState);
        beforeInitView();
        initView();
        initListener();
        initData();
    }

    private void initFragmentNavigator(Bundle savedInstanceState) {
        mFragmentNavigator = createFragmentNavigator(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.removeActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFragmentNavigator != null) {
            mFragmentNavigator.saveFragmentState(outState);
        }
    }

    @Override
    public void addFragment(Fragment fragment, int containerId, String tag) {
        mFragmentNavigator.addFragment(fragment, containerId, tag);
    }

    @Override
    public void showFragment(Fragment fragment, int containerId, String tag) {
        mFragmentNavigator.showFragment(fragment, containerId, tag);
    }

    @Override
    public void hideFragment(Fragment fragment) {
        mFragmentNavigator.hideFragment(fragment);
    }

    @Override
    public void hideFragment(String tag) {
        mFragmentNavigator.hideFragment(tag);
    }

    @Override
    public void detachFragment(Fragment fragment) {
        mFragmentNavigator.detachFragment(fragment);
    }

    @Override
    public void detachFragment(String tag) {
        mFragmentNavigator.detachFragment(tag);
    }

    @Override
    public <S extends Fragment> S findFragmentByTag(String tag) {
        return mFragmentNavigator.findFragmentByTag(tag);
    }

    public FragmentNavigator getNavigator() {
        return mFragmentNavigator;
    }

    public FragmentNavigator createFragmentNavigator(Bundle savedInstanceState) {
        return new MultiFragmentNavigator(getSupportFragmentManager(), savedInstanceState);
    }
}
