package io.ganguo.wechat.share;

/**
 * 分享数据
 * Created by zoyen on 2018/7/11.
 */
public class WeChatShareEntity {

    private Builder mBuilder;

    public WeChatShareEntity(Builder builder) {
        this.mBuilder = builder;
    }

    public String getMusicUrl() {
        return mBuilder.musicUrl;
    }

    public String getImageUrl() {
        return mBuilder.imageUrl;
    }

    public String getImagePath() {
        return mBuilder.imagePath;
    }

    public int getImageResource() {
        return mBuilder.imageResource;
    }

    public String getTitle() {
        return mBuilder.title;
    }

    public String getDescription() {
        return mBuilder.description;
    }

    public String getText() {
        return mBuilder.text;
    }

    public String getVideoUrl() {
        return mBuilder.videoUrl;
    }

    public String getLink() {
        return mBuilder.link;
    }

    public String getType() {
        return mBuilder.type;
    }

    public static class Builder {
        private String musicUrl; //分享音乐
        private String imageUrl;
        private String imagePath;
        private int imageResource;
        private String title;
        private String description;
        private String text; //分享纯文本
        private String videoUrl; //分享视频
        private String link; //分享链接
        private String type;

        public Builder(String type) {
            this.type = type;
        }

        public WeChatShareEntity.Builder setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
            return this;
        }

        public WeChatShareEntity.Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public WeChatShareEntity.Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public WeChatShareEntity.Builder setImageResource(int imageResource) {
            this.imageResource = imageResource;
            return this;
        }

        public WeChatShareEntity.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public WeChatShareEntity.Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public WeChatShareEntity.Builder setText(String text) {
            this.text = text;
            return this;
        }

        public WeChatShareEntity.Builder setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public WeChatShareEntity.Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public WeChatShareEntity build() {
            return new WeChatShareEntity(this);
        }
    }

}
