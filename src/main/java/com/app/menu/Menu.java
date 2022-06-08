package com.app.menu;

import java.util.List;

public class Menu implements MenuInterface {

    private List<String> menus;

    public Menu(List<String> menus) {
        this.menus = menus;
    }

    @Override
    public void displayMenu() {
        int size = menus.size();
        for (int i = 0; i < size; i++) {
            int j = i + 1;
            System.out.println(j + ". " + menus.get(i));
        }
    }

    @Override
    public int getSize() {
        return menus.size();
    }
}
