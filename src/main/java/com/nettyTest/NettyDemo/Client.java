package com.nettyTest.NettyDemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public static void main(String[] args) throws Exception {
    		//创建工程线程组
        	EventLoopGroup workerGroup = new NioEventLoopGroup();
        	//创建辅助类Bootstrap。
            Bootstrap b = new Bootstrap(); // (1)
            //将工程线程组加入到公作组里面
            b.group(workerGroup); // (2)
            //指定处理通信管道类型
            b.channel(NioSocketChannel.class); // (3)
            //把ClientHandler添加到ServerBootstrap处理服务器端发过来的信息
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel sc) throws Exception {
                    sc.pipeline().addLast( new ClientHandler());
                }
            });
            
            // 启动客户端并加载连接端口127.0.0.1:8080
            ChannelFuture cf1 = b.connect("127.0.0.1", 8080).sync(); // (5)
            //从管道里面写入数据Unpooled.copiedBuffer将字节数组 bytep[]转成缓冲流 Buf
            cf1.channel().write(Unpooled.copiedBuffer("777".getBytes()));
            //　flush()把缓冲区的内容强制的写出
            cf1.channel().flush();
            // 等待连接关闭.
            cf1.channel().closeFuture().sync();
           //关闭工作线程
            workerGroup.shutdownGracefully();
        
    }
}
