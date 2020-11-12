package com.xschen.nettydemo.im.ch08.protocol.command;

import lombok.Data;

/**
 * @author xschen
 */

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
