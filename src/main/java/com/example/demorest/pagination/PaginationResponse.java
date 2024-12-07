package com.example.demorest.pagination;

import java.util.List;
import java.util.Objects;

public class PaginationResponse<T> {

    private List<T> item;

    private Page page;

    public PaginationResponse(List<T> item, Page page) {
        this.item = item;
        this.page = page;
    }

    public List<T> getItem() {
        return item;
    }

    public void setItem(List<T> item) {
        this.item = item;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaginationResponse<?> that = (PaginationResponse<?>) o;
        return Objects.equals(item, that.item) && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, page);
    }

    @Override
    public String toString() {
        return "PaginationResponse{" +
                "item=" + item +
                ", page=" + page +
                '}';
    }
}
