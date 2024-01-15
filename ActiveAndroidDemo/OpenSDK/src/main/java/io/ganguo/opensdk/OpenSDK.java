package io.ganguo.opensdk;

import android.content.Context;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * 第三方分享与登录SDK
 * 审核通过后 isByPassApproval=false
 * <p/>
 * Created by Tony on 10/24/15.
 */
public class OpenSDK {

    private static Logger LOG = LoggerFactory.getLogger(OpenSDK.class);

    /**
     * 是否绕过审核，审核通过后应该设置为false
     */
    private static boolean isByPassApproval = true;

    /**
     * 是否先通过客户端授权，如果没安装客户端通过网页
     */
    private static boolean isShareByAppClient = true;

    /**
     * share sdk
     */
    public static String ShareSDK_AppKey;

    /**
     * 新浪微博
     */
    public static String SinaWeibo_AppKey;
    public static String SinaWeibo_AppSecret;
    public static String SinaWeibo_RedirectUrl;

    /**
     * 微信好友 + 朋友圈
     */
    public static String Wechat_AppId;
    public static String Wechat_AppSecret;

    /**
     * qq + qq空间
     */
    public static String QQ_AppId;
    public static String QQ_AppKey;

    public static void init(Context ctx, boolean isStage) {
        if (isStage) {
            OpenSDK.initStage(ctx);
        } else {
            OpenSDK.initProduct(ctx);
        }
    }

    /**
     * 初始化测试环境
     *
     * @param ctx
     */
    private static void initStage(Context ctx) {
        // keys
        ShareSDK_AppKey = "b791344b1ea0";

        SinaWeibo_AppKey = "568898243";
        SinaWeibo_AppSecret = "38a4f8204cc784f81f9f0daaf31e02e3";
        SinaWeibo_RedirectUrl = "http://www.sharesdk.cn";

        Wechat_AppId = "wx4868b35061f87885";
        Wechat_AppSecret = "64020361b8ec4c99936c0e3999a9f249";

        QQ_AppId = "100371282";
        QQ_AppKey = "aed9b0303e3ed1e27bae87c33761161d";

        register(ctx);

        LOG.i("initStage");
    }

    /**
     * 初始化正式环境
     *
     * @param ctx
     */
    private static void initProduct(Context ctx) {
        // keys
        ShareSDK_AppKey = "b791344b1ea0";

        SinaWeibo_AppKey = "568898243";
        SinaWeibo_AppSecret = "38a4f8204cc784f81f9f0daaf31e02e3";
        SinaWeibo_RedirectUrl = "http://www.sharesdk.cn";

        Wechat_AppId = "wx4868b35061f87885";
        Wechat_AppSecret = "64020361b8ec4c99936c0e3999a9f249";

        QQ_AppId = "100371282";
        QQ_AppKey = "aed9b0303e3ed1e27bae87c33761161d";

        register(ctx);

        LOG.i("initProduct");
    }

    /**
     * 初始化
     *
     * @param ctx
     */
    private static void register(Context ctx) {
        ShareSDK.initSDK(ctx, ShareSDK_AppKey);

        initSinaWeibo();
        initWechatAndMoments();
        initQQAndQZone();
    }

    private static void initSinaWeibo() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("Id", "1");
        hashMap.put("SortId", "1");
        hashMap.put("AppKey", SinaWeibo_AppKey);
        hashMap.put("AppSecret", SinaWeibo_AppSecret);
        hashMap.put("RedirectUrl", SinaWeibo_RedirectUrl);
        hashMap.put("ShareByAppClient", isShareByAppClient ? "true" : "false");
        hashMap.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(SinaWeibo.NAME, hashMap);
    }

    private static void initWechatAndMoments() {
        HashMap<String, Object> wechatMap = new HashMap<String, Object>();
        wechatMap.put("Id", "4");
        wechatMap.put("SortId", "4");
        wechatMap.put("AppId", Wechat_AppId);
        wechatMap.put("AppSecret", Wechat_AppSecret);
        wechatMap.put("BypassApproval", isByPassApproval ? "true" : "false");
        wechatMap.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(Wechat.NAME, wechatMap);

        HashMap<String, Object> momentsMap = new HashMap<String, Object>();
        momentsMap.put("Id", "5");
        momentsMap.put("SortId", "5");
        momentsMap.put("AppId", Wechat_AppId);
        momentsMap.put("AppSecret", Wechat_AppSecret);
        momentsMap.put("BypassApproval", isByPassApproval ? "true" : "false");
        momentsMap.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, momentsMap);
    }

    private static void initQQAndQZone() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("Id", "7");
        hashMap.put("SortId", "7");
        hashMap.put("AppId", QQ_AppId);
        hashMap.put("AppKey", QQ_AppKey);
        hashMap.put("ShareByAppClient", isShareByAppClient ? "true" : "false");
        hashMap.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(QQ.NAME, hashMap);

        HashMap<String, Object> qzoneMap = new HashMap<String, Object>();
        qzoneMap.put("Id", "3");
        qzoneMap.put("SortId", "3");
        qzoneMap.put("AppId", QQ_AppId);
        qzoneMap.put("AppKey", QQ_AppKey);
        qzoneMap.put("ShareByAppClient", isShareByAppClient ? "true" : "false");
        qzoneMap.put("Enable", "true");
        ShareSDK.setPlatformDevInfo(QZone.NAME, qzoneMap);
    }

}
