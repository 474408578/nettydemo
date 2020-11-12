package com.xschen.nettydemo.im.ch07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author xschen
 *
 * todo: ByteBuf的相关API
 * 容量API：
 *      capacity()
 *      maxCapacity()
 *      readableBytes()
 *      isReadable()
 *      writableBytes()
 *      isWritable()
 *      maxWritableBytes()
 *
 * 读写指针相关的API:
 *      readerIndex() // 返回当前的读指针
 *      readerIndex(int) // 设置读指针
 *      writerIndex() // 返回当前写指针
 *      writerIndex(int) // 设置写指针
 *
 *      markReaderIndex()  // 保存当前读指针
 *      resetReaderIndex() // 把当前读指针恢复到之前保存的值
 *
 *      markWriterIndex()
 *      resetWriteIndex()
 *
 * 读写API:
 *      writeBytes(byte[] src) // 将字节数组src的数据写到ByteBuf
 *      buffer.readBytes(byte[] dst) // 把ByteBuf中的数据全部读到dst
 *
 *      writeByte(byte b) // 往ByteBuf中写一个字节
 *      buffer.readByte() // 读一个字节
 *
 *      Netty 的 ByteBuf 是通过引用计数的方式管理的，如果一个 ByteBuf 没有地方被引用到，需要回收底层内存。
 *      默认情况下，当创建完一个 ByteBuf，它的引用为1，然后每次调用 retain() 方法， 它的引用就加一，
 *      release() 方法原理是将引用计数减一，减完之后如果发现引用计数为0，则直接回收 ByteBuf 底层的内存。
 *
 *      release() // 引用计数减1
 *      retain() // 引用计数加1
 *
 *      slice()
 *      duplicate()
 *      copy()
 *
 *      retainedSlice() // 截取内存片段的同时，增加内存的引用计数
 *      retainedDuplicate()
 *
 *
 */


public class ByteBufTest {
    public static void main(String[] args) {
        /**
         * capacity
         * maxCapacity
         */
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate ByteBuf(9, 100)", buffer);

        // writeBytes方法改变了写指针，写完了之后写指针未指到capacity的时候，buffer任然是可写的
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1, 2, 3, 4)", buffer);

        // writeInt 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // writeBytes 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // writeBytes 方法改变写指针，写的时候发现buffer不可写，但是还未到达maxCapacity, 于是扩容
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get方法不影响读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getBytes()", buffer);

        // set方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // readBytes 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ==============" + action + "===============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable()： " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}
