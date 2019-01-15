package com.liqiangit.SocketDemo.websocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * okhttp3.5 websocket
 */
public final class WebSocketClient extends WebSocketListener {    
//	ThreadPoolTaskExecutor
	  ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);  
	private WebSocketClient(){
		
	}
    private static WebSocketClient single=null;  
    public static synchronized WebSocketClient getInstance() {  
        if (single == null) {    
            single = new WebSocketClient();  
        }    
       return single;  
}  
	@Override
	public void onClosed(WebSocket webSocket, int code, String reason) {
		System.out.println("onClosed");
	}

//	String url = "ws://192.168.10.90:8080/websocket";
	String url="ws://localhost:8080/websocket";

	public void run() {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		WebSocket ws = client.newWebSocket(request, this);
		client.dispatcher().executorService().shutdown();
	}

	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
		WebSocketClient ws = WebSocketClient.getInstance();
		ws.run();
	}

	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		System.out.println("onOpen");
		webSocket.send("hello server");
		System.out.println(response.message());
	}

	@Override
	public void onMessage(WebSocket webSocket, final String text) {     
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				onMessage(text);
			}
		});
		onMessage(text);
}
	public void onMessage( String message ) {
		System.out.println(message);
	}
	@Override
	public void onClosing(WebSocket webSocket, int code, String reason) {
		System.out.println("onOpen");
	}

	@Override
	public void onFailure(WebSocket webSocket, Throwable t, Response response) {
		webSocket.close(1000,"断线重连");
		System.out.println("onFailure");
		try {
			Thread.sleep(30*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		run();
	}

}
