package io.ganguo.wechat.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import io.ganguo.open.sdk.IService;
import io.ganguo.open.sdk.OfficialHelper;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.Tasks;

/**
 * 微信分享
 * Created by zoyen on 2018/7/10.
 */
public class WeChatShareService implements IService {
    /**
     * share bitmap size
     */
    private static int BITMAP_WIDTH = 120;
    private static int BITMAP_HEIGHT = 120;
    /**
     * 微信分享类型(网页，音乐，音频，视频)
     */
    public final static String WE_CHAT_TEXT_TYPE = "text";  // 文本类型
    public final static String WE_CHAT_IMAGE_TYPE = "img";  //图片类型
    public final static String WE_CHAT_MUSIC_TYPE = "music";  //视频类型
    public final static String WE_CHAT_VIDEO_TYPE = "video";  //音乐类型
    public final static String WE_CHAT_WEB_PAGE_TYPE = "webpage";  //音乐类型
    private WXMediaMessage mediaMessage;
    private SendMessageToWX.Req req;
    private WXMediaMessage.IMediaObject object;
    private IWXAPI iwxapi;
    private int mScene;
    private boolean isCompress = false;

    private WeChatShareService() {
    }

    /**
     * function：单例  - 懒汉模式
     *
     * @return
     */
    public static WeChatShareService get() {
        return WeChatShareService.LazyHolder.HOLDER;
    }


    public static class LazyHolder {
        static WeChatShareService HOLDER = new WeChatShareService();
    }

    /**
     * function: 添加分享数据
     *
     * @param shareData
     */
    public WeChatShareService applyShareData(Activity activity, IWXAPI iwxapi, WeChatShareEntity shareData) {
        this.iwxapi = iwxapi;
        this.object = createShareMediaObj(activity, shareData);
        this.req = new SendMessageToWX.Req();
        this.mediaMessage = new WXMediaMessage();
        this.mediaMessage.description = shareData.getDescription();
        this.mediaMessage.title = shareData.getTitle();
        this.mScene = SendMessageToWX.Req.WXSceneSession;
        this.req.transaction = OfficialHelper.buildTransaction(shareData.getType());
        return this;
    }


    /**
     * function: create share Object
     *
     * @param shareData
     * @return
     */
    protected WXMediaMessage.IMediaObject createShareMediaObj(Activity activity, WeChatShareEntity shareData) {
        WXMediaMessage.IMediaObject shareObject = null;
        switch (shareData.getType()) {
            case WE_CHAT_WEB_PAGE_TYPE:
                WXWebpageObject webObject = new WXWebpageObject();
                webObject.webpageUrl = shareData.getLink();
                shareObject = webObject;
                break;
            case WE_CHAT_MUSIC_TYPE:
                WXMusicObject musicObject = new WXMusicObject();
                musicObject.musicDataUrl = shareData.getMusicUrl();
                shareObject = musicObject;
                break;
            case WE_CHAT_TEXT_TYPE:
                WXTextObject wxTextObject = new WXTextObject();
                wxTextObject.text = shareData.getText();
                shareObject = wxTextObject;
                break;
            case WE_CHAT_VIDEO_TYPE:
                WXVideoObject wxVideoObject = new WXVideoObject();
                wxVideoObject.videoUrl = shareData.getVideoUrl();
                shareObject = wxVideoObject;
                break;
            case WE_CHAT_IMAGE_TYPE:
                applyShareImage(activity, shareData);
                break;
        }
        return shareObject;
    }


    /**
     * function: 分享图片
     *
     * @param activity
     * @param shareData
     */
    protected void applyShareImage(Activity activity, WeChatShareEntity shareData) {
        if (Strings.isNotEmpty(shareData.getImageUrl())) {
            isCompress = true;
            asyCompressBitmap(shareData.getImageUrl());
            return;
        }
        if (Strings.isNotEmpty(shareData.getImagePath())) {
            applyShareBitmap(OfficialHelper.getFileBitmap(shareData.getImagePath()));
            return;
        }
        if (shareData.getImageResource() != 0) {
            applyShareBitmap(BitmapFactory.decodeResource(activity.getResources(), shareData.getImageResource()));
        }
    }


    /**
     * function: 图片异步下载压缩
     *
     * @param url
     */
    protected void asyCompressBitmap(final String url) {
        Tasks.runOnThreadPool(() -> {
            try {
                Bitmap thumb = Bitmap
                        .createScaledBitmap(OfficialHelper.GetLocalOrNetBitmap(url),
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
        if (object == null) {
            WXImageObject wxImageObject = new WXImageObject(bitmap);
            object = wxImageObject;
        }
        this.mediaMessage.thumbData = OfficialHelper.bmpToByteArray(bitmap, true);
    }

    /**
     * function: apply share
     *
     * @return
     */
    @Override
    public boolean apply() {
        if (isCompress) {
            return true;
        }
        this.mediaMessage.mediaObject = object;
        this.req.message = mediaMessage;
        this.req.scene = mScene;
        this.iwxapi.sendReq(req);
        return true;
    }

    /**
     * function: 资源释放
     */
    @Override
    public void release() {
        mediaMessage = null;
        req = null;
        object = null;
    }
}
