package com.jzh.netty.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8888));
        while (true) {
            Socket s = serverSocket.accept();

            new Thread(() -> {
                handle(s);
            }).start();
        }
    }

    static void handle(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            int len = socket.getInputStream().read(bytes);
            System.out.println(new String(bytes, 0, len));

            socket.getOutputStream().write(bytes, 0, len);
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
