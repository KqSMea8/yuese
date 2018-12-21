package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Paths;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Downloader extends Thread {
	
	private static final int BUFF_LENGTH = 4096;

	private String downloadUrl;
	private String outputFile;
	private DownloaderListener listener;
	
	private OkHttpClient okHttpClient;
	
	public Downloader(String url, String filepath, DownloaderListener listener) {
		this.downloadUrl = url;
		this.listener = listener;
		this.outputFile = filepath;
		okHttpClient = new OkHttpClient();
	}
	
	private void onError(int code, String reason) {
		if(listener != null) {
			listener.onError(code, reason);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Log.v("XXX","http -1: "+downloadUrl);
			Request request = new Request.Builder().url(downloadUrl).build();
			Call call = okHttpClient.newCall(request);
		
			Response response = call.execute();
			
			Headers headers = response.headers();
			for(String name : headers.names()) {
				System.out.println(name+": "+headers.get(name));
			}
			
			int code = response.code();
			Log.v("XXX","http code: "+code);
			if(code == 200) {
//				System.out.println();
				ResponseBody body = response.body();
//				System.out.println("--------------------------------------");
//				System.out.println("contentLength: "+body.contentLength());
//				System.out.println("contentType: "+body.contentType());
				
				byte[] buf = new byte[BUFF_LENGTH];
				long fileLength = body.contentLength();
				InputStream is = body.byteStream();

				Log.v("XXX","http -2");
				// 如果目录层次不存在则
				File parentDirs = new File(outputFile).getParentFile();
				if(parentDirs.exists() == false) {
					parentDirs.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(new File(outputFile));
				Log.v("XXX","http -3");
				int curReadLength;
				int totalReadLength = 0;
				int progress = 0;
				
				if(listener != null) {
					listener.onProgress(0);
				}
				Log.v("XXX","http -4");
				while((curReadLength = is.read(buf, 0, BUFF_LENGTH)) != -1) {
					totalReadLength += curReadLength;
					
					fos.write(buf, 0, curReadLength);
					fos.flush();
					
					int curProgress = (int) ((double)totalReadLength/(double)fileLength*100);
					if(curProgress != progress) {
						Log.v("XXX","http -5:"+progress);
						progress = curProgress;
						if(listener != null) {
							listener.onProgress(progress);
						}
					}
				}
				Log.v("XXX","http -6");
				is.close();
				fos.close();
				
				
				if(listener != null) {
					listener.onSuccess(fileLength, outputFile);
				}
			} else {
				onError(code, "HTTP返回码错误");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			onError(-2, getStackInfo(e));
		}
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}
	
	private String getStackInfo(Throwable e) {
		String info;
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		info = writer.toString();
		pw.close();
		try {
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return info;
	}
	
	public interface DownloaderListener {

		public void onError(int code, String reason);
		public void onProgress(int progress);
		public void onSuccess(long length, String filePath);
	}
}
