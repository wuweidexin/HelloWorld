package com.chen.test;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.jawin.COMException;
import org.jawin.DispatchPtr;

import net.sf.json.JSONObject;


//我的第一个测试类
public class HelloWorld {
	public static void main(String[] args) throws Exception {
//		DispatchPtr p2f = null;
//		try {
//			p2f = new DispatchPtr("Print2Flash4.Server");
//		} catch (Exception e) {
//			//如果4不存在，那么就取3
//			try {
//				p2f =  new DispatchPtr("Print2Flash3.Server");
//			} catch (COMException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//		System.out.println("Hello");
		
//		DispatchPtr p2f = null;
//		System.out.println(System.getProperty("java.library.path"));  
//		System.out.println("Hello");
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MONTH, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(sdf.format(cal.getTime()));
		
//		System.out.println(MemberStatus.ACTIVE);
		/*String sas=null;
		try {
			sas = URLEncoder.encode("<script>alert('fdsfsd')</script>", "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sas);*/
//		JSONObject json = new JSONObject();
//		json.put("name", "chenjunquan");
		
//		Calendar yc = Calendar.getInstance();
//        yc.add(Calendar.DAY_OF_MONTH, -2);
//        yc.set(Calendar.HOUR_OF_DAY, 24);
//        yc.set(Calendar.MINUTE,0);
//        yc.set(Calendar.SECOND,0); 
//        Date from = yc.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(sdf.format(from));
//        
//        Calendar yc1 = Calendar.getInstance();
//        yc1.add(Calendar.DAY_OF_MONTH, -1);
//        yc1.set(Calendar.HOUR_OF_DAY, 23);
//        yc1.set(Calendar.MINUTE,59);
//        yc1.set(Calendar.SECOND,59); 
//        Date to = yc1.getTime();
//        System.out.println(sdf.format(to));
//		JSONObject json = new JSONObject();
//		json.put("time", new Date());
//		Date d = (Date) json.get("time");
//		System.out.println(d.getTime());
//		System.out.println(11/5);
	/*	String longName = "测试工作圈!";
		if(longName.endsWith("!")){
			longName = longName.substring(0, longName.length()-1);
		}
		System.out.println(longName);*/
		
//		System.out.println(getParentLongNumber("!"));
		
//		Calendar yc = Calendar.getInstance();
//        yc.add(Calendar.DAY_OF_MONTH, -90);
//        yc.set(Calendar.HOUR_OF_DAY, 24);
//        yc.set(Calendar.MINUTE,0);
//        yc.set(Calendar.SECOND,0); 
//		Date limitDate = yc.getTime();
		//String str = "2019-12-31 23:59:59";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date dat = new Date(1497855121455L);
//		System.out.println(sdf.format(dat));
	    
//	    String lo1 = "0!1!3";
//	    String plo = "0!1";
//	    System.out.println(lo1.contains(plo));
//	    System.out.println(plo.contains(lo1));
//	    String[] arr = new String[]{"A", "B", "C", "D"};
//	   
//	    System.out.println(Arrays.toString(arr));
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse("2017-08-21"));
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        System.out.println(sdf.format(yesterday));
        
      //前天
//        Calendar tcal = Calendar.getInstance();
//        tcal.setTime(new Date());
//        tcal.add(Calendar.DAY_OF_MONTH, -2);
//        Date dby = tcal.getTime();
//        System.out.println(sdf.format(yesterday)+", " + sdf.format(dby));
	}
	
	private static String getParentLongNumber(String longNum) {
		if(null == longNum && longNum.trim().length() == 0) {
			return "";
		}
		//工作圈级别的父亲的longNumber是空串
		String[] arr = longNum.split("!");
		if(arr.length == 1) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < arr.length - 1; i ++) { 
			String temp = arr[i];
			buf.append(temp).append("!");
		}
		return buf.toString();
	}
}
