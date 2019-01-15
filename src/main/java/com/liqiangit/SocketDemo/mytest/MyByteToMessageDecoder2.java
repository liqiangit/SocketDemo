package com.liqiangit.SocketDemo.mytest;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author 李强
 *
 */
public class MyByteToMessageDecoder2 extends ByteToMessageDecoder {
	/**
	 * 相当于<br>
	 * .addLast(new FixedLengthFrameDecoder(4))<br>
	 * .addLast(new StringDecoder())
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] header = new byte[10];// 一次读取10个字节
		in.readBytes(header);
		byte[] body = new byte[Integer.parseInt(new String(header))];
		in.readBytes(body);
		String data = new String(body, "utf-8").trim();
		out.add(data);
	}
}
