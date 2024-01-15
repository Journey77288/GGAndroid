package io.ganguo.utils.helper.screen;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;

import io.ganguo.utils.util.AppBars;
import io.ganguo.utils.util.Screens;


/**
 * <a helf="https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA">实现方案相关文章</a>
 * 屏幕适配工具类
 * 1、使用时需要在AppContext中init
 * 2、BaseActivity已经引入该工具类
 * 3、布局文件设置尺寸和之前方式一样，直接使用即可。例如@dimen/dp_5,最终屏幕的显示像素结果是displayMetrics.density*dp
 * 4、java代码中需要获取dimen资源Id的参数，使用ResHelper中的方法进行获取
 */
public class ScreenAdaptorHelper {
    /**
     * ********************注:这里只是设计适配尺寸规范，这个是设计师，设计时考虑的范畴，我们一般不用管，知道计算方式即可************************************
     * 1、设计的宽度，375f表示设计尺寸为750px，以720P作为换算基准，2px=1dp（1080p则是3px=1dp），750px/2=375dp，所以375f，代表375dp，表示把屏幕宽度分为375份
     * 2、则每dp在不同机型上所占的像素点关系为：px=屏幕宽度/375dp，例如720p手机，则是720px/375dp=1.92px，表示每dp占1.92px。
     * ********************注：这里只是设计适配尺寸规范，这个是设计师，设计时考虑的范畴，我们一般不用管，知道计算方式即可***********************************
     * 3、DESIGN_WIDTH_DP直接给设计尺寸就可以，不管设计是什么尺寸，直接给量出来的宽度px即可,是360就直接给360，是1080就直接给1080，反正输出的设计尺寸是多少就给多少。
     */
    public static final float DESIGN_WIDTH_DP = 375f;
    /**
     * 设计高度，去除了statusBar的高度,计算方式同宽度一样，一般直接用宽度即可
     */
    public static final float DESIGN_HEIGHT_DP = 667f;
    /**
     * 适配基准模式，宽度或高度二选一，一般直接以设计宽度为基准来做适配
     */
    private static final StandardMode DESIGN_STANDARD_MODE = StandardMode.DESIGN_WIDTH;
    private static DisplayMetrics displayMetrics;
    private static Context context;
    private static ScreenAdaptorHelper INSTANCE = new ScreenAdaptorHelper();
    /**
     * 像素密度，也就是1dp在某个尺寸的手机上所占的像素，按设计尺寸375dp，手机尺寸720x1280px，则density=720/375=1.92
     */
    private static float density;
    /**
     * 字体的缩放密度
     */
    private static float scaledDensity;
    /**
     * 状态栏高度
     */
    private static int statusBarHeight;

    private ScreenAdaptorHelper() {
    }

    public static ScreenAdaptorHelper get() {
        return INSTANCE;
    }


    /**
     * 获取一些基本配置
     *
     * @param baseContext 上下文引用
     */
    public void init(Context baseContext) {
        if (baseContext == null) {
            return;
        }
        context = baseContext;
        displayMetrics = Screens.getDisplayMetrics(baseContext);
        statusBarHeight = AppBars.getStatusBarHeight(baseContext);
        density = displayMetrics.density;
        scaledDensity = displayMetrics.scaledDensity;
    }

    /**
     * 注册Application生命周期回调
     *
     * @param application
     */
    public void registerComponentCallbacks(Application application) {
        if (application == null) {
            return;
        }
        application.registerComponentCallbacks(new ComponentLifeCallback() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                //字体改变后,将appScaledDensity重新赋值
                if (newConfig == null && newConfig.fontScale <= 0) {
                    return;
                }
                scaledDensity = Screens.getScaledDensity(context);
            }
        });
    }

    /**
     * 绑定屏幕适配
     *
     * @param context 上下文
     */
    public Context onBindScreenAdapter(Context context) {
        return onBindScreenAdapter(context, DESIGN_STANDARD_MODE, DESIGN_WIDTH_DP);
    }

    /**
     * 绑定屏幕适配
     *
     * @param context      上下文
     * @param mode         适配基准模式
     * @param designDpSize 适配设计基准尺寸，mode=MODE_WIDTH时，以720p手机作为适配基准，2px=1dp,则取:standardSize=设计宽度/2
     */
    public Context onBindScreenAdapter(Context context, StandardMode mode, float designDpSize) {
        return updateDisplayMetrics(context, mode, designDpSize);
    }


    /**
     * @param context      上下文
     * @param mode         适配基准模式
     * @param designDpSize 适配设计基准尺寸，mode=MODE_WIDTH时，以720p手机作为适配基准，2px=1dp,则取:standardSize=设计宽度/2
     */
    private Context updateDisplayMetrics(@Nullable Context context, StandardMode mode, float designDpSize) {
        //基准像素密度
        float standardDensity;
        if (mode == StandardMode.DESIGN_HEIGHT) {
            //以设计宽度作为适配
            standardDensity = (displayMetrics.heightPixels - statusBarHeight) / designDpSize;
        } else {
            //以设计宽度作为适配
            standardDensity = displayMetrics.widthPixels / designDpSize;
        }
        //计算出适配尺寸的dpi（注意和dip不是同一个概念），例如设计尺寸是以720p来换算的，2px=1dp，则Density=2，dpi=2x160=320dpi
        //实际的换算结果，只需要记住布局中xml的dp * standardDensity就是所占屏幕的px尺寸
        int standardDensityDpi = (int) (160 * standardDensity);
        //更新Configuration
        Context newContext;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            newContext = updateDisplayMetricsNougatAbove(context, standardDensityDpi);
        } else {
            newContext = setDisplayMetricsNougatbelow(context, standardDensity, standardDensityDpi);
        }
        return newContext;
    }

    /**
     * Android Nougat及以上版本更新DisplayMetrics配置
     *
     * @param context            上下文
     * @param standardDensityDpi 适配尺寸的dpi
     */
    private Context updateDisplayMetricsNougatAbove(Context context, int standardDensityDpi) {
        //更新Configuration
        Configuration configuration = context.getResources().getConfiguration();
        //configuration.densityDpi赋值后，会自动计算density、scaledDensity等值
        configuration.densityDpi = standardDensityDpi;
        return context.createConfigurationContext(configuration);
    }

    /**
     * Android Nougat及以下版本更新DisplayMetrics配置
     *
     * @param context            上下文
     * @param standardDensity    基准像素密度
     * @param standardDensityDpi 适配尺寸的dpi
     */
    private Context setDisplayMetricsNougatbelow(Context context, float standardDensity, int standardDensityDpi) {
        //根据换算出的屏幕基准像素密度，换算得到字体基准像素密度
        float standardScaledDensity = standardDensity * (scaledDensity / density);
        //将计算过的standardDensity、standardScaledDensity以及standardDensityDpi的值，赋值给系统
        DisplayMetrics currentDisplayMetrics = context.getResources().getDisplayMetrics();
        currentDisplayMetrics.density = standardDensity;
        currentDisplayMetrics.scaledDensity = standardScaledDensity;
        currentDisplayMetrics.densityDpi = standardDensityDpi;
        return context;
    }

}
