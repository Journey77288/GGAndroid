package io.ganguo.rxqiniu;

import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

import java.io.File;

/**
 * 七牛上传图片结果
 * Created by Roger on 5/21/16.
 */
public class UploadResult {
    private ResponseInfo responseInfo;
    private JSONObject jsonObject;
    private String imageUrl;
    private File data;
    private String key;
    private UploadRequest uploadRequest;

    public UploadResult(UploadRequest request, String imageUrl, String key, ResponseInfo responseInfo, JSONObject jsonObject) {
        this.uploadRequest = request;
        this.data = request.getFile();
        this.jsonObject = jsonObject;
        this.key = key;
        this.responseInfo = responseInfo;
        this.imageUrl = imageUrl;
    }

    public boolean isSuccess() {
        return responseInfo != null && responseInfo.isOK();
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(ResponseInfo responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public File getData() {
        return data;
    }

    public void setData(File data) {
        this.data = data;
    }

    public UploadRequest getUploadRequest() {
        return uploadRequest;
    }

    public void setUploadRequest(UploadRequest uploadRequest) {
        this.uploadRequest = uploadRequest;
    }

    @Override
    public String toString() {
        return "UploadResult{" +
                "key='" + key + '\'' +
                ", responseInfo=" + responseInfo +
                ", jsonObject=" + jsonObject +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
