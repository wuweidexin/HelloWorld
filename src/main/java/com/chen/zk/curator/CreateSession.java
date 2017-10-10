package com.chen.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author chen
 */
public class CreateSession {
	private static String ZK_PATH = "/zkmaster";
	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(10000, 5);
		CuratorFramework client = CuratorFrameworkFactory
				.newClient("127.0.0.1:2180", 5000, 5000, retryPolicy);
		   // 2.Client API test
        // 2.1 Create node
        String data1 = "hello";
        try {
            client.create().
                    creatingParentsIfNeeded().
                    forPath(ZK_PATH, data1.getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 2.2 Get node and data
        System.out.println("ls"+ "/");
        try {
            System.out.println(client.getChildren().forPath("/"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            System.out.println(client.getData().forPath(ZK_PATH));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 2.3 Modify data
        String data2 = "world";
        client.setData().forPath(ZK_PATH, data2.getBytes());
        System.out.println(client.getData().forPath(ZK_PATH));

        // 2.4 Remove node
        client.delete().forPath(ZK_PATH);
        System.out.println(client.getChildren().forPath("/"));
	}
}
