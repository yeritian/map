package com.shipmap.modules.system.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 导航栏Vo
 **/
public class NavigationBarVo {

    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuVo> menuVoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuVo> getMenuVoList() {
        return menuVoList;
    }

    public void setMenuVoList(List<MenuVo> menuVoList) {
        this.menuVoList = menuVoList;
    }
}
