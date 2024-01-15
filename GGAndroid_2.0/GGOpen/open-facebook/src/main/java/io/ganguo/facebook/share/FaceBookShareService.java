package io.ganguo.facebook.share;


import android.app.Activity;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import io.ganguo.facebook.FaceBookAPIFactory;
import io.ganguo.facebook.callback.IFaceBookCallBack;
import io.ganguo.open.sdk.IService;

/**
 * <p>
 * Facebook分享
 * </p>
 * Created by leo on 2018/9/7.
 */
public class FaceBookShareService implements FacebookCallback<Sharer.Result>, IService {
    private IFaceBookCallBack<Sharer.Result> shareCallBack;
    private ShareLinkContent.Builder builder;
    private Activity activity;

    private FaceBookShareService() {
    }

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static FaceBookShareService get() {
        return LazyHolder.HOLDER;
    }


    public static class LazyHolder {
         static FaceBookShareService HOLDER = new FaceBookShareService();
    }

    /**
     * function：分享链接参数配置
     *
     * @param activity
     * @param builder
     */
    public FaceBookShareService applyShareLinkData(Activity activity, ShareLinkContent.Builder builder, IFaceBookCallBack shareCallBack) {
        this.activity = activity;
        this.shareCallBack = shareCallBack;
        this.builder = builder;
        return this;
    }

    /**
     * function：分享链接
     */
    @Override
    public boolean apply() {
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.registerCallback(FaceBookAPIFactory.get().getCallbackManager(), this);
        ShareLinkContent linkContent = builder.build();
        shareDialog.show(linkContent);
        return true;
    }

    @Override
    public void onSuccess(Sharer.Result result) {
        if (shareCallBack == null) {
            return;
        }
        shareCallBack.onSuccess(result);
    }

    @Override
    public void onCancel() {
        if (shareCallBack == null) {
            return;
        }
        shareCallBack.onCancel();
    }

    @Override
    public void onError(FacebookException error) {
        if (shareCallBack == null) {
            return;
        }
        shareCallBack.onFailed(error);
    }

    /**
     * function：资源释放
     */
    @Override
    public void release() {
        activity = null;
        builder = null;
        shareCallBack = null;
    }

}
