package com.chen.zk.balance;
/**
 * @author chen
 */
public interface BalanceUpdateProvider {
	public boolean addBalance(Integer step);
	public boolean reduceBalance(Integer step);
}
