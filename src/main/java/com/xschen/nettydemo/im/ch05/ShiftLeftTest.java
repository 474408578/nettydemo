package com.xschen.nettydemo.im.ch05;

/**
 * @author xschen
 *
 * 测试位运算左移
 */


public class ShiftLeftTest {
    public static void main(String[] args) {
        int a = 1;
        int b = 0;
        for (int i = 0; i < 5; i++) {
            // a 左移1位
            a = a << 1;
            // b等于1左移b位
            b = 1 << b;
            System.out.println("a: " + a);
            System.out.println("b: " + b);
        }
    }
}
