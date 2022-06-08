package com.app.main.helper;

import com.app.server.account.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public static List<String> toListString(List<Account> accountList){
        List<String> result = new ArrayList<>();
        for(Account account: accountList){
            result.add(account.toString());
        }
        return result;
    }
}
