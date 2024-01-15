package io.ganguo.http.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 分页数据
 * Created by leo on 2018/6/21.
 */
public class HttpPage {

    /**
     * currentPage : 1
     * totalElements : 3
     * totalPages : 1
     * isFirst : true
     * isLast : true
     * size : 10
     */

    @SerializedName("currentPage")
    private int currentPage;
    @SerializedName("totalElements")
    private int totalElements;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("isFirst")
    private boolean isFirst;
    @SerializedName("isLast")
    private boolean isLast;
    @SerializedName("size")
    private int size;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
