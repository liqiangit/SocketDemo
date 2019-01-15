package com.liqiangit.SocketDemo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class server {  
	static Set<Socket> clients=new HashSet<Socket>();
    public static void main(String[] args) {   
        try {  
            ServerSocket serverSocket = new ServerSocket(9999);  
            System.out.println("服务器端--开始监听");  
  
            while(true){  
                Socket socket  = serverSocket.accept();  
                clients.add(socket);
                ServerHandel hm = new ServerHandel(socket);  
                Thread t = new Thread(hm);  
                t.start();  
            }  
  
            //1.socket 的输入输出流任意一个关闭，则socket都不可再用了，所以要关闭就一起关闭了。  
            //2.socket 流的读是阻塞的，A不要输入流关闭前时，要考虑B端的输出流是否还需要写。否者，B端一直等待A端接收，而A端却接受不了，B一直阻塞,在长连接中尤其要注意，注意流的结束标志  
            //3.io 流最后一定要关闭，不然会一直占用内存，可能程序会崩溃。文件输出也可能没有任何信息  
            //4.字符输出推荐使用printWriter 流,它也可以直接对文件操作，它有一个参数，设置为true 可以自动刷新，强制从缓冲中写出数据  
            //5.缓冲流 都有 bufw.newLine();方法，添加换行  
            //6.输入流 是指从什么地方读取/输出流是指输出到什么地方  
            //7.OutputStreamWriter和InputStreamReader是转换流,把字节流转换成字符流  
            //8.Scanner 取得输入的依据是空格符,包括空格键,Tab键和Enter键,任意一个按下就会返回下一个输入，如果需要包括空格之类的则用bufferreader来获取输入  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
}  