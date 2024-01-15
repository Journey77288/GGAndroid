package io.ganguo.twitter.entity;

import android.app.Activity;
import android.net.Uri;

/**
 * <p>
 * Twitter Share Entity
 * </p>
 * Created by leo on 2018/9/30.
 */
public class TwitterShareData {
    private String text;
    private String url;
    private Uri fileUri;

    public TwitterShareData setText(String text) {
        this.text = text;
        return this;
    }

    public TwitterShareData setUrl(String url) {
        this.url = url;
        return this;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public TwitterShareData setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
        return this;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "TwitterShareData{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", fileUri=" + fileUri +
                '}';
    }
}
