package com.chen.zk.zkmaster;

import java.io.Serializable;

/**
 * @author chen
 */
public class ConfigData implements Serializable,Comparable<ConfigData>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private String port;
	private String name;
	private Integer balance;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	@Override
	public int compareTo(ConfigData o) {
		return this.getBalance().compareTo(o.getBalance());
	}
	
}
