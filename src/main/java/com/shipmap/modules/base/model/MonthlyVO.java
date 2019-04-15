package com.shipmap.modules.base.model;

import java.util.List;

public class MonthlyVO {

    private Monthly monthly;
    private List<Specification> specificationList;

    @Override
    public String toString() {
        return "MonthlyVO{" +
                "monthly=" + monthly +
                ", specificationList=" + specificationList +
                '}';
    }

    public Monthly getMonthly() {
        return monthly;
    }

    public void setMonthly(Monthly monthly) {
        this.monthly = monthly;
    }

    public List<Specification> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<Specification> specificationList) {
        this.specificationList = specificationList;
    }
}
