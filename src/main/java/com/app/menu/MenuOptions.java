package com.app.menu;

import com.app.constant.PathConstants;
import com.app.utils.ReadConfigFromFile;

import java.util.ArrayList;
import java.util.List;

public class MenuOptions {

    public static List<String> getStartMenu() {
        List<String> menus = new ArrayList<>();
        menus.addAll(ReadConfigFromFile.readMenu(PathConstants.DEFAULT_PATH_MENU, "start.options"));
        return menus;
    }

    public static List<String> getLoginMenu() {
        List<String> menus = new ArrayList<>();
        menus.addAll(ReadConfigFromFile.readMenu(PathConstants.DEFAULT_PATH_MENU, "login.options"));
        return menus;
    }


}
