package com.chen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileOperation {
	
	public static String getClassPathFile(String fileName) throws IOException{
		InputStream im = ClassLoader.getSystemResourceAsStream(fileName);
		byte [] b = new byte[128];
		StringBuffer sf = new StringBuffer();
		int len = 0;
		while((len = im.read(b)) != -1) {
			sf.append(new String(b));
			b = new byte[len];
		}
		im.close();
		return sf.toString();
	}
	
	public static String getPathFile(String url) throws IOException{
		InputStream im = new FileInputStream(url);
		byte [] b = new byte[128];
		StringBuffer sf = new StringBuffer();
		int len = 0;
		while((len = im.read(b)) != -1) {
			sf.append(new String(b));
			b = new byte[len];
		}
		im.close();
		return sf.toString().trim();
	}
	public static void writeToFile(String key, String url) {
		File file = new File(url);
		if(file.exists()) {
			file.delete();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] tb =key.getBytes();
		try {
			out.write(tb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			String str = getPathFile("D:/out.txt");
			System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
