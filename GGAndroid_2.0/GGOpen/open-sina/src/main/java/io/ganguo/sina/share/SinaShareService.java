package io.ganguo.sina.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;

import io.ganguo.open.sdk.IService;
import io.ganguo.open.sdk.OfficialHelper;
import io.ganguo.sina.R;
import io.ganguo.sina.SinaShareCallback;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.Tasks;

/**
 * 新浪分享
 * Created by zoyen on 2018/7/10.
 */
public class SinaShareService implements IService {
    /**
     * share bitmap size
     */
    private static int BITMAP_WIDTH = 120;
    private static int BITMAP_HEIGHT = 120;

    private WeiboMultiMessage weiboMessage;
    private WbShareHandler shareHandler;
    private boolean isCompress = false;
    private Activity activity;
    private SinaShareCallback callback;

    private SinaShareService() {
    }

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static SinaShareService get() {
        return SinaShareService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static SinaShareService HOLDER = new SinaShareService();
    }


    /**
     * function: 添加分享数据
     *
     * @param activity
     * @param entity
     * @return
     */
    public SinaShareService applyShareData(Activity activity, SinaShareCallback callback, SinaShareEntity entity) {
        this.callback = callback;
        this.activity = activity;
        getShareHandler().registerApp();
        this.weiboMessage = new WeiboMultiMessage();
        this.weiboMessage.textObject = createTextObject(entity);
        this.weiboMessage.mediaObject = createWebPageObject(entity);
        applyShareImage(activity, entity);
        return this;
    }


    /**
     * function: create TextObject
     *
     * @param entity
     * @return
     */
    protected TextObject createTextObject(SinaShareEntity entity) {
        TextObject object = new TextObject();
        object.actionUrl = entity.getLink();
        object.title = entity.getTitle();
        object.text = entity.getContent();
        return object;
    }

    /**
     * function: get WbShareHandler
     *
     * @return
     */
    public WbShareHandler getShareHandler() {
        if (activity == null) {
            return null;
        }
        if (shareHandler == null) {
            shareHandler = new WbShareHandler(activity);
        }
        return shareHandler;
    }

    /**
     * function: doResultIntent
     *
     * @param intent
     */
    public void doResultIntent(Intent intent) {
        getShareHandler().doResultIntent(intent, callback);
    }

    /**
     * function: 分享图片
     *
     * @param context
     * @param entity
     */
    protected void applyShareImage(Context context, SinaShareEntity entity) {
        if (Strings.isNotEmpty(entity.getImageUrl())) {
            isCompress = true;
            asyCompressBitmap(entity.getImageUrl());
            return;
        }
        if (Strings.isNotEmpty(entity.getImagePath())) {
            applyShareBitmap(OfficialHelper.getFileBitmap(entity.getImagePath()));
            return;
        }
        if (entity.getImageResource() != 0) {
            applyShareBitmap(BitmapFactory.decodeResource(context.getResources(), entity.getImageResource()));
        }
    }


    /**
     * function: create WebpageObject
     *
     * @param entity
     * @return
     */
    private WebpageObject createWebPageObject(SinaShareEntity entity) {
        WebpageObject object = new WebpageObject();
        object.identify = Utility.generateGUID();
        object.actionUrl = entity.getLink();
        object.defaultText = entity.getContent();
        object.title = entity.getTitle();
        object.description = entity.getDescription();
        return object;
    }


    /**
     * function: 图片异步下载压缩
     *
     * @param url
     */
    private void asyCompressBitmap(final String url) {
        Tasks.runOnThreadPool(() -> {
            try {
                Bitmap thumb = Bitmap.createScaledBitmap(OfficialHelper.GetLocalOrNetBitmap(url),
                        BITMAP_WIDTH,
                        BITMAP_HEIGHT,
                        true);//压缩Bitmap
                applyShareBitmap(thumb);
                isCompress = false;
                apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * function: 设置分享图片位图 bitmap
     *
     * @param bitmap
     */
    private void applyShareBitmap(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        imageObject.setThumbImage(bitmap);
        this.weiboMessage.mediaObject.setThumbImage(bitmap);
        this.weiboMessage.imageObject = imageObject;
    }


    /**
     * function: apply share
     *
     * @return
     */
    @Override
    public boolean apply() {
        if (isCompress) {
            return false;
        }
        getShareHandler().shareMessage(weiboMessage, false);
        return true;
    }

    /**
     * function: 资源释放
     */
    @Override
    public void release() {
        activity = null;
        weiboMessage = null;
        shareHandler = null;
    }
}
