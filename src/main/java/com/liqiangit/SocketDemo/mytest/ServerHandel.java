package com.liqiangit.SocketDemo.mytest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class ServerHandel implements Runnable {  
    public static int count = 0;  
    Socket socket = null;  
    public ServerHandel(Socket socket){  
        count++;  
        this.socket = socket;  
        System.out.println("用户"+count+"接入");  
    }  
  
    public void run() {  
        BufferedInputStream bis = null;  
        try {  
             bis = new  BufferedInputStream(socket.getInputStream());  
     		BufferedOutputStream bisout = new BufferedOutputStream(socket.getOutputStream());  
            while (true){  
                read(bis);  
        		byte[] content=UUID.randomUUID().toString().getBytes();                
                send(bisout,content);  
            }  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally {  
            try {  
                bis.close();  
                socket.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }

	private void read(BufferedInputStream bis) throws IOException {
		byte [] head = new byte[10];  
		bis.read(head);  
		byte [] data = new byte[Integer.parseInt(new String(head))];  
		bis.read(data);  
		System.out.println(new String(data).trim());
	}

	private void send(BufferedOutputStream bisout,byte[] content) throws IOException {
		byte[] head = String.format("%010d",content.length).getBytes();
		bisout.write(head);  
		bisout.flush();  
		bisout.write(content);  
		bisout.flush();
	}  
  
  
}  