package io.ganguo.rxqiniu;


import java.io.File;

/**
 * Created by Roger on 7/28/16.
 */
public class UploadRequest {

    private File file;
    private boolean isSuccess = false;

    public UploadRequest(File file) {
        this.file = file;
    }

    public UploadRequest(String filePath) {
        this.file = new File(filePath);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "UploadRequest{" +
                "file=" + file +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
