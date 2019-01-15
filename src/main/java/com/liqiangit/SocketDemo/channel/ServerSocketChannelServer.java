package com.liqiangit.SocketDemo.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//http://blog.csdn.net/wenhuayuzhihui/article/details/51900204
public class ServerSocketChannelServer {

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.MILLISECONDS, 
                new ArrayBlockingQueue<Runnable>(100));

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(1234));

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            //每个连接使用一个单独线程处理
            if (socketChannel != null) {
                executor.submit(new SocketChannelThread(socketChannel));
            }
        }
    }
}