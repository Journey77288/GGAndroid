package io.ganguo.http.entity.dto;


import io.ganguo.http.entity.HttpPage;
import io.ganguo.http.entity.response.HttpResponse;

/**
 * <p>
 * 1、适用于甘果java后台,有分页的Api。
 * <p/>
 * Created by leo on 2018/7/15.
 */
public class PageJavaDTO<T> extends HttpResponse<T> {
    private HttpPage page;

    public HttpPage getPage() {
        return page;
    }

    public PageJavaDTO setPage(HttpPage page) {
        this.page = page;
        return this;
    }

    @Override
    public String toString() {
        return "PageJavaDTO{" +
                "page=" + page +
                '}';
    }
}
