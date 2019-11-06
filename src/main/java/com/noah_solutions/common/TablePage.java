package com.noah_solutions.common;

import java.util.List;

public class TablePage<T> {
    private Long totalElements;
    private int page;
    private int size;
    private List<T> content;

    public TablePage() {
    }

    public TablePage( Long totalElements, int page, int size, List<T> content) {
        this.totalElements = totalElements;
        this.page = page;
        this.size = size;
        this.content = content;
    }


    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<T> getContent() {
        return content;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
