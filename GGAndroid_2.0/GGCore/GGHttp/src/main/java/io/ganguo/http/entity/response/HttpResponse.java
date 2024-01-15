package io.ganguo.http.entity.response;


/**
 * 根据 Jsend 规范
 * link:(http://labs.omniti.com/labs/jsend)
 * <p/>
 * Created by hulk on 25/3/2016.
 */
public class HttpResponse<T> extends BHttpResponse {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "data=" + data +
                '}';
    }
}
