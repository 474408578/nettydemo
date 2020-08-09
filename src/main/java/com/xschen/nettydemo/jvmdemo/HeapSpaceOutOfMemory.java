package com.xschen.nettydemo.jvmdemo;

import java.util.ArrayList;

/**
 * @author xschen
 *
 * 堆内存溢出
 *
 * 1、占用内存最多的对象是谁？
 * 2、分析那个线程的调用栈，找到是哪个方法引发的内存溢出。
 */


public class HeapSpaceOutOfMemory {
    public static void main(String[] args) {
        long counter = 0;

        ArrayList<Object> list = new ArrayList<>();
        while (true) {
            list.add(new Object());
            System.out.println("当前创建了第" + (++counter) + "个对象");
        }
    }
}
