package com.liqiangit.SocketDemo.mytest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class client {  
    
    public static void main(String[] args) {   
   
        Socket  socket = null;  
   
        try {  
             socket = new Socket("127.0.0.1",9009);  
             System.out.println("客户端开始连接");  
             read(socket);
             //一直读取控制台  
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
             BufferedOutputStream bis = new BufferedOutputStream(socket.getOutputStream());  
            while (true){  
                //包体  
                byte [] content = br.readLine().getBytes();  
                send(bis, content);  
            }  
   
   
     } catch (Exception e) {  
         // TODO Auto-generated catch block  
         e.printStackTrace();  
     }finally {  
            try {  
                socket.close();  
            }catch (Exception e){  
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

	private static void send(BufferedOutputStream bis, byte[] content) throws IOException {
		//包头,固定4个字节,包含包体长度信息  
		byte [] head = Tool.intToByteArray1(content.length);  
		bis.write(head);  
		bis.flush();  
		bis.write(content);  
		bis.flush();
	}  
 }  