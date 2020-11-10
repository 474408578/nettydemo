package com.xschen.nettylearn.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xschen
 *
 * 1、使用BIO模型编写一个服务器端，监听6666端口，当有客户端连接时，就启动一个线程与之通讯。
 * 2、要求使用线程池机制改善，可以连接多个客户端.
 *
 * 3、服务器端可以接收客户端发送的数据(telnet 方式即可)。
 *
 *
 * 使用telnet进行测试时，ctrl + ]后，使用send命令可以发送数据串
 */


public class BIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("服务端启动了");

        while (true) {
            System.out.println(Thread.currentThread().getName());
            Socket socket = serverSocket.accept();
            System.out.println("连接到客户端");
            executorService.submit(()-> {
                    handler(socket);
                }
            );

        }
    }

    private static void handler(Socket socket) {
        System.out.println(Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println(Thread.currentThread().getName() + "  read……");
                System.out.println();
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭与客户端的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
