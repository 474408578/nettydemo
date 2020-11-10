package com.xschen.nettydemo.im.ch06.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author xschen
 */


public class NettyClient {
    private final static int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new FirstClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, 5);
    }

    public static void connect(Bootstrap bootstrap,
                               String host,
                               int port,
                               int retry) {
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                    } else if (retry == 0) {
                        System.err.println("重试次数已用完，放弃连接");
                    } else {
                        int order = MAX_RETRY - retry + 1;
                        int delay = order << 1;
                        bootstrap.config()
                                .group()
                                .schedule(() ->
                                        connect(bootstrap, host, port, retry - 1),
                                        delay,
                                        TimeUnit.SECONDS);
                    }
                });
    }
}
