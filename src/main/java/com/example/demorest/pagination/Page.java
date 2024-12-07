package com.example.demorest.pagination;

import java.util.Objects;

public class Page {

    private int page;
    private int pageSize;
    private long totalItem;
    private int totalPage;

    public Page(int page, int pageSize, long totalItem, int totalPage) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalItem = totalItem;
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Page page1 = (Page) o;
        return page == page1.page && pageSize == page1.pageSize && totalItem == page1.totalItem && totalPage == page1.totalPage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pageSize, totalItem, totalPage);
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", totalItem=" + totalItem +
                ", totalPage=" + totalPage +
                '}';
    }
}
