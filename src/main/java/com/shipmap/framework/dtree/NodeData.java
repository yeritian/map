package com.shipmap.framework.dtree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JunGao
 * @create 2018-12-14 14:47
 */
public class NodeData {
    private String id;
    private String title;
    private Boolean isLast;
    private String level;
    private String parentId;
    private List<Check> checkArr = new ArrayList<>();
    private List<NodeData> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Check> getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(List<Check> checkArr) {
        this.checkArr = checkArr;
    }

    public List<NodeData> getChildren() {
        return children;
    }

    public void setChildren(List<NodeData> children) {
        this.children = children;
    }
}
