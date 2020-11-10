package com.xschen.nettydemo.im.ch04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * @author xschen
 * todo：服务端启动流程
 */


public class NettyServer {
    private final static int BEGIN_PORT = 8000;
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .attr(AttributeKey.newInstance("serverName"), "NettyServer")
                .childAttr(clientKey, "clientValue")
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        System.out.println(channel.attr(clientKey).get());
                    }
                });

        bind(serverBootstrap, BEGIN_PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap
                .bind(port)
                .addListener(future ->  {
                        if (future.isSuccess()) {
                            System.out.println("端口[" + port + "]绑定成功");
                        } else {
                            System.out.println("端口[" + port + "]绑定失败");
                            bind(serverBootstrap, port + 1);
                        }
                    });
    }
}
