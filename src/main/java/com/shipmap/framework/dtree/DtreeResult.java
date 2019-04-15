package com.shipmap.framework.dtree;


import java.util.ArrayList;
import java.util.List;

/**
 * @author JunGao
 * @create 2018-12-14 14:42
 */
public class DtreeResult {

    private Status status;
    private List<NodeData> data = new ArrayList<>();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<NodeData> getData() {
        return data;
    }

    public void setData(List<NodeData> data) {
        this.data = data;
    }
}
