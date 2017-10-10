package com.chen.zk.master;

import java.io.Serializable;

/**
 * @author chen
 */
public class ConfigData implements Serializable{
	private String ip;
	private String port;
	private String name;
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
	
}
