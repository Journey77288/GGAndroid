package io.ganguo.sina.share;

/**
 * 微博分享数据
 * Created by zoyen on 2018/7/12.
 */
public class SinaShareEntity {
    private Builder mBuilder;

    public SinaShareEntity(Builder builder) {
        this.mBuilder = builder;
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

    public String getContent() {
        return mBuilder.content;
    }

    public String getLink() {
        return mBuilder.link;
    }

    public static class Builder {
        private String imageUrl;
        private String imagePath;
        private int imageResource;
        private String title;
        private String description;
        private String content;
        private String link;

        public SinaShareEntity.Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public SinaShareEntity.Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public SinaShareEntity.Builder setImageResource(int imageResource) {
            this.imageResource = imageResource;
            return this;
        }

        public SinaShareEntity.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public SinaShareEntity.Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public SinaShareEntity.Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public SinaShareEntity.Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public SinaShareEntity build() {
            return new SinaShareEntity(this);
        }
    }
}
