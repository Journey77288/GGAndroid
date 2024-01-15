package io.ganguo.rxqiniu;


/**
 * Created by Roger on 7/28/16.
 */
public class RxQiNiuThrowable extends Throwable {

    private UploadResult result;

    public RxQiNiuThrowable(UploadResult result) {
        this.result = result;
    }

    public UploadResult getResult() {
        return result;
    }

    public void setResult(UploadResult result) {
        this.result = result;
    }
}
