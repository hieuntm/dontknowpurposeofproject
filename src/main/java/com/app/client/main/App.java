package com.app.client.main;

import com.app.client.account.AccountManager;
import com.app.client.connectiom.Connection;
import com.app.constant.PathConstants;
import com.app.helper.UserInput;
import com.app.menu.LoginMenu;
import com.app.menu.Menu;
import com.app.menu.MenuOptions;
import com.app.utils.MessageUtils;
import com.app.utils.ReadConfigFromFile;

import static com.app.utils.CommonUtils.checkEmpty;
import static com.app.utils.CommonUtils.checkPassword;

public class App {

    private Menu menu;
    private UserInput userInput;
    private AccountManager accountControl;

    public App() {
        userInput = new UserInput();
    }

    public void start() {
        accountControl = new AccountManager();
        showMenu();
    }

//    private

    public void showMenu() {
        menu = new LoginMenu(MenuOptions.getLoginMenu());
        int menuSize = menu.getSize();
        int userChoice = 0;
        boolean endChoice;
        do {
            menu.displayMenu();
            userChoice = userInput.getUserInput(menuSize);
            if (userChoice >= menu.getSize()) {
                System.exit(0);
            }
            endChoice = false;
            switch (userChoice) {
                case 1:
                    if (!handleLogin()) {
                        endChoice = true;
                    }
                    break;
                case 2:
                    if (!handleRegister()) {
                        endChoice = true;
                    }
                    break;
                case 3:
                    break;
            }
        } while ((userChoice < 0 || userChoice > (menuSize + 1)) || endChoice);


    }

    private boolean handleLogin() {
        String username = userInput.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.username"));
        String password = userInput.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.password"));
        if (checkEmpty(username) && checkEmpty(password)) {
            Connection.sendRequest(new String[]{"login-request", username, password});
        } else {
            MessageUtils.displayErrorMessage(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "login.fail"));
            return false;
        }
        return true;
    }

    private boolean handleRegister() {
        String username = userInput.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.username"));
        String password = userInput.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.password"));
        String confirmPassword = userInput.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.re.password"));
        String email = userInput.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.email"));
        if (checkEmpty(username) && checkEmpty(password) && checkPassword(password, confirmPassword)) {
            Connection.sendRequest(new String[]{"register-request", username, password, email});
        } else {
            MessageUtils.displayErrorMessage(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "register.fail"));
            return false;
        }
        return true;
    }
}
