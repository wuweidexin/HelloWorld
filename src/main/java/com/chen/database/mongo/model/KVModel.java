package com.chen.database.mongo.model;

public class KVModel {
	String key;
	String val;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public KVModel(String key, String val) {
		super();
		this.key = key;
		this.val = val;
	}
	public KVModel() {
		super();
	}
}
