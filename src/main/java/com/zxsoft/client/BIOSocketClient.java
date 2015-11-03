package com.zxsoft.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOSocketClient {

	private String url;

	private int port;

	public BIOSocketClient(String url, int port) {
		this.url = url;
		this.port = port;
	}

	private void go() {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 1; i++) {
			exec.execute(new MyThread(i));
		}
		exec.shutdown();
	}

	private class MyThread implements Runnable {

		private Socket socket;
		private InputStream is;
		private OutputStream os;
		private int i;

		public MyThread(int i) {
			this.i = i;
			try {
				socket = new Socket(url, port);
				is = socket.getInputStream();
				os = socket.getOutputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String s = gens(1000 * 10);  // 1000 * 1000 = 1M×Ö½Ú
			byte[] req = new String(s + i + System.getProperty("line.separator", "\n")).getBytes();
			byte[] rsp = new byte[1024];
			try {
				for (int i = 0;i < 10000; i++) {
					os.write(req);		
				}
//				is.read(rsp);
//				System.out.println(new String(rsp));
				Thread.sleep(100000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private String gens(int j) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < j; i++) {
				sb.append('a');
			}
			return sb.toString();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "192.168.5.202";
		String url1 = "127.0.0.1";
		int port = 8080;
		new BIOSocketClient(url1, port).go();
	}
}
