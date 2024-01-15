package io.ganguo.http.entity.dto;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <p>
 * 1、适用于甘果PHP后台API，数据规范。
 * 2、仅仅支持分页的API。
 * 3、未分页的API，直接使用GPhPHttpResponseHandler + Entity类 即可
 * <p/>
 * Created by leo on 2018/7/15.
 */
public class PagePhpDTO<T> {
    private int total;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("last_page")
    private int lastPage;
    @SerializedName("next_page_url")
    private String nextPageUrl;
    @SerializedName("prev_page_url")
    private String prevPageUrl;
    private String from;
    private String to;
    private List<T> data;

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<T> getData() {
        return data;
    }

    public boolean isLastPage() {
        return getLastPage() == getCurrentPage() || lastPage == 0;
    }

    public boolean nullData() {
        return getTotal() == 0;
    }

    @Override
    public String toString() {
        return "PagePhpDTO{" +
                "total=" + total +
                ", perPage=" + perPage +
                ", currentPage=" + currentPage +
                ", lastPage=" + lastPage +
                ", nextPageUrl='" + nextPageUrl + '\'' +
                ", prevPageUrl='" + prevPageUrl + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", data=" + data +
                '}';
    }
}
