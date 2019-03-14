package com.nettyTest.NettyDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class ClientHandler extends ChannelHandlerAdapter {
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
			System.out.println("Client: "+request);
			
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
