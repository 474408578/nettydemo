package com.xschen.nettydemo.sourcecode.ch05;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xschen
 */


public class SampleInBoundHandler extends ChannelInboundHandlerAdapter {

    private final String name;
    private final boolean flush;

    public SampleInBoundHandler(String name, boolean flush) {
        this.name = name;
        this.flush = flush;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandler: " + name);
        if (flush) {
//            ctx.channel().writeAndFlush(msg);
            throw new RuntimeException("InBoundHandler: " + name);
        } else {
            super.channelRead(ctx, msg);
        }
    }

    /**
     * ctx.fireExceptionCaught 会将异常按顺序从 Head 节点传播到 Tail 节点。如果用户没有对异常进行拦截处理，最后将由 Tail 节点统一处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.out.println("InBoundHandlerException: " + name);
//        ctx.fireExceptionCaught(cause);
//    }
}
