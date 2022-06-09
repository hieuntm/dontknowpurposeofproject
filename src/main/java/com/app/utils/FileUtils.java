package com.app.utils;

import com.app.server.account.Account;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static List<Account> readAccountFromFile(String path) {
        List<Account> result = new ArrayList<>();
        Gson gson = new Gson();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Account account = gson.fromJson(data, Account.class);
                result.add(account);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
//            System.err.println("Cannot read file.");
            e.printStackTrace();

        }
        return result;
    }

    public static void createFile(String path) {
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
//                System.err.println("File created: " + myObj.getName());
            } else {
//                System.err.println("File already exists.");
            }
        } catch (IOException e) {
//            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeFile(String path, List<String> data) {
        try {
            FileWriter myWriter = new FileWriter(path);
            for (String string : data) {
                myWriter.write(string);
            }
            myWriter.close();
//            System.err.println("Successfully wrote to the file.");
        } catch (IOException e) {
//            System.err.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
