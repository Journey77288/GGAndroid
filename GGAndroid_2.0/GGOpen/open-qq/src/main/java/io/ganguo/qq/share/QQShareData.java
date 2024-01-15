package io.ganguo.qq.share;

import com.tencent.connect.share.QQShare;

/**
 * 分享数据
 * Created by zoyen on 2018/7/11.
 */
public class QQShareData {

    private Builder mBuilder;

    public QQShareData(Builder builder) {
        this.mBuilder = builder;
    }


    public String getImageUrl() {
        return mBuilder.imageUrl;
    }

    public String getImagePath() {
        return mBuilder.imagePath;
    }

    public String getTitle() {
        return mBuilder.title;
    }

    public String getLink() {
        return mBuilder.link;
    }

    public int getType() {
        return mBuilder.type;
    }

    public String getSummary() {
        return mBuilder.summary;
    }

    public String getAudioUrl() {
        return mBuilder.audioUrl;
    }

    public String getAppName() {
        return mBuilder.appName;
    }

    public static class Builder {
        private String imageUrl;
        private String imagePath;
        private String title;
        private String link;
        private int type;
        private String summary;
        private String audioUrl;
        private String appName;

        public static final int SHARE_TO_QQ_TYPE_DEFAULT = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
        public static final int SHARE_TO_QQ_TYPE_AUDIO = QQShare.SHARE_TO_QQ_TYPE_AUDIO;
        public static final int SHARE_TO_QQ_TYPE_IMAGE = QQShare.SHARE_TO_QQ_TYPE_IMAGE;
        public static final int SHARE_TO_QQ_TYPE_APP = QQShare.SHARE_TO_QQ_TYPE_APP;

        public Builder(int type) {
            this.type = type;
        }


        public QQShareData.Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public QQShareData.Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public QQShareData.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public QQShareData.Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public QQShareData.Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public QQShareData.Builder setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
            return this;
        }

        public QQShareData.Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public QQShareData build() {
            return new QQShareData(this);
        }
    }

}
