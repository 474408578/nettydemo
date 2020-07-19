package com.xschen.nettydemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author xschen
 * @Date 2020/7/19 20:44
 */
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        // 接收新连接线程
        new Thread(() -> {
           while (true) {
               try {
                   // 阻塞方法获取新的连接
                   Socket socket = serverSocket.accept();

                   // 每个新的连接都创建一个线程，负责读取数据
                   new Thread(() -> {
                       try {
                           int len;
                           byte[] data = new byte[1024];
                           InputStream inputStream = socket.getInputStream();
                           while ((len = inputStream.read(data)) != -1) {
                               System.out.println(new String(data, 0, len));
                           }
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }).start();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }).start();


    }
}
