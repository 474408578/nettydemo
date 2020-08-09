package com.xschen.nettydemo.jvmdemo;

/**
 * @author xschen
 *
 * 栈溢出的分析：
 *      GC日志、内存快照这类东西对栈内存溢出有帮助吗？
 *
 *      栈内存溢出跟堆内存是没有关系的，因为他的本质是一个线程的栈中压入了过多方法调用的栈帧。
 *      比如说几千次方法调用的几千个栈帧。
 *
 *      此时GC日志是没有用的。因为GC日志主要分析堆内存和Metaspace区域的一些GC情况的，就线程的栈内存和栈帧而言，不存在所谓的GC.
 *
 *      内存快照呢？内存快照主要是分析一些内存占用的，同样是针对堆内存和Metaspace的，所以对于线程的栈内存而言，也不需要借助这个东西。
 *
 *
 *
 *
 *
 *
 */


public class StackOutOfMemoryDemo {

    public static long counter = 0;

    public static void main(String[] args) {
        work();
    }


    public static void work() {
        System.out.println("目前是第" + (++counter) + "次方法调用");
        work();
    }

}
