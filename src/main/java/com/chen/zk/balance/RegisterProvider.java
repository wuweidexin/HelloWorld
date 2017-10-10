package com.chen.zk.balance;
/**
 * @author chen
 */
public interface RegisterProvider {
	public boolean register(Object context);
	public boolean unregister(Object context);
}
