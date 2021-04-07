package com.xschen.nettydemo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用 nio 读取文件
 * @author xschen
 */

@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {
        // 1. 读取文件 FileChannel
        // a. 输入输出流, b. RandomAccessFile
        File file = new File("data.txt");
        try (FileChannel channel = new FileInputStream(file).getChannel()){
            // 准备缓冲区 暂存数据
            ByteBuffer buffer = ByteBuffer.allocate(10); // 10 Byte 大小作为缓冲区
            while (true) {
                // 从 channel 读取数据，即向 buffer 写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数：{}", len);
                if (len == -1)
                    break;
                // 切换到 buffer 读模式
                buffer.flip();
                while (buffer.hasRemaining()) { // 是否还有剩余数据
                    byte b = buffer.get(); // 一次读 1 字节
                    log.debug("实际字节: {}", (char) b);
                    System.out.println();

                }
                buffer.clear(); // 切换为写模式
            }
        } catch (IOException e) {

        }
    }
}
