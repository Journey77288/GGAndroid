package io.ganguo.incubator;

import io.ganguo.incubator.bean.Constants;
import io.ganguo.incubator.entity.User;
import io.ganguo.incubator.util.Https;
import io.ganguo.library.BaseContext;
import io.ganguo.library.Config;
import io.ganguo.library.core.http.HttpFactory;
import io.ganguo.library.util.StringUtils;
import io.ganguo.library.util.gson.GsonUtils;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * App上下文环境
 * <p/>
 * Created by Tony on 1/4/15.
 */
public class AppContext extends BaseContext {
    private final Logger logger = LoggerFactory.getLogger(AppContext.class);
    private final AppEnvironment environment = new AppEnvironment();

    /**
     * 登录用户
     */
    private User mUser = null;

    /**
     * 获取上一文环境实例
     *
     * @return
     */
    public static AppContext getInstance() {
        return BaseContext.getInstance();
    }

    /**
     * 应用启动，只会执行一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
        logger.i("App started!");
    }

    /**
     * 获取登录用户，如果没有登录返回 null
     *
     * @return
     */
    public User getUser() {
        if (mUser == null) {
            String userStr = Config.getString(Constants.CONFIG_LOGIN_USER);
            if (StringUtils.isNotEmpty(userStr)) {
                mUser = GsonUtils.fromJson(userStr, User.class);
            }
        }
        return mUser;
    }

    /**
     * 登录成功后设置用户，持久保存到本地
     *
     * @param user
     */
    public void setUser(User user) {
        mUser = user;
        if (user != null) {
            Config.putString(Constants.CONFIG_LOGIN_USER, GsonUtils.toJson(user));
        } else {
            Config.remove(Constants.CONFIG_LOGIN_USER);
        }
    }

    /**
     * 是否已经登录
     * user != null && user.getToken() != null
     *
     * @return
     */
    public boolean isLogined() {
        return getUser() != null;
    }

    /**
     * 初始化网络配置，以及登录token
     */
    private void initHttp() {
        Https.init();
        HttpFactory.getHttpService().addHeader("token", "test");
    }
}
