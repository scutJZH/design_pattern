package com.jzh.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        // 设置阻塞态为false
        channel.configureBlocking(false);

        System.out.println("start listening " + channel.getLocalAddress());
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                handle(selectionKey);
            }
        }
    }

    static void handle(SelectionKey selectionKey) {
        if (selectionKey.isAcceptable()) {
            try {
                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = channel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
                System.out.println("start open read");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isReadable()) {
            SocketChannel socketChannel = null;
            try {
                socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.clear();
                int len = socketChannel.read(byteBuffer);
                if (len != -1) {
                    System.out.println(new String(byteBuffer.array(), 0 ,len));
                }
                socketChannel.write(ByteBuffer.wrap("hello client".getBytes()));
            } catch (IOException e) {

            }
        }
    }
}
