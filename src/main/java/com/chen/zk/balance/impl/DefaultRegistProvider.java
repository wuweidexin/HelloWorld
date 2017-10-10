package com.chen.zk.balance.impl;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

import com.chen.zk.balance.RegisterProvider;
import com.chen.zk.balance.util.ZooKeeperRegisterContext;
import com.chen.zk.zkmaster.ConfigData;

/**
 * @author chen
 */
public class DefaultRegistProvider implements RegisterProvider {

	@Override
	public boolean register(Object context) {
		// TODO Auto-generated method stub
		ZooKeeperRegisterContext rgct = (ZooKeeperRegisterContext) context;
		ZkClient client = rgct.getZkClient();
		String path = rgct.getPath();
		try {
			client.createEphemeral(path, rgct.getData());
		} catch (ZkNoNodeException e) {
			String rootpaht = path.substring(0, path.lastIndexOf("/"));
			client.createPersistent(rootpaht, true);
			register(context);
		}
		return true;
	}

	@Override
	public boolean unregister(Object context) {
		return false;
	}

}
