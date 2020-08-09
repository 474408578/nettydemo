package com.xschen.nettydemo;

/**
 * @author xschen
 *
 * 验证码主线程中方法是否是同步阻塞调用的。
 * 方法1中启动了一个线程，并且执行一系列的逻辑，方法2中也启动一个线程，执行一系列的逻辑，
 * 测试这两个方法的线程是否是交替执行的，还是同步执行（即执行完方法1才能执行方法2）
 */


public class MultiThreadTests {

    public static void main(String[] args) {
        method1();
        method2();

    }

    public static void method1() {
        new Thread(new Task1()).start();
    }

    public static void method2() {
        new Thread(new Task2()).start();
    }


    static class Task1 implements Runnable {
        @Override
        public void run() {
            int i = 999;
            while (i > 0) {
                System.out.println(--i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Task2 implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (i < 1000) {
                System.out.println(++i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
