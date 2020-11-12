package com.xschen.nettydemo.sourcecode.ch03;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;

/**
 * @author xschen
 * Netty服务器端启动：
 *      1、配置线程池：引导类，bossGroup，workGroup
 *      2、Channel初始化：Channel类型，注册ChannelHandler,设置Channel参数
 *      3、端口绑定
 */


public class HttpServer {
    public static void main(String[] args) throws InterruptedException {
        new HttpServer().start(8088);
    }


    public void start(int port) throws InterruptedException {
        // bossGroup负责处理Accept， workGroup负责Channel声明周期内的IO事件
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 给引导类配置两大线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    // 指定服务器端的IO模型为NIO
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 注册channelHandler，workGroup处理IO事件
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 入站：HTTP 编解码处理器、HTTPContent 压缩处理器、HTTP 消息聚合处理器、自定义业务逻辑处理器
                            // 出站：HTTPContent 压缩处理器、HTTP 编解码处理器写回客户端
                            socketChannel.pipeline()
                                    // HTTP编解码
                                    .addLast("codec", new HttpServerCodec())
                                    // httpContent 压缩
                                    .addLast("compressor", new HttpContentCompressor())
                                    // Http消息聚合
                                    .addLast("aggregator", new HttpObjectAggregator(65536))
                                    // 自定义业务逻辑处理器
                                    .addLast("handler", new HttpServerHandler());
                        }
                    })
                    // 给workGroup线程组设置TCP底层相关属性，此处表示开启TCP底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 端口绑定，bind()会真正触发启动，sync方法会阻塞，直至整个启动过程完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println("Http Server started， Listening on " + port);

            // 使线程进入wait状态，这样服务器可以一直运行如果没有这行代码，bind操作之后，会进入finally代码块，服务端退出
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
