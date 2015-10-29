package com.zxsoft.server;

import com.zxsoft.constant.KafkaProperties;
import com.zxsoft.util.ProducerHolder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import kafka.producer.KeyedMessage;

public class TelnetServerHandler extends ChannelHandlerAdapter implements KafkaProperties {
	
	public TelnetServerHandler() {
		System.out.println("TelnetServerHandler constructor init"); // 
	}
	
	private int counter;
	
	private boolean isWriteKafka;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("enter channelRead method.");
		String req = null;
		String input = null;
		if (msg instanceof java.lang.String) {
			req = (String) msg;
		}
		else if (msg instanceof io.netty.buffer.ByteBuf) {
			ByteBuf in = (ByteBuf) msg;
			byte[] bs = new byte[in.readableBytes()];
			in.readBytes(bs);
			req = new String(bs);
		}
		System.out.println("The Server receiver req : " + req + " , and counter is : " + ++counter);
		input = req.substring(0, req.length() - lineSeparator.length() );
		// ÍùkafkaÐ´Êý¾Ý
		if (!isWriteKafka) {
			ProducerHolder.getProducer().send(new KeyedMessage(topic, input));			
		}
//		ctx.writeAndFlush(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//		cause.printStackTrace();
		ctx.close();
	}
}
