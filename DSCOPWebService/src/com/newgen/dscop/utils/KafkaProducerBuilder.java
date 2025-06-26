package com.newgen.dscop.utils;
import org.apache.kafka.clients.producer.Producer;


public interface KafkaProducerBuilder {
	
	  Producer<String, String> buildProducer(String topic);

}
