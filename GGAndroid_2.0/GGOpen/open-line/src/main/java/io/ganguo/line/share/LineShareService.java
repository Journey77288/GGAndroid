package io.ganguo.line.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.StringDef;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;

import io.ganguo.open.sdk.IService;
import io.ganguo.utils.util.AppInstalls;
import io.ganguo.utils.util.Bitmaps;
import io.ganguo.utils.util.Strings;

/**
 * Line 分享
 * Created by leo on 2018/11/8.
 */
public class LineShareService implements IService {
    //Line 路由
    public static String LINE_ROUTE = "line://msg/";
    public static String LINE_PATH = "jp.naver.line.android.activity.selectchat.SelectChatActivity";
    public static String UTF_8 = "UTF-8";

    private Builder builder;

    private LineShareService() {
    }

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static LineShareService get() {
        return LineShareService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static LineShareService HOLDER = new LineShareService();
    }

    /**
     * function：添加分享相关参数
     *
     * @param builder
     * @return
     */
    public LineShareService appShareData(Builder builder) {
        this.builder = builder;
        return this;
    }

    @Override
    public boolean apply() {
        try {
            builder.activity.startActivity(createShareTextIntent());
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void release() {
        builder = null;
    }

    /**
     * function：create line share intent
     *
     * @return
     */
    protected Intent createShareTextIntent() throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder(LINE_ROUTE);
        stringBuilder.append(builder.shareType);
        if (Strings.isNotEmpty(builder.title)) {
            stringBuilder.append(URLEncoder.encode(builder.title + "\n", UTF_8));
        }
        if (Strings.isNotEmpty(builder.content)) {
            stringBuilder.append(URLEncoder.encode(builder.content + "\n", UTF_8));
        }
        if (Strings.isNotEmpty(builder.url)) {
            stringBuilder.append(URLEncoder.encode(builder.url + "\n", UTF_8));
        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuilder.toString()));
    }


    /**
     * function：分享类型是否是文本类型
     */
    protected boolean isTextType() {
        if (Strings.isEquals(builder.shareType, LineShareType.LINE_TEXT_TYPE)) {
            return true;
        }
        return false;
    }


    /**
     * function：分享参数
     */
    public static class Builder {
        protected Activity activity;
        protected String content;
        protected String url;
        protected String title;
        @LineShareType
        protected String shareType;

        public Builder(Activity activity, @LineShareType String shareType) {
            this.activity = activity;
            this.shareType = shareType;
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

    }


    @Retention(RetentionPolicy.SOURCE)
    @StringDef(value = {LineShareType.LINE_TEXT_TYPE, LineShareType.LINE_IMAGE_TYPE})
    public @interface LineShareType {
        // TODO: 2018/11/8  暂时只支持分享文本，图片分享研究中
        String LINE_TEXT_TYPE = "text/";
        String LINE_IMAGE_TYPE = "image/";

    }

}
