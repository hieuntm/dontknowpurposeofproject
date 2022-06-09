package com.app.client.connectiom;

import com.app.client.account.AccountManager;
import com.app.client.chat.ChatManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private final AccountManager accountControl;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private Thread getDataFromServerThread;
    private ChatManager chatManager;

    public Connection(AccountManager accountControl) {
        this.accountControl = accountControl;
    }

    public void setChatManager(ChatManager chat) {
        this.chatManager = chat;
    }

    public static void sendRequest(String[] request) {
        try {
            oos.writeObject(request);
            oos.flush();
        } catch (IOException ex) {
        }
    }

    public boolean connectToServer() {
        try {
            socket = new Socket("localhost", 2897);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            allowGetDataFromServer();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private void allowGetDataFromServer() {
        getDataFromServerThread = new Thread() {
            @Override
            public void run() {
                try {
                    String[] data;
                    while (true) {
                        if ((data = (String[]) ois.readObject()) != null) {
                            if (data[0].equals("register-result")) {
                                accountControl.processRegisterResult(data);
                            }
                            if (data[0].equals("login-result")) {
                                accountControl.processLoginResult(data);
                            }
                            // Xử lý những dữ liệu này khi đã đăng nhập thành công.
                            // Lúc đó thì đã khởi tạo đối tượng chatManager
                            if (chatManager != null) {
                                if (data[0].equals("new-register")) {
                                    chatManager.updateUserOnline(data[1]);
                                }
                                if (data[0].equals("get-all-client")) {
                                    chatManager.addAllClient(data[1], data[2]);
                                }
                                if (data[0].equals("online")) {
                                    chatManager.updateUserOnline(data[1]);
                                }
                                if (data[0].equals("set-offline")) {
                                    chatManager.setOffline(data[1]);
                                }
                                if (data[0].equals("send-message")) {
                                    chatManager.getMessage(data);
                                }
                                if (data[0].equals("switch-to-offline-message")) {
                                    chatManager.offlineMessageNoty(data);
                                }
//                                if (data[0].equals("have-offline-message")) {
//                                    chatManager.processOfflineMessage(data);
//                                }
                                //
                            }
                        }
                        Thread.sleep(28);
                    }
                } catch (InterruptedException | IOException | ClassNotFoundException ex) {
                    // exception
                }
            }
        };
        getDataFromServerThread.start();
    }

}
