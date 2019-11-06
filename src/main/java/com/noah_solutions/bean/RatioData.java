package com.noah_solutions.bean;

/**
 * 2019 2 28 lijun
 * 購物比例
 */
public class RatioData {
    private String name;//名稱(中文)
    private String nameEng;//名稱(英文)
    private Long num;//數量
    private Double proportion;//百分比

    public RatioData() {
    }
    public RatioData(String name, Long num) {
        this.name = name;
        this.num = num;
    }
    public RatioData(String name, String nameEng, Long num, Double proportion) {
        this.name = name;
        this.nameEng = nameEng;
        this.num = num;
        this.proportion = proportion;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }
}
