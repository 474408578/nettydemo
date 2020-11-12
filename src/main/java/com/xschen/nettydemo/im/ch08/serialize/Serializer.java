package com.xschen.nettydemo.im.ch08.serialize;

import com.xschen.nettydemo.im.ch08.serialize.impl.JSONSerializer;

/**
 * @author xschen
 */


public interface Serializer {

    // 默认序列化算法
    Serializer DEFAULT = new JSONSerializer();


    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);


    /**
     * 二进制转换为java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
