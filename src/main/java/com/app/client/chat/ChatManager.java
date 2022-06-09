package com.app.client.chat;

import java.util.HashMap;

public class ChatManager {

    private final String userName;
//    private final HomeUI homeUI;
//    private final HashMap<String, ChatUI> listChatRoom;
//    private static int numberOfflineMessage;
//    private final HashMap<String, OfflineMessage> listOfflineMessage;

    public ChatManager(String userName) {
        this.userName = userName;
//        listOfflineMessage = new HashMap<>();
//        listChatRoom = new HashMap<>();
//        homeUI = new HomeUI(this);
//        homeUI.setVisible(true);
        // Khi đăng nhập thành công sẽ gửi 4 yêu cầu lên server.
//        Connect.sendRequest(new String[]{"set-user-name", userName});
//        Connect.sendRequest(new String[]{"get-all-client", userName});
//        Connect.sendRequest(new String[]{"get-offline-message", userName});
//        Connect.sendRequest(new String[]{"online", userName});
    }

//    public HashMap<String, OfflineMessage> getListOfflineMessage() {
//        return listOfflineMessage;
//    }

    public String getUserName() {
        return userName;
    }

    public void addAllClient(String usrName, String status) {
        // Nếu không phải là tên của client đó thì mới thêm vào danh sách
        if (!usrName.equals(this.userName)) {
//            homeUI.addUserData(usrName, status);
        }
    }

    public void setOffline(String usrName) {
//        int idx = homeUI.getUserNameIndex(usrName);
//        if (idx > -1) { // chuyển trạng thái từ online sang offline
//            homeUI.removeUserName(idx);
//            homeUI.addUserData(usrName, "offline");
//        }
    }

    public void updateUserOnline(String usrName) {
//        int idx = homeUI.getUserNameIndex(usrName);
//        if (idx > -1) {
//            String status = homeUI.getUserData(idx, 1);
//            if (status.equals("offline")) {
//                homeUI.removeUserName(idx);
//                homeUI.addUserData(usrName, "online");
//            }
//            return;
//        }
//        if (!usrName.equals(this.userName)) {
//            homeUI.addUserData(usrName, "offline");
//        }
    }

    public void offlineMessageNoty(String[] data) {
        // rID = data[2];
//        listChatRoom.get(data[2]).addMessage("Your message will switch to offline message");
    }

    public void chatWith(String chatWith) {
//        if (listChatRoom.get(userName + "-" + chatWith) != null) {
//            listChatRoom.get(userName + "-" + chatWith).setVisible(true);
//        } else {
//            ChatUI chat = new ChatUI(userName, chatWith, userName + "-" + chatWith);
//            listChatRoom.put(userName + "-" + chatWith, chat);
//            chat.setVisible(true);
//        }
    }

    public void getMessage(String[] request) {
        // messageContent = request[1];
        // chatWith = request[2];
        // khi nhận được tin nhắn nếu chưa nhắn tin bao giờ.
//        if (listChatRoom.get(userName + "-" + request[2]) == null) {
//            listChatRoom.put(userName + "-" + request[2], new ChatUI(userName, request[2], userName + "-" + request[2]));
//            listChatRoom.get(userName + "-" + request[2]).setVisible(true);
//            listChatRoom.get(userName + "-" + request[2]).addMessage(request[2] + ": " + request[1]);
//        } else {
//            listChatRoom.get(userName + "-" + request[2]).setVisible(true);
//            listChatRoom.get(userName + "-" + request[2]).addMessage(request[2] + ": " + request[1]);
//        }
    }

    //Cập nhật lại số lượng tin nhắn offline mỗi lần mở của 1 người thì sẽ giảm đi
//    public void updateOfflineMessage() {
//        int total = 0;
//        for (OfflineMessage x : listOfflineMessage.values()) {
//            total += x.getNumberMessage();
//        }
//        homeUI.setTxtOffline(total);
//    }

//    public void processOfflineMessage(String[] request) {
//        // sender = request[1];
//        // allMess = request[2];
//        OfflineMessage of = new OfflineMessage(request[1], request[2]);
//        listOfflineMessage.put(request[1], of);
//        numberOfflineMessage += of.getNumberMessage();
//        homeUI.setTxtOffline(numberOfflineMessage);
//    }

}
