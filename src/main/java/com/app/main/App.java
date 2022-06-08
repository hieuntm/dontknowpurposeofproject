package com.app.main;

import com.app.account.management.LoginManagement;
import com.app.constant.PathConstants;
import com.app.main.helper.UserInput;
import com.app.menu.LoginMenu;
import com.app.menu.Menu;
import com.app.menu.MenuOptions;
import com.app.utils.MessageUtils;
import com.app.utils.ReadConfigFromFile;

public class App {

    private Menu menu;
    private LoginManagement loginManagement;
    private UserInput userInput;

    public App() {
        userInput = new UserInput();
        loginManagement = new LoginManagement(userInput);
    }

    public void start() {
        menu = new LoginMenu(MenuOptions.getLoginMenu());
        int menuSize = menu.getSize();
        int userChoice = 0;
        boolean endChoice = false;
        do {
            menu.displayMenu();
            userChoice = userInput.getUserInput(menuSize);
            if (userChoice >= menu.getSize()) {
                System.exit(0);
            }
            endChoice = false;
            switch (userChoice) {
                case 1:
                    if (loginManagement.login()) {
                        startInGame();
                    } else {
                        MessageUtils.displayErrorMessage(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "login.fail"));
                        endChoice = true;
                        continue;
                    }
                    break;
                case 2:
                    if (loginManagement.register()) {
                        registerSuccess();
                    } else {
                        MessageUtils.displayErrorMessage(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "register.fail"));
                        endChoice = true;
                    }
                    break;
                case 3:

                    break;
            }
        } while ((userChoice < 0 || userChoice > (menuSize + 1)) || endChoice);


    }

    public void startInGame() {
        System.out.println("In game");
    }

    public void registerSuccess(){
        System.out.println("Success");
    }
}
