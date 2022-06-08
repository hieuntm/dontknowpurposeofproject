package com.app.utils;

public class MessageUtils {

    public static void displayMessage(String text) {
        System.out.println("INFO: " + text);
    }

    public static void displayErrorMessage(String text) {
        System.err.println("SYSTEM: " + text);
    }
}
