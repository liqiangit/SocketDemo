package com.liqiangit.SocketDemo.netty2;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServerBootstrap {
	private int port;

	public NettyServerBootstrap(int port) {
		this.port = port;
		bind();
	}

	private void bind() {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss, worker);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 1024); // ������
			bootstrap.option(ChannelOption.TCP_NODELAY, true); // ���ӳ٣���Ϣ��������
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); // ������
			bootstrap.handler(new LoggingHandler(LogLevel.INFO));
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new StringEncoder())
										.addLast(new StringDecoder())
										.addLast(new NettyServerHandler());
				}
			});
			ChannelFuture f = bootstrap.bind(port).sync();
			if (f.isSuccess()) {
				System.out.println("����Netty����ɹ����˿ںţ�" + this.port);
			}
			// �ر�����
			f.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
			try {
				((Channel) boss).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				((Channel) worker).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		NettyServerBootstrap server = new NettyServerBootstrap(9998);
	}
	
}