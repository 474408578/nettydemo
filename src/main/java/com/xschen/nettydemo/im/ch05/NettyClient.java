package com.xschen.nettydemo.im.ch05;

import com.sun.org.apache.bcel.internal.generic.I2F;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author xschen
 */


public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                // 绑定自定义属性到channel
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {

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
                    } else if (retry == 0){
                        System.err.println("重试次数已用完，放弃连接");
                    } else {
                        int order = (MAX_RETRY - retry) + 1;
                        int delay = 1 << order;
                        System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                        // 定时任务，bootstrap.config()会返回一个BootstrapConfig
                        bootstrap.config()
                                // bootstrap.config().group()返回之前配置的线程模型workGroup
                                .group()
                                // 调用schedule实现定时任务逻辑
                                .schedule(() -> connect(bootstrap, host, port, retry - 1),
                                        delay,
                                        TimeUnit.SECONDS);
                    }
                });
    }
}
