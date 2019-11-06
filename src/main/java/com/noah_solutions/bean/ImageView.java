package com.noah_solutions.bean;

public class ImageView {
    private String src;
    private String name;


    public ImageView(String src, String name) {
        this.src = src;
        this.name = name;
    }

    public String getSrc() {
        return src;
    }



    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
