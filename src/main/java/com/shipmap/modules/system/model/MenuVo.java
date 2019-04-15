package com.shipmap.modules.system.model;

import java.util.List;

/**
 * 菜单Vo
 **/
public class MenuVo {
    private String name;
    private String icon;
    private String url;
    private List<MenuVo> subMenus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuVo> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<MenuVo> subMenus) {
        this.subMenus = subMenus;
    }
}

