package io.ganguo.qq.share;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.lang.ref.WeakReference;

import io.ganguo.open.sdk.IService;
import io.ganguo.open.sdk.base.ICallback;
import io.ganguo.utils.util.Strings;

/**
 * QQ分享
 * Created by zoyen on 2018/7/10.
 */
public class QQShareService implements IService, IUiListener {
    private WeakReference<Activity> mActivity;
    private Bundle bundle;
    private ICallback shareCallback;
    private Tencent tencent;


    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static QQShareService get() {
        return LazyHolder.HOLDER;
    }

    public static class LazyHolder {
        static QQShareService HOLDER = new QQShareService();
    }


    /**
     * function: 分享完成
     */
    @Override
    public void onComplete(Object o) {
        if (shareCallback != null) {
            shareCallback.onSuccess();
        }
        release();
    }

    /**
     * function: 分享错误
     */
    @Override
    public void onError(UiError uiError) {
        if (shareCallback != null) {
            shareCallback.onFailed();
        }
        release();
    }

    /**
     * function: 分享取消
     */
    @Override
    public void onCancel() {
        if (shareCallback != null) {
            shareCallback.onCancel();
        }
        release();
    }


    /**
     * function：添加分享数据、上下文
     *
     * @param activity
     * @param data
     * @param shareCallback
     * @return
     */
    public QQShareService applyShareData(Activity activity, Tencent tencent, QQShareData data, ICallback shareCallback) {
        release();
        this.mActivity = new WeakReference<>(activity);
        this.shareCallback = shareCallback;
        this.bundle = createShareBundle(data);
        this.tencent = tencent;
        return this;
    }


    /**
     * function：创建share Bundle data
     *
     * @param data
     * @return
     */
    private Bundle createShareBundle(QQShareData data) {
        Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, data.getType());
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "来自" + data.getAppName() + "的分享");
        if (Strings.isNotEmpty(data.getSummary())) {
            bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, data.getSummary());
        }
        if (Strings.isNotEmpty(data.getTitle())) {
            bundle.putString(QQShare.SHARE_TO_QQ_TITLE, data.getTitle());
        }
        if (Strings.isNotEmpty(data.getImageUrl())) {
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, data.getImageUrl());
        }
        if (Strings.isNotEmpty(data.getImagePath())) {
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, data.getImagePath());
        }
        if (Strings.isNotEmpty(data.getLink())) {
            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, data.getLink());
        }
        if (Strings.isNotEmpty(data.getImagePath())) {
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, data.getImagePath());
        }
        if (Strings.isNotEmpty(data.getAudioUrl())) {
            bundle.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, data.getAudioUrl());
        }
        return bundle;
    }

    /**
     * function: 执行分享
     */
    @Override
    public boolean apply() {
        tencent.shareToQQ(mActivity.get(), bundle, this);
        return true;
    }

    /**
     * function：资源释放
     */
    public void release() {
        if (this.mActivity != null) {
            this.mActivity.clear();
            this.mActivity = null;
        }
        bundle = null;
        shareCallback = null;
    }
}
