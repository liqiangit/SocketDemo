package com.liqiangit.SocketDemo.mytest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class client2 {  
    
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

		}).start();
	}
	private static void read(final BufferedInputStream inputStream) throws IOException {
		byte[] head = new byte[10];
		inputStream.read(head);
		byte [] data = new byte[Integer.parseInt(new String(head))];  
		inputStream.read(data);
		System.out.println(new String(data).trim());
	}

	private static void send(BufferedOutputStream outputStream, byte[] content) throws IOException {
		//包头,固定10个字节,包含包体长度信息  
		byte[] head = String.format("%010d",content.length).getBytes();
		outputStream.write(head);  
		outputStream.flush();  
		outputStream.write(content);  
		outputStream.flush();
	}  
 }  