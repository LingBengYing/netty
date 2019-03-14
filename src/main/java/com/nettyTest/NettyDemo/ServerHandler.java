package com.nettyTest.NettyDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class ServerHandler extends ChannelHandlerAdapter {
	//重写channelRead方法，

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
			//创建缓冲区buf
			ByteBuf buf = (ByteBuf) msg;
			//创建字节数组data
			byte[] data=new byte[buf.readableBytes()];
			//将缓冲区buf的内容写入data
			buf.readBytes(data);
			//将字节数组data转为字符串request，指定字符集
			String request = new String(data, "utf-8");
			System.out.println("客户端连接");
			System.out.println("Server: "+request);
			//服务器给客户端的响应
			//String response ="1325865417";
			//从管道里面写入数据Unpooled.copiedBuffer将字节数组 bytep[]转成缓冲流 Buffer
			ctx.writeAndFlush(Unpooled.copiedBuffer("123".getBytes()));
			ctx.flush();
		 
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

}
