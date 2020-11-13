package com.xschen.nettydemo.sourcecode.ch05;

import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author xschen
 */


public class SampleOutBoundHandler extends ChannelOutboundHandlerAdapter {
    private final String name;

    public SampleOutBoundHandler(String name) {
        this.name = name;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundHandler: " + name);
        super.write(ctx, msg, promise);
    }
}
