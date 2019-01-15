package com.liqiangit.SocketDemo.netty2;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class NettyServerHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel c = ctx.channel();
		c.write("�Ѿ�������");
		c.flush();
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) {
		System.out.println(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}
}