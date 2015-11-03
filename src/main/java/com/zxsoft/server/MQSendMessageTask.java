package com.zxsoft.server;

import com.zxsoft.util.ProducerHolder;

import kafka.producer.KeyedMessage;

public class MQSendMessageTask implements Runnable {

	private String topic;
	
	private String input;
	
	public MQSendMessageTask(String topic, String input) {
		this.topic = topic;
		this.input = input;
	}
	@Override
	public void run() {
		ProducerHolder.getProducer().send(new KeyedMessage(topic, input));
	}

}
