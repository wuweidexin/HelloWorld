package com.chen.zk.balance.impl;

import org.apache.zookeeper.data.Stat;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import com.chen.zk.balance.BalanceUpdateProvider;
import com.chen.zk.zkmaster.ConfigData;

/**
 * @author chen
 */
public class DefaultBalanceUpdateProvider implements BalanceUpdateProvider {
	private ZkClient zkClient;
	private String serverPath;

	public DefaultBalanceUpdateProvider() {
		// TODO Auto-generated constructor stub
	}

	public DefaultBalanceUpdateProvider(ZkClient zkClient, String serverPath) {
		this.zkClient = zkClient;
		this.serverPath = serverPath;
	}

	@Override
	public boolean addBalance(Integer step) {
		// 注意要考虑在多个client同时调用的时候出现的异常
		Stat stat = new Stat();
		ConfigData configData = null;
		while (true) {
			try {
				configData = zkClient.readData(serverPath, stat);
				configData.setBalance(configData.getBalance() + step);
				zkClient.writeData(serverPath, configData, stat.getAversion());
				return true;
			} catch (ZkBadVersionException e) {
				// 暂时采取忽略
			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override
	public boolean reduceBalance(Integer step) {
		Stat stat = new Stat();
		ConfigData configData = null;
		while (true) {
			try {
				configData = zkClient.readData(serverPath, stat);
				int balance = configData.getBalance();
				configData.setBalance(balance-step>0?balance-step:0);
				zkClient.writeData(serverPath, configData, stat.getAversion());
				return true;
			} catch (ZkBadVersionException e) {
				// 暂时采取忽略
			} catch (Exception e) {
				return false;
			}
		}
	}

}
