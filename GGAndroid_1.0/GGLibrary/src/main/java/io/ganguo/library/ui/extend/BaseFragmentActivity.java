package io.ganguo.library.ui.extend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.ganguo.library.AppManager;
import io.ganguo.library.BaseContext;
import io.ganguo.library.R;
import io.ganguo.library.core.event.BusProvider;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * Created by hulk on 10/20/14.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements IContext {
    private final Logger logger = LoggerFactory.getLogger(BaseFragmentActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始进来时的语言环境
        setLanguage(getAppContext().getLanguage());

        AppManager.getInstance().addActivity(this);
        BusProvider.getInstance().register(this);

        beforeInitView();
        initView();
        initListener();
        initData();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        // 返回通过Action
        View actionBack = findViewById(R.id.action_back);
        if (actionBack != null) {
            actionBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BusProvider.getInstance().unregister(this);
        AppManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract void beforeInitView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    /**
     * 获取上下文环境
     *
     * @return
     */
    @Override
    public <T extends BaseContext> T getAppContext() {
        return BaseContext.getInstance();
    }

    /**
     * 国际化代码
     */
    private Locale language = Locale.getDefault();

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // 当语言改变时，重新启动
        if (!isSameLanguage()) {
            restartActivity();
        }
    }

    /**
     * 重新启动当前Activity，如果界面已经打开，当语言改变时可以设置这个动作
     */
    protected void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * 显示是跟设置语言环境一样
     *
     * @return
     */
    protected boolean isSameLanguage() {
        return language == getAppContext().getLanguage();
    }

    /**
     * fragment 显示处理
     */
    private Map<Class<? extends Fragment>, Fragment> fragmentMap = new HashMap<>();
    private Fragment currentFragment;

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);

        logger.i("onSaveInstanceState " + outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);

        logger.i("onRestoreInstanceState " + savedInstanceState);
    }

    /**
     * 显示Fragment到容器中
     *
     * @param clazz
     */
    protected void showFragment(int res, final Class<? extends Fragment> clazz) {
        if (!fragmentMap.containsKey(clazz)) {
            try {
                fragmentMap.put(clazz, clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Fragment target = fragmentMap.get(clazz);
        if (currentFragment != null && target != null && currentFragment.getClass().equals(target.getClass())) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
            logger.i("hide " + currentFragment);
        }
        if (target.isAdded()) {
            if (target.isDetached()) {
                transaction.attach(currentFragment);
            } else {
                transaction.show(target);
            }
        } else {
            transaction.add(res, target);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = target;
    }

    /**
     * 让fragment重新走生命周期
     */
    protected void updateFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Class<? extends Fragment> clazz : fragmentMap.keySet()) {
            Fragment fragment = fragmentMap.get(clazz);
            if (fragment != currentFragment) {
                transaction.remove(fragment);
            }
        }
        transaction.detach(currentFragment);
        transaction.attach(currentFragment);
        transaction.commitAllowingStateLoss();
    }
}
