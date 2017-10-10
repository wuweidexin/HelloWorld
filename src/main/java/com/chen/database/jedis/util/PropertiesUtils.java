package com.chen.database.jedis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class PropertiesUtils {

	static Logger logger = Logger.getLogger(PropertiesUtils.class);

	private static PropertiesUtils parse=new PropertiesUtils();

	private static Properties properties;

	static String path;

	public static void init(String path) {
		PropertiesUtils.path = path;
		System.out.println(path);
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	public String getProperty(String key,String defaultValue) {
		return properties.getProperty(key,defaultValue);
	}
	private PropertiesUtils() {

	}

	public static PropertiesUtils getInstance() {
		if (properties == null) {
			try {
				properties= new Properties();
				FileInputStream in = new FileInputStream(new File(path));
				properties.load(in);
				IOUtils.closeQuietly(in);
			} catch (FileNotFoundException e) {
				logger.info(e.getMessage() + e.getCause());
			} catch (IOException e) {
				logger.info(e.getMessage() + e.getCause());
			}
		}
		return parse;
	}

	public static boolean isDev() {
		return Boolean.valueOf(parse.getProperty("openimport.isdev"));
	}
}