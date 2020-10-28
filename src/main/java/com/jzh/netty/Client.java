package com.jzh.netty;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        socket.getOutputStream().write("hello world".getBytes());
        socket.getOutputStream().flush();

        System.out.println("writing ending...");

        byte[] bytes = new byte[1024];
        int len = socket.getInputStream().read(bytes);
        System.out.println(new String(bytes, 0, len));

        socket.getOutputStream().write("hello world2".getBytes());
        socket.getOutputStream().flush();
        len = socket.getInputStream().read(bytes);
        System.out.println(new String(bytes, 0, len));
        socket.close();
    }
}
