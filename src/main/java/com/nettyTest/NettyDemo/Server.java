package com.nettyTest.NettyDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class Server {
	public static void main(String[] args) throws Exception{
		//创建接收线程组，接收Client端发过来的信息
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		//创建工作线程组，处理实际业务
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		//创建辅助类负责初始化netty服务器，并且开始监听端口的socket请求
		ServerBootstrap b=new ServerBootstrap();
		//把两个线程组加入到辅助类Bootstrap里面去
		b.group(bossGroup,workerGroup)
		//指定处理通信管道类型
		.channel(NioServerSocketChannel.class)
		//把ServerHandler添加到ServerBootstrap处理服务器端发过来的信息
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(new ServerHandler());
			}
		});
		//服务器开始监听端口8080
		ChannelFuture f=b.bind(8080).sync();
		//等待连接关闭
		f.channel().closeFuture().sync();
		//关闭接收线程组
		bossGroup.shutdownGracefully();
		//关闭工作线程组
		workerGroup.shutdownGracefully();
	}
}
