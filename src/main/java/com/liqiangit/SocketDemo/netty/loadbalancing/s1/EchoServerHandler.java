package com.liqiangit.SocketDemo.netty.loadbalancing.s1;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.liqiangit.SocketDemo.Tool;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead...; received:" + msg);
//        ctx.write(msg);
//		byte[] content=UUID.randomUUID().toString().getBytes();    
//		send(ctx, content);
    }
	private void send(ChannelHandlerContext bisout,byte[] content) throws IOException {
		byte[] head;
		head = Tool.intToByteArray1(content.length);  
		bisout.write(head);  
		bisout.flush();  
		bisout.write(content);  
		bisout.flush();
	}  
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channelReadComplete..");
        ctx.writeAndFlush(Unpooled.copiedBuffer("ying da", CharsetUtil.UTF_8));
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("ying da", CharsetUtil.UTF_8));
            Thread.sleep(100);
		}

        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        //ctx.flush(); // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        //ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server occur exception:" + cause.getMessage());
        cause.printStackTrace();
        ctx.close(); // 关闭发生异常的连接
    }
}