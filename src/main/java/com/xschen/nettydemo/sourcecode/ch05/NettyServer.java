package com.xschen.nettydemo.sourcecode.ch05;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author xschen
 *
 * InBound和OutBound的传播方向是不一样的
 * Inbound 事件的传播方向为 Head -> Tail，而 Outbound 事件传播方向是 Tail -> Head
 */


public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {

                        channel.pipeline()
                                .addLast(new SampleInBoundHandler("SampleInBoundHandlerA", false))
                                .addLast(new SampleInBoundHandler("SampleInBoundHandlerB", false))
                                // 触发writeAndFlush
                                .addLast(new SampleInBoundHandler("SampleInBoundHandlerC", true));

                        // 异常处理
                        channel.pipeline()
                                .addLast(new ExceptionHandler());

                        channel.pipeline()
                                .addLast(new SampleOutBoundHandler("SampleOutBoundHandlerA"))
                                .addLast(new SampleOutBoundHandler("SampleOutBoundHandlerB"))
                                .addLast(new SampleOutBoundHandler("SampleOutBoundHandlerC"));
                    }
                });

        serverBootstrap.bind(8000);
        System.out.println("Http Server started， Listening on " + 8000);

    }
}
