package com.chen.database.mongo.model;

public class Config {
	private String host;
	private int port;
	private String db;
	private String username;
	private String password;
	public Config(String host, int port, String db, String username,
			String password) {
		super();
		this.host = host;
		this.port = port;
		this.db = db;
		this.username = username;
		this.password = password;
	}
	public Config() {
		super();
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
