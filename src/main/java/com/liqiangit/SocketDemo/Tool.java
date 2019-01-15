package com.liqiangit.SocketDemo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Tool {
    //int 转字节数组  
    public static byte[] intToByteArray1(int i) {  
        byte[] result = new byte[4];  
        result[0] = (byte)((i >> 24) & 0xFF);  
        result[1] = (byte)((i >> 16) & 0xFF);  
        result[2] = (byte)((i >> 8) & 0xFF);  
        result[3] = (byte)(i & 0xFF);  
        return result;  
    }  
  
    public static byte[] intToByteArray2(int i) throws Exception {  
        ByteArrayOutputStream buf = new ByteArrayOutputStream();  
        DataOutputStream out = new DataOutputStream(buf);  
        out.writeInt(i);  
        byte[] b = buf.toByteArray();  
        out.close();  
        buf.close();  
        return b;  
    }  
  
    //字节数组转int  
    public static int byteArrayToInt(byte[] b) {  
        int intValue=0;  
        for(int i=0;i<b.length;i++){  
           intValue +=(b[i] & 0xFF)<<(8*(3-i));  
        }  
        return intValue;  
    }  
    public static void main(String[] args) {
    	System.out.println("0000".getBytes().length);
    	System.out.println(String.format("%010d",100));
    	System.out.println(new String("0000".getBytes()));
//		System.out.println(new String(Tool.intToByteArray1("liqiang".getBytes().length)));
	}
}
