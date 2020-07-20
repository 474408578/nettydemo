package com.xschen.nettydemo.jvmdemo;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xschen
 */

public class MetaSpaceOutOfMemoryDem {
    public static void main(String[] args) {
        long counter = 0;
        // 不停地创建Car类的子类
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Car.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                // 调用子类的run方法时，拦截一下
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    if (method.getName().equals("run")) {
                        System.out.println("汽车启动之前，先进行自动的安全检查……");
                        return methodProxy.invokeSuper(o, objects);
                    } else {
                        return methodProxy.invokeSuper(o, objects);
                    }
                }
            });

            Car car = (Car) enhancer.create();
            car.run();

            System.out.println("目前创建了 " + (++counter) + "个Car类的子类了");
        }
    }


    static class Car {
        public void run() {
            System.out.println("汽车启动，开始行使……");
        }
    }

    /**
     * 这个类不需要使用，用cglib就可以动态创建出来了
     */
    static class SafeCar extends Car {
        @Override
        public void run() {
            System.out.println("汽车启动，开始行使……");
            super.run();
        }
    }
}
