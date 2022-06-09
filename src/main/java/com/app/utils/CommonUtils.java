package com.app.utils;

public class CommonUtils {

    public static boolean checkEmpty(String data) {
        return !data.isEmpty() && !data.matches("\\s+");
    }

    public static boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

}
