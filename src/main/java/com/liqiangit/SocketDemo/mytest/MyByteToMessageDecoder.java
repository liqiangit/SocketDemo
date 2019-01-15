package com.liqiangit.SocketDemo.mytest;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author 李强
 *
 */
public class MyByteToMessageDecoder extends ByteToMessageDecoder {
	/**
	 * 相当于<br>
	 * .addLast(new FixedLengthFrameDecoder(4))<br>
	 * .addLast(new StringDecoder())
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		byte[] header = new byte[4];
		in.readBytes(header);
		byte[] data = new byte[Tool.byteArrayToInt(header)];
		in.readBytes(data);
		String str = new String(data).trim();
		out.add(str);
	}
}
