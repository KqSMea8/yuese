package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileInOut {
	//输出流
	public static void writeFile(byte[] in, File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			for (int i = 0; i < in.length; i++) {
				fos.write(in[i]);
			}
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println("操作的文件不存在");
		} catch (IOException e) {
			System.out.println("发生IO操作异常");
		}
	}
	//输入流
	public static byte[] readFile(File file) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		FileInputStream is;
		byte[] in = null;
		try {
			is = new FileInputStream(file);
			in = new byte[(int) file.length()];
			int size = 0;
			while ((size = is.read(in)) != -1) {
				out.write(in, 0, size);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println("操作的文件不存在");
		} catch (IOException e) {
			System.out.println("发生IO操作异常");
		}
		return in;
	}
	//把流转化为Bitmap图片        
	public static Bitmap getLoacalBitmap(String path) {
		  try {
              FileInputStream fis = new FileInputStream(path);
              return BitmapFactory.decodeStream(fis);  

           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return null;
         }
	}

}
