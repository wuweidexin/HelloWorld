package com.chen.opensourceframework.netty.copy;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
private ByteBuf buf;
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(4); // (1)
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        buf.release(); // (1)
        buf = null;
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("收到服务端消息...");
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
