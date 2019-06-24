package cn.crap.dto;

import cn.crap.model.Menu;

import java.io.Serializable;
import java.util.List;

public class MenuWithSubMenuDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Menu menu;
    private List<Menu> subMenu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
    }

}