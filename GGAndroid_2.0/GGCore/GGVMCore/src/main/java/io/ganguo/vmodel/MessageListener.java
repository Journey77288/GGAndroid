package io.ganguo.vmodel;

/**
 * Created by Roger on 6/20/16.
 */

public interface MessageListener {
    void onHandleMessage(int what, int code, Object obj);
}
