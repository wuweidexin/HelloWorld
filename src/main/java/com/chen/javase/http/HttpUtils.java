package com.chen.javase.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.chen.util.FileOperation;
import com.chen.util.Signature;


public class HttpUtils {
	
	public static final String multipartReq = "multipart/form-data";
	public static final String formReq = "application/x-www-form-urlencoded";
	public static final String json = "application/json";
	
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	public static final String DEF_CHATSET = "UTF-8";
	/**
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @param accept 
	 * 			  客户端接受类型
	 * @param contentType
	 * 			服务的需要返回的类型
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map<String, Object> params,
			String method, String accept, String contentType) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if ((method == null || method.equals("GET")) && null != params) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			if(null != accept && accept.length() > 0) {
				//设置接受类型
				conn.setRequestProperty("Accept", accept);
			}
			if(null != contentType && contentType.length() > 0) {
				//设置发送类型
				conn.setRequestProperty("Content-Type", contentType);
			}
			conn.connect();
			if (params != null && method.equals("POST")) {
				try (DataOutputStream out = new DataOutputStream(
						conn.getOutputStream())) {
					out.writeBytes(urlencode(params));
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}
	// 将map型转为请求参数型
	public static String urlencode(Map<String, ?> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, ?> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static String getTime(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d = sdf.parse(sdf.format(new Date()));
			return String.valueOf(d.getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void generateBaseParam(Map<String, Object> params, boolean inAppId) throws IOException{
		String timestamp = String.valueOf(System.currentTimeMillis());
		String nonce = "121";
		if(inAppId) {
			String str = FileOperation.getPathFile("D:/out.txt");
			if(StringUtils.isNoneBlank(str)) {
				JSONObject json = JSONObject.fromObject(str);
				String appid = json.getString("appid");
				String token = json.getString("token");
				params.put("appid", appid);
				params.put("token", token);
			}
		}
		String signature = Signature.sign(timestamp, nonce, null);
		params.put("signature", signature);
		params.put("timestamp", timestamp);
		params.put("nonce", nonce);
	}
	
	

}
