package io.ganguo.incubator.bean;

/**
 * 分页参数
 * 多页（读取数据列表）时分页参数为：
 * 页码（默认为 1）：page
 * 每页记录数量（默认为 20）：per
 * 建议对分页参数显式传值，以免后期默认值可能会修改。
 * <p/>
 * Created by Tony on 12/27/14.
 */
public class PageMeta {
    /**
     * 页码
     */
    private int page = 1;

    /**
     * 每页记录数量
     */
    private int per = 20;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总结果数
     */
    private int totalResult;

    public PageMeta() {
    }

    public PageMeta(int page, int per) {
        this.page = page;
        this.per = per;
    }

    /**
     * 重置到首页
     *
     * @return page
     */
    public int reset() {
        page = 1;
        return page;
    }

    /**
     * 上一页
     *
     * @return page
     */
    public int prev() {
        page--;
        if (page < 1) {
            page = 1;
        }
        return page;
    }

    /**
     * 下一页
     *
     * @return page
     */
    public int next() {
        page++;
        return page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;

        // 总页数
        setTotalPage(totalResult / per);
    }
}
