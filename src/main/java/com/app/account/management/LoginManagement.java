package com.app.account.management;

import com.app.server.account.Account;
import com.app.constant.PathConstants;
import com.app.main.helper.AccountMapper;
import com.app.main.helper.UserInput;
import com.app.utils.FileUtils;
import com.app.utils.ReadConfigFromFile;

import java.util.ArrayList;
import java.util.List;

public class LoginManagement {

    private FileUtils fileUtils;
    private List<Account> accountList;
    private UserInput userChoice;

    public LoginManagement(UserInput userInput) {
        userChoice = userInput;
        accountList = new ArrayList<>();
        fileUtils = new FileUtils();
        fileUtils.createFile(PathConstants.DEFAULT_PATH_ACCOUNT_FILE);
        loadAccount();
    }

    private void loadAccount() {
        accountList = fileUtils.readAccountFromFile(PathConstants.DEFAULT_PATH_ACCOUNT_FILE);
    }

    public boolean login() {
        String username = userChoice.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.username"));
        String password = userChoice.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.password"));
        return validateLogin(username, password);
    }

    public boolean register() {
        String username = userChoice.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.username"));
        String password = userChoice.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.password"));
        String confirmPassword = userChoice.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.re.password"));
        String email = userChoice.getUserInput(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION, "enter.email"));
        if(validateRegister(username, password, confirmPassword)){
            Account account = new Account(username, password, email);
            accountList.add(account);
            FileUtils.writeFile(PathConstants.DEFAULT_PATH_ACCOUNT_FILE, AccountMapper.toListString(accountList));
            return true;
        }
        return false;
    }

    private boolean validateLogin(String username, String password) {
        for (Account a : accountList) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateRegister(String username, String password, String confirmPassword) {
        return checkDuplicateAccount(username) && checkPassword(password, confirmPassword);
    }

    private boolean checkDuplicateAccount(String username) {
        for (Account a : accountList) {
            if (a.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }


}
