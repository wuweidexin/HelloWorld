package com.chen.javase.regex;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class Signature {
	public static String appSecret = "@#$everyonehappiness2016$#@";

	public static String sign(String timestamp, String nonce, String appid) {
		if (StringUtils.isNotBlank(appid)) {
			String[] arr = new String[] { appSecret, timestamp, nonce, appid };
			Arrays.sort(arr);// 字典排序
			String tempStr = StringUtils.join(arr);// 连接起来
			tempStr = sha1(tempStr);// sha加密
			return tempStr;
		} else {
			String[] arr = new String[] { appSecret, timestamp, nonce };
			Arrays.sort(arr);// 字典排序
			String tempStr = StringUtils.join(arr);// 连接起来
			tempStr = sha1(tempStr);// sha加密
			return tempStr;
		}
	}

	public static String sha1(String... data) {
		Arrays.sort(data);// 按字母顺序排序数组
		return new String(DigestUtils.sha1(StringUtils.join(data)));// 把数组连接成字符串（无分隔符），并sha1哈希
	}
}