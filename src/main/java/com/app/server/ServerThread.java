package com.app.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final Socket socket;
    private String threadID;
    private final ServerMain server;

    public ServerThread(ObjectOutputStream oos, ObjectInputStream ois, Socket socket, String id, ServerMain server) {
        this.oos = oos;
        this.ois = ois;
        this.socket = socket;
        this.server = server;
        this.threadID = id;
    }


    public void setThreadID(String threadID) {
        // Giữ nguyên thread cũ nhưng đổi tên key trong hashtable.
        server.getListThread().put(threadID, this);
        server.getListThread().remove(this.threadID);
        this.threadID = threadID;
        server.getListAccount().get(threadID).setOnline(true);// Cập nhật trạng thái online
    }

    private void sendData(String[] data) {
        //Gửi dữ liệu tới client đang kết nối tới nó.
        try {
            oos.writeObject(data);
            oos.flush();
        } catch (IOException ex) {

        }
    }

    private void sendTo(String[] data, String receiver) {
        //Gửi dữ liệu tới client cần gửi.
        server.getListThread().get(receiver).sendData(data);
    }

    private void sendAll(String[] data) {
        //Gửi dữ liệu tới tất cả các client trong hệ thống.
        server.getListThread().values().forEach((client) -> {
            client.sendData(data);
        });
    }

    private void processRegisterRequest(String[] request) {
        // Gọi phương thức xử lý đăng ký  và gửi lại kết quả cho client
        String checkReg = (server.checkRegister(request)) ? "ok" : "fail";
        sendData(new String[]{"register-result", checkReg});
        sendAll(new String[]{"new-register", request[2]});
    }

    private void processLoginRequest(String[] request) {
        // Gọi phương thức xử lý đăng nhập và trả kết quả lại cho client
        sendData(new String[]{"login-result", server.checkLogin(request), request[1]});
    }

    private void sendClientInfo() {
        // Khi 1 client online sẽ yêu cầu server gửi hết thông tin của các
        // client khác có trong hệ thống để cập nhật hiển thị.
        server.getListAccount().values().forEach((x) -> {
            sendData(new String[]{"get-all-client", x.getUsername(), x.isOnline() ? "online" : "offline"});
        });
    }

    private void processOffline(String usr) {
        server.getListAccount().get(usr).setOnline(false); // chỉnh lại trạng thái trong list account
        sendAll(new String[]{"set-offline", usr}); // gửi cho tất cả client về thằng offline
        server.getListThread().remove(usr); // loại bỏ thread quản lý client đó
    }

//    private void processOfflineMessage(String[] request) {
//        // request[1] = userName
//        // Kiểm tra xem user đó có tin nhắn offline không?
//        ListOfflineMessage ls = server.getOfflineMessage(request[1]);
//        if (ls != null) {
//            // Gửi thông tin về tin nhắn offline cho client
//            for (Map.Entry<String, OfflineMessageServer> x : ls.getListOfflineMessage().entrySet()) {
//                sendData(new String[]{"have-offline-message", x.getKey(), x.getValue().getMessageContent()});
//            }
//        }
//    }

    //    private void processSendMessage(String[] request) {
//        // Nếu người nhận online thì mới gửi tin nhắn
//        if (server.getListThread().get(request[3]) != null) {
//            sendTo(request, request[3]);
//        } else {
//            // Lưu tin nhắn offline vào server
//            server.processOfflineMessage(request[2], request[3], request[1]);
//            // Đồng thời gửi thông báo cho client kia biết là tin nhắn đã chuyển sang trạng thái offline
//            sendData(new String[]{"switch-to-offline-message", request[2], request[4]});
//        }
//
//    }

    @Override
    public void run() {
        try {
            String[] request;
            while (true) {
                // Khi có request sẽ phân loại request tương ứng và xử lý cụ thể tùy trường hợp.
                if ((request = (String[]) ois.readObject()) != null) {
                    // Xử lý đăng nhập
                    if (request[0].equals("login-request")) {
                        processLoginRequest(request);
                    }
                    // Xử lý đăng ký
                    if (request[0].equals("register-request")) {
                        processRegisterRequest(request);
                    }
                    // Khởi tạo dữ liệu sau khi đăng nhập thành công
                    if (request[0].equals("set-user-name")) {
                        setThreadID(request[1]);
                    }
                    // Lấy user name của tất cả client
                    if (request[0].equals("get-all-client")) {
                        sendClientInfo();
                    }
                    // Gửi trạng thái online cho tất cả client khác biết và cập nhật
                    if (request[0].equals("online")) {
                        sendAll((String[]) new String[]{"online", request[1]});
                    }
                    // Chuyển trạng thái offline cho client này
                    if (request[0].equals("set-offline")) {
                        processOffline(request[1]);
                    }
//                    // Xử lý gửi tin nhắn tới client khác
//                    if (request[0].equals("send-message")) {
//                        processSendMessage(request);
//                    }
//                    // Xử lý kiểm tra tin nhắn offline cho client
//                    if (request[0].equals("get-offline-message")) {
//                        processOfflineMessage(request);
//                    }
                }
                Thread.sleep(28);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException ex) {

        }
    }
}
