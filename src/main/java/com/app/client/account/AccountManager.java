package com.app.client.account;

import com.app.client.chat.ChatManager;
import com.app.client.connectiom.Connection;
import com.app.constant.PathConstants;
import com.app.utils.MessageUtils;
import com.app.utils.ReadConfigFromFile;

public class AccountManager {

    private final Connection conn;
//    private final LoginUI loginUI;

    public AccountManager() {
        conn = new Connection(this);
//        loginUI = new LoginUI(this);
//        loginUI.setVisible(true);
        startApp(); // gọi phương thức kết nối tới server
    }

    public void processRegisterResult(String[] result) {
        // Nhận kết quả sau khi gửi yêu cầu đăng ký tài khoản lên server
        if (result[1].equals("ok")) {
//            loginUI.displayMessage("Register ok");
//            loginUI.setFocus(0);
        } else {
//            loginUI.displayMessage("User Name is exist,try input other");
        }
    }

    public void processLoginResult(String[] result) {
        //  userName = result[2];
        //  loginResult = result[1];
        switch (result[1]) {
            case "login-success":
//                loginUI.dispose();
                conn.setChatManager(new ChatManager(result[2]));
                break;
            case "account-is-online":
//                loginUI.displayMessage("User: " + result[2] + " is online!");
                break;
            case "wrong-password":
//                loginUI.displayMessage("Wrong password!");
                break;
            default:
//                loginUI.displayMessage("User: " + result[2] + " is not exist");
                break;
        }

    }

    private void startApp() {
        if (!conn.connectToServer()) { // Kiểm tra kết nối tới server
            MessageUtils.displayErrorMessage(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION_COMMON, "can.not.connect.to.the.server"));
            System.exit(0);
        } else {
            MessageUtils.displayMessage(ReadConfigFromFile.readConfig(PathConstants.DEFAULT_PATH_NOTIFICATION_COMMON, "login.to.join.server"));
        }
    }

    public boolean checkPassword(String password, String repassword) {
        return password.equals(repassword);
    }

    public boolean checkEmpty(String data) {
        return !data.isEmpty() && !data.matches("\\s+");
    }
}
