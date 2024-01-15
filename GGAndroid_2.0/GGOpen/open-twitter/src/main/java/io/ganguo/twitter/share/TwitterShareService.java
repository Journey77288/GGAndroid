package io.ganguo.twitter.share;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import io.ganguo.open.sdk.IService;
import io.ganguo.twitter.BuildConfig;
import io.ganguo.twitter.entity.TwitterShareData;
import io.ganguo.utils.util.Files;
import io.ganguo.utils.util.Strings;

/**
 * <p>
 * Twitter Share Service
 * </p>
 * Created by leo on 2018/9/30.
 */
public class TwitterShareService implements IService {
    private TwitterShareData shareData;
    private Context context;

    private TwitterShareService() {
    }


    /**
     * 单例
     *
     * @return
     */
    public static TwitterShareService get() {
        return LazyHolder.HOLDER;
    }


    /**
     * 单例 - 懒加载
     */
    public static class LazyHolder {
        private static TwitterShareService HOLDER = new TwitterShareService();
    }

    /**
     * function: 设置分享参数
     *
     * @param context
     * @param data
     * @return
     */
    public TwitterShareService applyShareData(Context context, TwitterShareData data) {
        this.shareData = data;
        this.context = context;
        return this;
    }

    /**
     * function:share twitter
     *
     * @return
     */
    @Override
    public boolean apply() {
        return share();
    }


    /**
     * function:share twitter
     *
     * @return
     */
    protected boolean share() {
        TweetComposer.Builder builder = null;
        try {
            builder = new TweetComposer
                    .Builder(context)
                    .text(shareData.getText())
                    .url(new URL(shareData.getUrl()));
            if (shareData.getFileUri() != null) {
                builder.image(shareData.getFileUri());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (builder != null) {
            builder.show();
            return true;
        }
        return false;
    }


    /**
     * function:资源释放
     *
     * @return
     */
    @Override
    public void release() {
        shareData = null;
        context = null;
    }
}
