package com.chen.javase.jawin;

import java.io.File;
import java.io.IOException;

import org.jawin.COMException;
import org.jawin.DispatchPtr;
import org.jawin.win32.Ole32;

public class P2FConvertor {
	
	public static String convert(String absPath) {
		String filename = null;
//		String absPath = null;
		Boolean err = Boolean.valueOf(false);
		File file = null;
		try {
			//文件名
			filename = absPath;
			//路径
//			absPath = null;


			Ole32.CoInitialize();

			DispatchPtr p2f = null;
			//调用Print2Flash4.Server
			p2f = new DispatchPtr("Print2Flash4.Server");
			//获取默认的配置，可以进行重新设置
			DispatchPtr defProfile = (DispatchPtr) p2f.get("DefaultProfile");
			
			//打印设置
			DispatchPtr defPrintPrf = (DispatchPtr) p2f
					.get("DefaultPrintingPreferences");
			//
			DispatchPtr defBatchPrf = (DispatchPtr) p2f
					.get("DefaultBatchProcessingOptions");
			defProfile.put("InterfaceOptions", 1064959);
			
			// 位掩码指定哪些保护选项应在转换后的文件被激活。 4：启用Print2Flash文档API支持（使用Print2Flash文档API从Flash只是部分禁用）
			defProfile.put("ProtectionOptions", 4);
			//输出格式,1：表示单文件
			defProfile.put("OutputFormat", 1);
			//描述了该网页 页面缩略图创建,所有界面用all,否则要写页面范围格式
			//[Range1Start[-][Range1End]] [,Range2Start[-][Range2End]] [, ... ]
			defProfile.put("ThumbnailPageRange", "1");
			//	页面缩略图命名
			defProfile.put("ThumbnailFileName", "%name%_%page%.%ext%");
			//	页面缩略图 的图像文件格式1：jpeg;2:png
			defProfile.put("ThumbnailFormat", 2);
			//缩略图的宽
			defProfile.put("ThumbnailImageWidth", 110);
			//缩略图的高
			defProfile.put("ThumbnailImageHeight", 83);

			file = new File(absPath);
			long length = file.length();
			if (length > 8388608L) {
				System.out.println("Bigger than 8MB, low quality.");
				defPrintPrf.put("Resolution", 120);
				//使用压缩算法输出JPEG
				defProfile.put("UseJpeg", true);
				//JPEG资料1-100
				defProfile.put("JpegQuality", 20);
			} else if (length > 5242880L) {
				System.out.println("Bigger than 5MB and smaller than 8MB, middle quality.");
				defPrintPrf.put("Resolution", 144);
				defProfile.put("UseJpeg", true);
				defProfile.put("JpegQuality", 30);
			} else {
				System.out.println("Smaller than 5MB, high quality.");
				defPrintPrf.put("Resolution", 196);
				defProfile.put("UseJpeg", true);
				defProfile.put("JpegQuality", 60);
			}

			if (filename.toLowerCase().endsWith(".pdf")) {
				if (true) {
					System.out.println("Orientation: Landscape");
					defPrintPrf.put("Orientation", 2);
				} else {
					System.out.println("Orientation: Portrait");
					defPrintPrf.put("Orientation", 1);
				}

			}

			if ((filename.toLowerCase().endsWith(".html"))
					|| (filename.toLowerCase().endsWith(".htm"))
					|| (filename.toLowerCase().endsWith(".mht"))
					|| (filename.toLowerCase().endsWith(".mhtml"))) {
				defBatchPrf.put("PressPrintButton", 1);
			}

			if ((filename.toLowerCase().endsWith(".xls"))
					|| (filename.toLowerCase().endsWith(".xlsx"))) {
				DispatchPtr excelPrf = (DispatchPtr) defBatchPrf
						.get("ExcelOptions");
				excelPrf.put("SheetRange", "1-100");
				defBatchPrf.invoke("ApplyChanges");
			}

			p2f.invoke("ConvertFile", absPath);

			String flashId = null;//saveFile(absPath + ".swf");

			String thumbnailId = null;//saveFile(absPath + "_1.png");
			File dest;
			return flashId + "," + thumbnailId;
		} catch (IllegalArgumentException ie) {
			err = Boolean.valueOf(true);
//			logger.error("Could not find file from Grid FS: ", ie);
			try {
				Ole32.CoUninitialize();
			} catch (Exception localException2) {
			}
			if (err.booleanValue()) {
				try {
					File dest = new File(file.getParent() + "\\" + "p2f.err."
							+ file.getName());
					file.renameTo(dest);
				} catch (Exception e) {
					e.printStackTrace();
//					logger.warn("Failed to rename bad_format file:", e.getMessage());
//					if (!logger.isWarnEnabled())
//						return "BAD_FORMAT,BAD_FORMAT";
				}
			}
		} catch (COMException ce) {
			ce.printStackTrace();
			err = Boolean.valueOf(true);
//			logger.error(
//					"An error occurred during the process of conversion: ", ce);
			try {
				Ole32.CoUninitialize();
			} catch (Exception localException3) {
			}
			if (err.booleanValue()) {
				try {
					File dest = new File(file.getParent() + "\\" + "p2f.err."
							+ file.getName());
					file.renameTo(dest);
				} catch (Exception e) {
					e.printStackTrace();
//					logger.warn("Failed to rename bad_format file:", e.getMessage());
//					if (!logger.isWarnEnabled())
//						return "BAD_FORMAT,BAD_FORMAT";
				}
				
			}
		} catch (Throwable t) {
			err = Boolean.valueOf(true);
			t.printStackTrace();
//			logger.error(
//					"Un-expected error occurred during the process of conversion: ",
//					t);
			try {
				Ole32.CoUninitialize();
			} catch (Exception localException5) {
			}
			if (err.booleanValue()) {
				try {
					File dest = new File(file.getParent() + "\\" + "p2f.err."
							+ file.getName());
					file.renameTo(dest);
				} catch (Exception e) {
					e.printStackTrace();
//					logger.warn("Failed to rename bad_format file:", e.getMessage());
//					if (!logger.isWarnEnabled())
//						return "BAD_FORMAT,BAD_FORMAT";
				}
				
			}
		} finally {
			try {
				Ole32.CoUninitialize();
			} catch (Exception localException6) {
			}
			if (err.booleanValue()) {
				try {
					File dest = new File(file.getParent() + "\\" + "p2f.err."
							+ file.getName());
					file.renameTo(dest);
				} catch (Exception e) {
//					if (logger.isWarnEnabled()) {
//						logger.warn("Failed to rename bad_format file:",
//								e.getMessage());
//					}
					e.printStackTrace();
				}
			}
		}
		return "BAD_FORMAT,BAD_FORMAT";

	}
	public static void main(String[] args) {
		String filePath = "F://demo.pptx";
		convert(filePath);
	}
}
