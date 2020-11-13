package com.xschen.nettydemo.sourcecode.ch05;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author xschen
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
                                .addLast(new SampleInBoundHandler("SampleInBoundHandlerC", true));

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
