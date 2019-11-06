package com.noah_solutions.bean;

import lombok.Data;

/**
 * 2018 12 24 lijun
 * 單位
 */
@Data
public class ProductUnit {
    private String  productUnit;
    private String  productUnitEng;

    public ProductUnit(String productUnit, String productUnitEng) {
        this.productUnit = productUnit;
        this.productUnitEng = productUnitEng;
    }
}
