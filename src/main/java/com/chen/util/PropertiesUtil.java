package com.chen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import javax.security.auth.login.Configuration;

import org.apache.commons.lang3.ClassPathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    public static Properties load(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            return load(in);
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static Properties load(InputStream in) throws IOException {
        Properties prop = new Properties();
        prop.load(in);
        return prop;
    }

    public static Properties loadXml(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            return loadXml(in);
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static Properties loadXml(InputStream in) throws IOException {
        Properties prop = new Properties();
        prop.loadFromXML(in);
        return prop;
    }

    public static boolean isXmlProperties(String name) {
        return name.endsWith(".xml.properties");
    }

    public static boolean isProperties(String name) {
        return name.endsWith(".properties");
    }

    public static Properties load(String name, InputStream in)
            throws IOException {
        Properties prop = null;
        if (isXmlProperties(name)) {
        	prop = loadXml(in);
        } else if (isProperties(name)) {
        	prop = load(in);
        }
        return prop;
    }
    
    public static void store(Properties prop, File file) throws IOException {
		OutputStream out = null;
		try{
			out = new FileOutputStream(file);
			String name = file.getName();
			if (isXmlProperties(name)) {
	        	prop.storeToXML(out, "");
	        } else {
	        	prop.store(out, "");
	        }
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
   

    private PropertiesUtil() {
    }

    public static Properties loadProperties(String file) {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            properties.load(fis);
        } catch (Throwable ignore) {
            if (ignore instanceof FileNotFoundException) {
                logger.error(ignore.getMessage());
                properties = null;
            } else {
                logger.error(ignore.getMessage(), ignore);
            }
        } finally {
        	try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return properties;
    }
}
