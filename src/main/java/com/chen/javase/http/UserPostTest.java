package com.chen.javase.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.chen.util.FileOperation;
import com.chen.util.Signature;

public class UserPostTest {
	static String appid = "8eef700d-057d-4158-a148-094cac22abdd";
	// 登录第一步测试
	public static void registerone() {
		String result = null;
		String url = "http://localhost:8080/everyonehappiness/userapi/checknickname";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("nickname", "cjqbrave");// 当前页数,默认1
		try {
			result = HttpUtils.net(url, params, "POST", null, null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void registerthree() {
		String result = null;
		String url = "http://localhost:8080/everyonehappiness/userapi/registerlogin";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		// params.put("account", "chenjunquan");
		// params.put("password", "chenjunquan");
		params.put("nickname", "cjqbrave");// 当前页数,默认1
		try {
			result = HttpUtils.net(url, params, "POST", null, null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateUser() {
		String result = null;
		String url = "http://localhost:8080/everyonehappiness/userapi/updateuser";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		// params.put("account", "chenjunquan");
		// params.put("password", "chenjunquan");
		params.put("id", "20151212");
		params.put("nickname", "chenjunquan");// 当前页数,默认1
		params.put("qq", "610392617");
		params.put("webchat", "610392617");
		try {
			result = HttpUtils.net(url, params, "POST", null, null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 登录第一步测试
	public static void testlogin() throws IOException {
		String result = null;
		String url = "http://localhost:8080/everyonehappiness/userapi/login";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("type", 0);
		params.put("key", "chenjunquan123");

		HttpUtils.generateBaseParam(params,false);
		try {
			result = HttpUtils.net(url, params, "POST", null, null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 注册
	public static void testregister() throws IOException {
		String result = null;
		String url = "http://localhost:8080/everyonehappiness/userapi/register";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("type", 0);

		params.put("account", "cjqbrave");
		params.put("password", "chenjunquan");
		params.put("id", "20151226");
		params.put("nickname", "cjq");
		params.put("qq", "610392617");
		params.put("webchat", "610392617");
		params.put("wechatkey", "610392617");

		HttpUtils.generateBaseParam(params, false);
	
		try {
			result = HttpUtils.net(url, params, "POST", null, null);
			JSONObject obj = JSONObject.fromObject(result);
			JSONObject data = obj.getJSONObject("data");
			File file = new File("D:/out.txt");
			FileOutputStream out = new FileOutputStream(file);
			byte[] tb =data.toString().getBytes();
			out.write(tb);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 测试token
	public static void testregistertwo() throws IOException {
		String result = null;
		String url = "http://localhost:8080/everyonehappiness/userapi/registertwo";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		HttpUtils.generateBaseParam(params, true);
		try {
			result = HttpUtils.net(url, params, "POST", null, null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			// registerone();
			// updateUser();
//			testlogin();
//			testregister();
			testregistertwo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
