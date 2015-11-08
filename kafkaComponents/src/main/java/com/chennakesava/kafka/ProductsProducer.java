package com.chennakesava.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.producer.KeyedMessage;
import kafka.producer.Producer;
import kafka.producer.ProducerConfig;
import scala.collection.Seq;

public class ProductsProducer {
	public static void main(String[] args) {
		long events = Long.parseLong(args[0]);
		Random rnd = new Random();
		
		Properties props = new Properties();
		props.put("metadata.broker.list", "broker1:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "example.producer.SimplePartitioner");
        props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
 
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (long nEvents = 0; nEvents < events; nEvents++) { 
               long runtime = new Date().getTime();  
               String productName = "sampleProduct_name" + rnd.nextInt(255);
               String price = String.valueOf(rnd.nextInt(1000));
               String msg = runtime +","+productName+","  + price; 
               KeyedMessage<String, String> data = new KeyedMessage<String, String>("products",msg);
               producer.send((Seq<KeyedMessage<String, String>>) data);
        }
        producer.close();
				
	}

}
