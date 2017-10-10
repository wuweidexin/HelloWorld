package com.chen.javase.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.chen.util.Signature;

import net.sf.json.JSONObject;

public class ArticleTest {
	public static void main(String[] args) {
		try {
//			testlogin();
			article();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 1.按更新时间查询笑话
	public static void article() throws IOException {
		String result = null;
		String url = "http://localhost:8080/everyonehappiness/articleapi/article";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("page", 0);// 当前页数,默认1
		params.put("pagesize", 10);// 每次返回条数,默认1,最大20
		HttpUtils.generateBaseParam(params, true);
		try {
			result = HttpUtils.net(url,params, "GET", null, null);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
