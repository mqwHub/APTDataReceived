package com.zxsoft.constant;

public interface KafkaProperties {
	public static final String kafkaClusterUrl = "192.168.5.201:9092,192.168.5.202:9092,192.168.3.11:9092";
	public static final String topic = "sentiment-cache";
	public static final String lineSeparator = System.getProperty("line.separator", "\n");
}
