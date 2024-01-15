package io.ganguo.library.ui.extend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

import io.ganguo.library.AppManager;
import io.ganguo.library.BaseContext;
import io.ganguo.library.R;
import io.ganguo.library.core.event.BusProvider;


/**
 * 基本的 Activity
 * 用于继续使用
 * <p/>
 * Created by zhihui_chen on 14-8-4.
 */
public abstract class BaseActivity extends Activity implements IContext {

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
}
