package io.ganguo.open.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.PlusShare;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;


import io.ganguo.facebook.FaceBookManager;
import io.ganguo.facebook.callback.IFaceBookCallBack;
import io.ganguo.google.GoogleManager;
import io.ganguo.google.auth.IGoogleAuthCallback;
import io.ganguo.google.shareplus.IGooglePlusShareCallback;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.line.ILineAuthCallback;
import io.ganguo.line.LineManager;
import io.ganguo.line.share.LineShareService;
import io.ganguo.open.BuildConfig;
import io.ganguo.open.R;
import io.ganguo.open.sdk.base.ICallback;
import io.ganguo.open.sdk.callback.IQQLoginCallBack;
import io.ganguo.open.sdk.callback.ISinaLoginCallBack;
import io.ganguo.open.sdk.callback.IWeChatCallBack;
import io.ganguo.qq.QQManager;
import io.ganguo.qq.share.QQShareData;
import io.ganguo.rx.RxActions;
import io.ganguo.sina.SinaManager;
import io.ganguo.sina.share.SinaShareEntity;
import io.ganguo.twitter.TwitterManager;
import io.ganguo.twitter.entity.TwitterShareData;
import io.ganguo.utils.common.ToastHelper;
import io.ganguo.utils.util.AppInstalls;
import io.ganguo.utils.util.URIs;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.item.ItemSampleVModel;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.wechat.WeChatManager;
import io.ganguo.wechat.share.WeChatShareEntity;
import io.ganguo.wechat.share.WeChatShareService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.internal.functions.Functions;

/**
 * GanGuo Open Sdk Demo
 * Created by leo on 2018/8/6.
 */
public class OpenSampleViewModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {

    /**
     * <p>
     * init header
     * </p>
     */
    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel.TitleItemViewModel("第三方分享/登录Demo"))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .build();
        ViewModelHelper.bind(container, this, headerViewModel);
    }


    @Override
    public void onViewAttached(View view) {

        //微信分享
        addViewModel(R.color.green_dark_translucent, "wechat share", () -> weChatShare());

        //微信登录
        addViewModel(R.color.green_dark_translucent, "wechat login", () -> weChatLogin());

        //QQ分享
        addViewModel(R.color.blue_dark_translucent, "qq share", () -> qqShare());

        //QQ登录
        addViewModel(R.color.blue_dark_translucent, "qq login", () -> qqLogin());

        //新浪分享
        addViewModel(R.color.red_dark_translucent, "sina share", () -> sinaShare());

        //新浪登录
        addViewModel(R.color.red_dark_translucent, "sina login", () -> sinaLogin());

        //Google认证
        addViewModel(R.color.yellow, "google auth", () -> googleAuth());

        //Google+ 分享
        addViewModel(R.color.yellow, "google+ share", () -> googlePlusShare());

        //Facebook 分享
        addViewModel(R.color.blue_light, "facebook share", () -> facebookShare());

        //Facebook 登录
        addViewModel(R.color.blue_light, "facebook login", () -> facebookLogin());

        //WhatApp Share
        addViewModel(R.color.green, "whatApp share", () -> shareToWhatApp());

        //Telegram Share
        addViewModel(R.color.blue_light, "Telegram share", () -> shareToTelegram());

        //Twitter Share
        addViewModel(R.color.blue_light, "Twitter share", () -> twitterShare());

        //Twitter 登录
        addViewModel(R.color.blue_light, "Twitter login", () -> twitterLogin());


        //Line 分享文本
        addViewModel(R.color.green_dark_translucent, "Line share text", () -> lineShareText());
        //Line 登录
        addViewModel(R.color.green_dark_translucent, "Line login", () -> lineLogin());

        toggleEmptyView();
    }


    /**
     * add ItemViewModel
     *
     * @param color
     * @param action
     * @param text
     */
    protected void addViewModel(@ColorRes int color, String text, Action action) {
        getAdapter().add(ItemSampleVModel.onCrateItemViewModel(color, text, action));
    }


    /**
     * line 分享文本内容
     */
    protected void lineShareText() {
        LineShareService.Builder builder = new LineShareService
                .Builder(getView().getActivity(), LineShareService.LineShareType.LINE_TEXT_TYPE)
                .setTitle("测试Line分享Title")
                .setUrl("https://www.baidu.com/")
                .setContent("测试Line分享内容......");
        LineManager.onShare(getView().getActivity(), builder);
    }


    /**
     * line 登录
     */
    protected void lineLogin() {
        LineManager.onAuth(getView().getActivity(), new ILineAuthCallback() {
            @Override
            public void onAuthSuccess(LineLoginResult result) {
                ToastHelper.showMessage( result.getLineProfile().getDisplayName());
            }

            @Override
            public void onAuthCancel() {
                ToastHelper.showMessage( "已取消");
            }

            @Override
            public void onAuthFailed(String errorMsg) {
                Logger.e("onAuthFailed:errorMsg:" + errorMsg);
                ToastHelper.showMessage( "认证失败");
            }
        });
    }

    /**
     * twitter 分享
     */
    protected void twitterShare() {
        RxImagePicker
                .get()
                .setChooseMode(RxImagePicker.ChooseMode.GALLERY).start(getContext())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(file -> {
                    TwitterShareData data = new TwitterShareData();
                    data
                            .setText("Twitter share test")
                            .setUrl("https://www.cnblogs.com/tangZH/p/8206569.html")
                            .setFileUri(URIs.fileToUri(getContext(), file, BuildConfig.APPLICATION_ID));
                    TwitterManager.onShare(getView().getActivity(), data);
                })
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable());
    }


    /**
     * twitter Login
     */
    protected void twitterLogin() {
        TwitterManager
                .onAuth(getView().getActivity(), new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        ToastHelper.showMessage( "twitter login success " + result.data.getUserName());
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        ToastHelper.showMessage( "twitter login failure " + exception.getMessage());
                    }
                });
    }

    /**
     * 微信分享
     */
    private void weChatShare() {
        WeChatShareEntity data = new WeChatShareEntity.Builder(WeChatShareService.WE_CHAT_WEB_PAGE_TYPE)
                .setLink("http://ganguo.io/")
                .setTitle("text")
                .setDescription("test")
                .build();
        WeChatManager.share(getView().getActivity(), data, new IWeChatCallBack() {
            @Override
            public void onCancel() {
                ToastHelper.showMessage( "WeChat Share onCancel");
            }


            @Override
            public void onSuccess(String code) {
                ToastHelper.showMessage( "WeChat Share onSuccess code==" + code);
            }

            @Override
            public void onFailed(RuntimeException t) {
                ToastHelper.showMessage( "WeChat Share onFailed " + t.getMessage());
            }

        });
    }

    /**
     * 微信登录
     */
    private void weChatLogin() {
        WeChatManager.onAuth(getView().getActivity(), new IWeChatCallBack() {
            @Override
            public void onCancel() {
                ToastHelper.showMessage( "WeChat Login onCancel");
            }


            @Override
            public void onSuccess(String code) {
                ToastHelper.showMessage( "WeChat Login onSuccess code==" + code);
            }

            @Override
            public void onFailed(RuntimeException t) {
                ToastHelper.showMessage( "WeChat Login onFailed " + t.getMessage());
            }
        });
    }

    /**
     * QQ 分享
     */
    private void qqShare() {
        QQShareData data = new QQShareData.Builder(QQShareData.Builder.SHARE_TO_QQ_TYPE_DEFAULT)
                .setLink("http://ganguo.io/")
                .setTitle("test")
                .setSummary("test test")
                .build();
        QQManager.onShare(getView().getActivity(), data, new ICallback() {
            @Override
            public void onSuccess() {
                ToastHelper.showMessage( "qq share onSuccess");
            }

            @Override
            public void onCancel() {
                ToastHelper.showMessage( "qq share onCancel");
            }

            @Override
            public void onFailed() {
                ToastHelper.showMessage( "qq share onFailed");
            }

        });
    }

    /**
     * QQ 登录
     */
    private void qqLogin() {
        QQManager.onAuth(getView().getActivity(), new IQQLoginCallBack() {

            @Override
            public void onQQLoginQSuccess(String openId, String accessToken, String expiresIn) {
                ToastHelper.showMessage( "onQQLoginSuccess");
            }


            @Override
            public void onLoginFailed(String error) {
                ToastHelper.showMessage( "onLoginFailed");
            }

            @Override
            public void onLoginCancel() {
                ToastHelper.showMessage( "onLoginCancel");
            }

        });
    }


    /**
     * 微博分享
     */
    private void sinaShare() {
        SinaShareEntity data = new SinaShareEntity.Builder()
                .setContent("Test")
                .setTitle("test")
                .setLink("http://ganguo.io/")
                .setDescription("test")
                .setImageResource(R.drawable.ic_launcher)
                .build();
        SinaManager.onShare(getView().getActivity(), data, new WbShareCallback() {
            @Override
            public void onWbShareSuccess() {
                ToastHelper.showMessage( "Weibo share onSuccess");
            }

            @Override
            public void onWbShareCancel() {
                ToastHelper.showMessage( "Weibo share onCancel");
            }

            @Override
            public void onWbShareFail() {
                ToastHelper.showMessage( "Weibo share onFailed");
            }
        });
    }

    /**
     * 微博登录
     */
    private void sinaLogin() {
        SinaManager.onAuth(getView().getActivity(), new ISinaLoginCallBack() {
            @Override
            public void onLoginFailed(String error) {
                ToastHelper.showMessage( "oginFailed");
            }

            @Override
            public void onLoginCancel() {
                ToastHelper.showMessage( "onLoginCancel");
            }

            @Override
            public void onLoginSuccess(String accessToken, String uid) {
                ToastHelper.showMessage( "loginSuccess " + accessToken);
            }
        });
    }


    /**
     * Google + 账号认证
     */
    private void googleAuth() {
        GoogleManager.onAuth(getView().getActivity(), new IGoogleAuthCallback() {
            @Override
            public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                ToastHelper.showMessage( "认证成功：" + googleSignInAccount.getEmail());
                Logger.e("googleAuth:" + "认证成功：" + googleSignInAccount.getEmail());
            }

            @Override
            public void onFailed(Throwable t) {
                Logger.e("googleAuth:onFailed:" + t.toString());
            }

        });

    }


    /**
     * Google + 分享
     */
    private void googlePlusShare() {
        PlusShare.Builder builder = new PlusShare.Builder(getView().getActivity())
                .setType("text/plain")
                .setText("Welcome to the Google+ platform.")
                .setContentUrl(Uri.parse("https://developers.google.com/+/"));
        GoogleManager.onPlusShare(getView().getActivity(), builder, new IGooglePlusShareCallback() {
            @Override
            public void onSuccess(Object o) {
                ToastHelper.showMessage( "分享成功");
            }

            @Override
            public void onFailed(Throwable t) {
                Logger.e("googlePlusShare:onFailed:" + t.toString());
            }

        });
    }

    /**
     * FaceBook 分享
     *
     * @see {@link FaceBookManager#registerActivityResultListener(int, int, Intent)}
     * <p>注意要在Activity的onActivityResult函数中注册调用以上函数，才能收到分享结果回调<P/>
     */
    private void facebookShare() {
        ShareLinkContent.Builder builder = new ShareLinkContent
                .Builder()
                .setQuote("【干货】前端开发者最常用的六款IDE")
                .setContentUrl(Uri.parse("https://www.jianshu.com/p/87a35e971b5c"));
        FaceBookManager.onShareLink(getView().getActivity(), builder, new IFaceBookCallBack<Sharer.Result>() {
            @Override
            public void onCancel() {
                ToastHelper.showMessage( "facebook 分享取消");
            }

            @Override
            public void onSuccess(Sharer.Result result) {
                ToastHelper.showMessage( "facebook 分享成功");
            }

            @Override
            public void onFailed(Throwable t) {
                ToastHelper.showMessage( "facebook 分享失败");
            }
        });
    }


    /**
     * FaceBook 登录
     *
     * @see {@link FaceBookManager#registerActivityResultListener(int, int, Intent)}
     * <p>注意要在Activity的onActivityResult函数中注册调用以上函数，才能收到分享结果回调<P/>
     */
    private void facebookLogin() {
        FaceBookManager.onAuth(getView().getActivity(), new IFaceBookCallBack<LoginResult>() {
            @Override
            public void onCancel() {
                ToastHelper.showMessage( "facebook 登录取消");
            }


            @Override
            public void onSuccess(LoginResult loginResult) {
                ToastHelper.showMessage( "facebook 登录成功");
            }

            @Override
            public void onFailed(Throwable t) {
                ToastHelper.showMessage( "facebook 登录失败");
            }
        });
    }

    /**
     * WhatApp分享，只能文本或者链接
     */
    private void shareToWhatApp() {
        if (!AppInstalls.isWhatAppInstalled(getContext())) {
            ToastHelper.showMessage( "请先安装WhatApp");
            return;
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        //Target whatsapp:
        shareIntent.setPackage("com.whatsapp");
        //Add text and then Image URI
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.baidu.com");
        getView().getActivity().startActivity(Intent.createChooser(shareIntent, "分享到WhatApp"));
    }

    /**
     * Telegram分享
     */
    public void shareToTelegram() {
        if (!AppInstalls.isTelegramInstalled(getContext())) {
            ToastHelper.showMessage( "请先安装Telegram");
            return;
        }
        Intent vIt = new Intent("android.intent.action.SEND");
        vIt.setPackage("org.telegram.messenger");
        vIt.setType("text/plain");
        vIt.putExtra(Intent.EXTRA_TEXT, "测试分享" + " " + "http://www.baidu.com");
        getView().getActivity().startActivity(vIt);
    }
}
