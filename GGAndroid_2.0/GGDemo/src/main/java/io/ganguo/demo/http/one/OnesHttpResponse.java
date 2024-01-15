package io.ganguo.demo.http.one;

import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * 《一个》开源API
 * 1、app：http://www.wandoujia.com/apps/one.hh.oneclient
 * 2、API文档：https://github.com/jokermonn/-Api/blob/master/ONEv3.5.0~.md
 * </p>
 * Created by leo on 2018/7/30.
 */

public class OnesHttpResponse<T> {

    /**
     * msg : success
     * res : 0
     */

    @SerializedName("msg")
    private String msg;
    @SerializedName("res")
    private int res;
    @SerializedName("data")
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public T getData() {
        return data;
    }

    public OnesHttpResponse setData(T data) {
        this.data = data;
        return this;
    }
}
