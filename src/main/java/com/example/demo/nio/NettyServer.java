package com.example.demo.nio;

import com.example.demo.service.ServiceDemo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-03
 **/
@ChannelHandler.Sharable
@Service
@Slf4j
public class NettyServer extends SimpleChannelInboundHandler {

    @Autowired
    private ServiceDemo serviceDemo;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        final ByteBuf msg1 = (ByteBuf) msg;
        byte[] req = new byte[msg1.readableBytes()];
        msg1.readBytes(req);
        final String s = new String(req, CharsetUtil.UTF_8);
        log.info("---------服务端接收到消息：{}",s);
        ctx.writeAndFlush(this.serviceDemo.getText()+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("--------------------connect：{}",ctx.name());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.warn("--------------------close：{}",ctx.name());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("--------------------error：{}",ctx.name());
        ctx.close();
    }

}
