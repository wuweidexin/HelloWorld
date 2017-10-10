package com.chen.test;

public enum MemberStatus {
	ACTIVE, NOT_ACTIVE, SUSPENDED,UNVERIFIED;  //在职、离职、禁用、未验证
	// 讯通 //人员状态，0离职 1在职 2禁用
	public static String toWBStatus(String xtStatus) {

		if("0".equals(xtStatus)){
			return NOT_ACTIVE.name();
		}else if("1".equals(xtStatus)){
			return ACTIVE.name();
		}else if("2".equals(xtStatus)){
			return SUSPENDED.name();
		}else if("3".equals(xtStatus)){
			return UNVERIFIED.name();
		}
		return "";
	}
}