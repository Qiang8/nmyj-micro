package com.taiji.base.sample.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author scl
 *
 * @date 2018-03-06
 */
public class MenuVo{
    @Getter
    @Setter
    private String dataUrl;
    @Getter
    @Setter
    private String href;
    @Getter
    @Setter
    private String menuText;
    @Getter
    @Setter
    private String menuIconClass;
    @Getter
    @Setter
    private String badge;

    @Getter
    @Setter
    List<MenuVo> childrenMenus;

    public MenuVo(){}

    public MenuVo(String dataUrl, String href, String menuText, String menuIconClass, String badge) {
        this.dataUrl = dataUrl;
        this.href = href;
        this.menuText = menuText;
        this.menuIconClass = menuIconClass;
        this.badge = badge;
        this.childrenMenus = new ArrayList<>(0);
    }

    public void addMenu(MenuVo menuVo)
    {
        if(childrenMenus ==  null)
        {
            childrenMenus = new ArrayList<>(0);
        }

        childrenMenus.add(menuVo);
    }
}
