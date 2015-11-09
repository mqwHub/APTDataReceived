package com.zxsoft.server;

import com.zxsoft.constant.KafkaProperties;
import com.zxsoft.util.ThreadPoolHolder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@Sharable
public class TelnetServerHandler extends ChannelHandlerAdapter implements KafkaProperties {
	
	public TelnetServerHandler() {
		System.out.println("TelnetServerHandler constructor init"); // 
	}
	
	private long time = 0l;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		long begin = System.currentTimeMillis();
		if (time == 0l) {
			time = begin;
		}
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
		System.out.println(Thread.currentThread().getName());
		input = req.substring(0, req.length() - lineSeparator.length() );
		long t = System.currentTimeMillis();
		System.out.println(t - this.time);
		
		// Òì²½Ð´Êý¾Ý
//		if (!isWriteKafka) {
//			exec.execute(new MQSendMessageTask(topic, input));			
//		}
		
		ThreadPoolHolder.getBusinessThreadPool().execute(new MQSendMessageTask(topic, input));
		
//		long before = System.currentTimeMillis();
//		ProducerHolder.getProducer().send(new KeyedMessage(topic, input));
//		exec.execute(new MQSendMessageTask(topic, input));
//		long after = System.currentTimeMillis();
//		System.out.println(after - before);
//		ctx.writeAndFlush(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//		cause.printStackTrace();
		ctx.close();
	}
}
