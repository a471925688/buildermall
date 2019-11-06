package com.noah_solutions.bean;

import java.util.List;

public class PageData<T> {
    Long totalPage;
    List<T> content;

    public PageData(Long totalPage, List<T> content) {
        this.totalPage = totalPage;
        this.content = content;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
