package com.xschen.nettydemo.jvmdemo;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xschen
 */

public class MetaSpaceOutOfMemoryDemo {
    public static void main(String[] args) {
        long counter = 0;
        // 不停地创建Car类的子类
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Car.class);
            // 禁用缓存，如果不禁止的话，只会生成一个
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
/**
 * Java HotSpot(TM) 64-Bit Server VM (25.221-b11) for windows-amd64 JRE (1.8.0_221-b11), built on Jul  4 2019 04:39:29 by "java_re" with MS VC++ 10.0 (VS2010)
 * Memory: 4k page, physical 33483568k(18136156k free), swap 38464304k(14352372k free)
 *
 * CommandLine是设置的jvm参数
 * CommandLine flags: -XX:CompressedClassSpaceSize=2097152 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./ -XX:InitialHeapSize=535737088 -XX:MaxHeapSize=8571793408 -XX:MaxMetaspaceSize=10485760 -XX:MaxNewSize=872415232 -XX:MaxTenuringThreshold=6 -XX:MetaspaceSize=10485760 -XX:OldPLABSize=16 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
 *
 * 内存分配失败，开始垃圾回收，新生代、整个heap的内存变化
 * 0.947: [GC (Allocation Failure) 0.949: [ParNew: 139776K->3161K(157248K), 0.0050355 secs] 139776K->3161K(506816K), 0.0073945 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 *
 * 0K->2895k表示Full gc前老年代是0K，之后升至2895K，括号外的是整个heap的大小变化
 * 1.040: [Full GC (Metadata GC Threshold) 1.041: [CMS: 0K->2895K(349568K), 0.0682837 secs] 29494K->2895K(506816K), [Metaspace: 9824K->9824K(1058816K)], 0.0685669 secs] [Times: user=0.08 sys=0.06, real=0.07 secs]
 *
 * 最后一次Full GC，会清理软引用
 * 1.109: [Full GC (Last ditch collection) 1.109: [CMS: 2895K->1915K(349568K), 0.0171581 secs] 2895K->1915K(506944K), [Metaspace: 9824K->9824K(1058816K)], 0.0172729 secs] [Times: user=0.01 sys=0.00, real=0.02 secs]
 *
 * CMS初始标记、并发标记，重新标记，并发清理
 * 1.129: [GC (CMS Initial Mark) [1 CMS-initial-mark: 1915K(349568K)] 1915K(506944K), 0.0002316 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 1.129: [CMS-concurrent-mark-start]
 * 1.151: [CMS-concurrent-mark: 0.004/0.022 secs] [Times: user=0.16 sys=0.00, real=0.02 secs]
 * 1.152: [CMS-concurrent-preclean-start]
 *
 * JVM退出时打印一下当前的内存情况
 * Heap
 *  par new generation   total 157376K, used 6967K [0x0000000600e00000, 0x000000060b8c0000, 0x0000000634e00000)
 *   eden space 139904K,   4% used [0x0000000600e00000, 0x00000006014cdd58, 0x00000006096a0000)
 *   from space 17472K,   0% used [0x00000006096a0000, 0x00000006096a0000, 0x000000060a7b0000)
 *   to   space 17472K,   0% used [0x000000060a7b0000, 0x000000060a7b0000, 0x000000060b8c0000)
 *  concurrent mark-sweep generation total 349568K, used 1915K [0x0000000634e00000, 0x000000064a360000, 0x00000007ffe00000)
 *  Metaspace       used 9852K, capacity 10186K, committed 10240K, reserved 1058816K
 *   class space    used 868K, capacity 881K, committed 896K, reserved 1048576K
 */