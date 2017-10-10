package com.chen.zk.balance.impl;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import com.chen.zk.balance.RegisterProvider;
import com.chen.zk.balance.Server;
import com.chen.zk.balance.ServerHandler;
import com.chen.zk.balance.util.ZooKeeperRegisterContext;
import com.chen.zk.zkmaster.ConfigData;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author chen
 */
public class ServerImpl implements Server {
	
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workGroup = new NioEventLoopGroup();
	private ServerBootstrap bootStrap = new ServerBootstrap();
	private ChannelFuture cf ;
	private ZkClient zc;
	private RegisterProvider registProvider;
	private boolean binded = false;
	private String zkAddress;
	private String serversPath;
	private String currentServerPath;
	private ConfigData sd;
	private int SESSION_TIME_OUT = 1000;
	private int CONNECT_TIME_OUT = 1000;
	
	public ServerImpl() {
		// TODO Auto-generated constructor stub
	}
	public ServerImpl(String zkAddress, String serversPath, ConfigData sd){
		this.zkAddress = zkAddress;
		this.serversPath = serversPath;
		this.zc = new ZkClient(this.zkAddress,SESSION_TIME_OUT,CONNECT_TIME_OUT,new SerializableSerializer());		
		this.registProvider = new DefaultRegistProvider();
		this.sd = sd;
	}	
	
	@Override
	public void bind() {
		if(binded){
			return;
		}
		String mePath = serversPath.concat("/").concat(sd.getPort());
		registProvider.register(new ZooKeeperRegisterContext(mePath, zc, sd));
		currentServerPath = mePath;
		bootStrap.group(bossGroup, workGroup)
		.channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 1024)
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new ServerHandler(new DefaultBalanceUpdateProvider(zc, currentServerPath)));
				
			}
		});
		try {
			cf = bootStrap.bind(Integer.valueOf(sd.getPort())).sync();
			binded = true;
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

}
