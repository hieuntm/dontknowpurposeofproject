package com.app.main.helper;

import com.app.constant.PathConstants;
import com.app.utils.MessageUtils;
import com.app.utils.ReadConfigFromFile;

import java.util.Scanner;

public class UserInput {

    private static Scanner scanner;

    public UserInput() {
        scanner = new Scanner(System.in);
    }

    public Integer getUserInput(int maxOption) {
        Integer userChoice = 0;
        try {
            do {
                while (!scanner.hasNextInt()) {
                    MessageUtils.displayErrorMessage(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION_INPUT, "not.a.number"));
                    scanner.next();
                }
                userChoice = scanner.nextInt();
            }while(userChoice < 0 || userChoice > (maxOption + 1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userChoice;
    }

    public String getUserInput(String text) {
        System.out.println(text);
        String userChoice = "";
        try {
                userChoice = scanner.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userChoice;
    }
}
