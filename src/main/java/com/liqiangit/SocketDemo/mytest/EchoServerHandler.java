package com.liqiangit.SocketDemo.mytest;

import java.io.IOException;

import com.liqiangit.SocketDemo.Tool;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@SuppressWarnings("deprecation")
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server channelRead...; received:" + msg);
		ctx.writeAndFlush(msg);
	}

	private void send(ChannelHandlerContext bisout, byte[] content) throws IOException {
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
//		ctx.writeAndFlush("应答");

		// 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
		// ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		// ctx.flush(); //
		// 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
		// ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server occur exception:" + cause.getMessage());
		cause.printStackTrace();
		ctx.close(); // 关闭发生异常的连接
	}
}