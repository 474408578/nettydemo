package com.xschen.nettydemo;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author xschen
 * @Date 2020/7/19 20:44
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    socket.getOutputStream().write((now + ": hello world").getBytes());
                    System.out.println(now);
                    Thread.sleep(2000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
