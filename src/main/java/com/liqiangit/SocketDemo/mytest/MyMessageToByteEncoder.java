package com.liqiangit.SocketDemo.mytest;

import com.liqiangit.SocketDemo.Tool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageToByteEncoder extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		byte[] header = Tool.intToByteArray1(msg.getBytes().length);  
		byte[] body = msg.getBytes();
		out.writeBytes(header);
		out.writeBytes(body);		
	}
}
