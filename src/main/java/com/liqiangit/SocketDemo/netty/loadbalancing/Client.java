package com.liqiangit.SocketDemo.netty.loadbalancing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.liqiangit.SocketDemo.Tool;

public class Client {

	public static void main(String[] args) {

		Socket socket = null;

		try {
			socket = new Socket("127.0.0.1", 65535);
			System.out.println("客户端开始连接");
			read(socket);
			// 一直读取控制台
			PrintWriter write = new PrintWriter(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				// 包体
				// 包头,固定4个字节,包含包体长度信息
				// byte [] content = br.readLine().getBytes();
				// byte [] head = Tool.intToByteArray1(content.length);
				// BufferedOutputStream bis = new
				// BufferedOutputStream(socket.getOutputStream());
				// bis.write(head);
				// bis.flush();
				// bis.write(content);
				// bis.flush();
				write.println(br.readLine());
				write.flush();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	private static void read(Socket socket) throws IOException {
		final BufferedInputStream stream = new BufferedInputStream(socket.getInputStream());  
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while (true) {
						read(stream);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private void read(final BufferedInputStream stream) throws IOException {
				byte[] head = new byte[4];
				stream.read(head);
				byte[] data = new byte[Tool.byteArrayToInt(head)];
				stream.read(data);
				System.out.println(new String(data).trim());
			}
		}).start();
	}	
}