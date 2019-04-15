package com.shipmap.modules.base.model;


import com.shipmap.framework.dtree.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JunGao
 * @create 2018-12-14 14:42
 */
public class FolderResult {

    private Status status;
    private List<Folder> data = new ArrayList<>();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Folder> getData() {
        return data;
    }

    public void setData(List<Folder> data) {
        this.data = data;
    }
}
