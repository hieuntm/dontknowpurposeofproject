package com.app.server;

import com.app.server.account.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public final class ServerMain {
    private ServerSocket serverSocket;
    private HashMap<String, ServerThread> listThread;
    private HashMap<String, Account> listAccount;
//    private HashMap<String, ListOfflineMessage> listOfflineMessage;
    private Thread acceptNewClient;
    private static int id;

    public ServerMain() {
        // default constructor
    }

    public static void main(String[] args) {
        ServerMain server = new ServerMain();
        if (server.openServer()) { // Nếu server mở thành công thì mới chạy
            server.allowAcceptNewClient();
        }
    }

    private boolean openServer() {
        try {
            serverSocket = new ServerSocket(2897);
            listThread = new HashMap<>();
//            listOfflineMessage = new HashMap<>();
            loadAccount(); // load danh sách user có trong hệ thống
//            loadOfflineMessage(); // load tin nhắn offline của các user
            resetServer(); // Khởi tạo trạng thái tất cả client về offline

            return true;
        } catch (IOException ex) {
            return false;
        }
    }


    public synchronized HashMap<String, ServerThread> getListThread() {
        return listThread;
    }

    public HashMap<String, Account> getListAccount() {
        return listAccount;
    }

    // Khi tắt server mở lại sẽ chuyển trạng thái của các tài khoản về offline
    private synchronized void resetServer() {
        if (listAccount.size() > 0) {
            listAccount.values().forEach((x) -> {
                x.setOnline(false);
            });
            saveAccount();
        }
    }

    private void allowAcceptNewClient() {
        acceptNewClient = new Thread() {
            @Override
            public void run() {
                try { // try catch nên đặt ngoài loop (For performance!)
                    while (true) {
                        System.out.println("Waiting new client...");
                        Socket socket = serverSocket.accept();
                        System.out.println("Accected new client!");
                        addNewClient(socket);
                        Thread.sleep(456);
                    }
                } catch (IOException | InterruptedException ex) {
                    saveAccount();
                }
            }
        };
        acceptNewClient.start();
    }

    private void addNewClient(Socket socket) {
        try {
            //Bắt buộc phải đặt ObjectOutputStream ở trên ObjectInputStream!
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ServerThread svThread = new ServerThread(oos, ois, socket, "" + id, this);
            new Thread(svThread).start();
            // thêm thread vào hashtable để quản lý về sau.
            listThread.put("" + id++, svThread);
        } catch (IOException ex) {
            //
        }
    }

//    private synchronized void loadOfflineMessage() {
//        // Kiểm tra file chưa danh sách offline message có tồn tại trước khi load
//        if (new File("offline_message.data").exists()) {
//            try {
//                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("offline_message.data"))) {
//                    listOfflineMessage = (HashMap<String, ListOfflineMessage>) ois.readObject();
//                }
//            } catch (FileNotFoundException ex) {
//            } catch (IOException | ClassNotFoundException ex) {
//            }
//            return;
//        }
//        // Nếu file không tồn tại tạo mới danh sách file
//        listOfflineMessage = new HashMap<>();
//    }

//    private synchronized void saveOfflineMessage() {
//        try {
//            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("offline_message.data"))) {
//                oos.writeObject(listOfflineMessage);
//            }
//        } catch (IOException ex) {
//        }
//    }

    private synchronized void loadAccount() {
        // Kiểm tra file chưa danh sách tài khoản có tồn tại trước khi load
        if (new File("account.data").exists()) {
            try {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("account.data"))) {
                    listAccount = (HashMap<String, Account>) ois.readObject();
                }
            } catch (FileNotFoundException ex) {
                return;
            } catch (IOException | ClassNotFoundException ex) {
                return;
            }
            return;
        }
        // Nếu file không tồn tại tạo mới danh sách file
        listAccount = new HashMap<>();
    }

    private synchronized void saveAccount() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("account.data"))) {
                oos.writeObject(listAccount);
            }
        } catch (IOException ex) {
        }
    }

    public synchronized boolean checkRegister(String[] data) {
        // fullName = data[1];
        // userName = data[2];
        // password = data[3];
        if (listAccount.get(data[2]) == null) { // kiểm tra tài khoản có tồn tại ?
            listAccount.put(data[2], new Account(data[1], data[2], data[3]));
            saveAccount(); // lưu luôn vào file
            return true;
        }
        return false;
    }

    public synchronized String checkLogin(String[] data) {
        // userName = data[1];
        // password = data[2];
        Account a = listAccount.get(data[1]);
        // Kiểm tra tài khoản tồn tại
        if (a != null) {
            // Kiểm tra mật khẩu
            if (data[2].equals(a.getPassword())) {
                // Kiểm tra tài khoản đó có đang online không
                if (!a.isOnline()) {
                    listAccount.get(data[1]).setOnline(true);
                    saveAccount();
                    return "login-success";
                }
                return "account-is-online";
            }
            return "wrong-password";
        }
        return "account-not-exist";
    }

//    public synchronized void processOfflineMessage(String sender, String receiver, String messageContent) {
//        // Nếu người này chưa từng có tin nhắn offline nào
//        if (listOfflineMessage.get(receiver) == null) {
//            OfflineMessageServer offlineMess = new OfflineMessageServer();
//            offlineMess.addMessage(messageContent);
//            ListOfflineMessage listMess = new ListOfflineMessage(sender);
//            listMess.getListOfflineMessage().put(sender, offlineMess);
//            listOfflineMessage.put(receiver, listMess);
//        } // Nếu người này đã có tin nhắn offline
//        else {
//            // Nếu chưa từng nhắn tin cho nhau bao giờ
//            if (listOfflineMessage.get(receiver).getListOfflineMessage().get(sender) == null) {
//                OfflineMessageServer offlineMess = new OfflineMessageServer();
//                offlineMess.addMessage(messageContent);
//                listOfflineMessage.get(receiver).getListOfflineMessage().put(sender, offlineMess);
//            } else { // Đã từng nhắn cho nhau từ trước thì append  thêm tin nhắn vào cuối
//                listOfflineMessage.get(receiver).getListOfflineMessage().get(sender).addMessage(messageContent);
//            }
//        }
//        saveOfflineMessage();
//    }
//
//    public synchronized ListOfflineMessage getOfflineMessage(String usr) {
//        ListOfflineMessage listMess = listOfflineMessage.get(usr);
//        listOfflineMessage.remove(usr); // remove offline message
//        saveOfflineMessage();
//        return listMess;
//    }


}
