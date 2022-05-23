package com.example.demo.config;

import com.example.demo.nio.NettyServer;
import com.example.demo.service.IStrategyService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.nio.charset.Charset;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-03
 **/
@Slf4j
@Component
public class NettyBootStrapConfig implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {
    @Value("${netty.websocket.ip}")
    private String ip;
    @Value("${netty.websocket.port}")
    private int port;
    @Value("${netty.websocket.path}")
    private String path;
    @Autowired
    private ApplicationContext applicationContext;

    private Channel channel;

    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        log.info("netty---------------start");
        //this.start();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (this.channel != null){
            this.channel.close();
        }
        log.warn("netty  stop!");
    }

    private void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                       /* ch.pipeline().addLast("decoder", new StringEncoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast("encoder", new StringEncoder(Charset.forName("GBK")));*/
                       ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(applicationContext.getBean(NettyServer.class));
                    }
                });
        final Channel channel = bootstrap.bind(ip, port).sync().channel();
        this.channel = channel;
        channel.closeFuture().sync();
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        this.boss.shutdownGracefully().sync();
        this.work.shutdownGracefully().sync();
    }
}
