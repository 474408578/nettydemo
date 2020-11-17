package com.xschen.nettydemo.sourcecode.ch05;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xschen
 * 统一异常处理
 */


public class ExceptionHandler extends ChannelDuplexHandler {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof RuntimeException) {
            System.out.println("Handle Business Exception Success");
        }
    }
}
