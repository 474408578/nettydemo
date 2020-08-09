package com.xschen.nettydemo;

import java.io.*;

/**
 * @author xschen
 */


public class _01_FileDemo {
    public static void main(String[] args) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("aaa.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("bbb.txt"));
        int len;
        byte[] arr = new byte[1024];
        while ((len = bis.read(arr)) != -1) {
            bos.write(arr, 0, len);
        }
        bis.close();
        bos.close();
    }
}
