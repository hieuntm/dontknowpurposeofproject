package com.app.utils;

import com.app.constant.PathConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ReadConfigFromFile {

    public static List<String> readMenu(String path, String key) {
        List<String> options = new ArrayList<>();
        try (InputStream input = new FileInputStream(path)) {
            Properties prop = new Properties();
            prop.load(input);

            String data = prop.getProperty(key);
            String[] option = data.split(",");

            options.addAll(Arrays.asList(option));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return options;
    }

    public static String readConfig(String path, String key) {
        String result = "";
        try (InputStream input = new FileInputStream(path)) {
            Properties prop = new Properties();
            prop.load(input);

            result = prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
