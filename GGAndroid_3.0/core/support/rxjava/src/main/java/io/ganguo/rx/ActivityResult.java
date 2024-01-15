package io.ganguo.rx;

import android.app.Activity;
import android.content.Intent;

/**
 * ActivityResult Container
 * Created by Roger on 8/10/16.
 */
public class ActivityResult {

    private final int requestCode;
    private final int resultCode;
    private final Intent data;

    public ActivityResult(Integer requestCode, Integer resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public boolean isResultOK() {
        return resultCode == Activity.RESULT_OK;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ActivityResult{" +
                "requestCode=" + requestCode +
                ", resultCode=" + resultCode +
                ", data=" + data +
                '}';
    }
}
