package com.zxsoft.util;

import java.util.Properties;

import com.zxsoft.constant.KafkaProperties;

import kafka.producer.ProducerConfig;

public class ProducerHolder implements KafkaProperties {

	private static kafka.javaapi.producer.Producer<Integer, String> producer;
	
	public static kafka.javaapi.producer.Producer<Integer, String> getProducer() {
		if (producer == null) {
			Properties props = new Properties();
			props.put("serializer.class", "kafka.serializer.StringEncoder");
			props.put("metadata.broker.list", kafkaClusterUrl);
			producer = new kafka.javaapi.producer.Producer(new ProducerConfig(props));
		}
		return producer;
	}
}
