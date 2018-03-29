package com.founder.zsy.founder.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by yy on 2017/6/6.
 * 底部导航所需
 */

public class TabEntity implements CustomTabEntity {
    private String title;
    private int selectedIcon;
    private int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
