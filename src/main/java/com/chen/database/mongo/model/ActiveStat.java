package com.chen.database.mongo.model;

import java.util.Date;


public class ActiveStat {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * user id
	 */
	private String userId;
	private String userName;
	private String account;
	private String dept;
	private String photoId;
	
	private String networkId;
	/**
	 * microblog sends count
	 */
	private long sends = 0;
	/**
	 * longin count
	 */
	private long logins = 0;
	/**
	 * be like by others
	 */
	private long belikes = 0;
	/**
	 * send like 
	 */
	private long likes = 0;
	/**
	 * reply others microblog
	 */
	private long replys = 0;
	/**
	 * be reply by others
	 */
	private long bereplys = 0;
	/**
	 * last login time
	 */
	private Date lastLoginTime;
	
	private long msgCounts = 0;
	
	private Date createTime;
	
	private String createTimeStr;
	
	public ActiveStat() {
		super();
	}
	
	public ActiveStat(String userId, String userName, String account, 
			String dept,String photourl,
			String networkId, long sends, long logins, long belikes,
			long replys, Date lastLoginTime, Date createTime,
			String createTimeStr, String photoId, long msgCount, long likes, long msgCounts, long bereplys) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.account = account;
		this.dept = dept;
		this.photoId = photoId;
		this.networkId = networkId;
		this.sends = sends;
		this.logins = logins;
		this.belikes = belikes;
		this.replys = replys;
		this.lastLoginTime = lastLoginTime;
		this.createTime = createTime;
		this.createTimeStr = createTimeStr;
		this.msgCounts = msgCounts;
		this.likes = likes;
		this.bereplys = bereplys;
	}



	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public long getSends() {
		return sends;
	}
	public void setSends(long sends) {
		this.sends = sends;
	}
	public long getLogins() {
		return logins;
	}
	public void setLogins(long logins) {
		this.logins = logins;
	}
	public long getBelikes() {
		return belikes;
	}
	public void setBelikes(long belikes) {
		this.belikes = belikes;
	}
	public long getReplys() {
		return replys;
	}
	public void setReplys(long replys) {
		this.replys = replys;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	
	
	public long getMsgCounts() {
		return msgCounts;
	}

	public void setMsgCounts(long msgCounts) {
		this.msgCounts = msgCounts;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public long getBereplys() {
		return bereplys;
	}

	public void setBereplys(long bereplys) {
		this.bereplys = bereplys;
	}

	@Override
	public String toString() {
		return "ActiveStat [userId=" + userId + ", userName=" + userName + ", account=" + account + ", dept=" + dept
				+ ", photoId=" + photoId + ", networkId=" + networkId + ", sends=" + sends + ", logins=" + logins
				+ ", belikes=" + belikes + ", likes=" + likes + ", replys=" + replys + ", bereplys=" + bereplys
				+ ", lastLoginTime=" + lastLoginTime + ", msgCounts=" + msgCounts + ", createTime=" + createTime
				+ ", createTimeStr=" + createTimeStr + "]";
	}
	
}
