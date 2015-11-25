package com.chennakesava.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
import org.json.JSONObject;
import org.json.JSONException;
import kafka.producer.KeyedMessage;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

public class ProductsProducer {
	public static void main(String[] args) {
		long events = Long.parseLong("200");
		Random rnd = new Random();
		try {
			Properties props = new Properties();
			//set broker address
			props.put("metadata.broker.list", "104.196.53.159:9092,104.196.22.222:9092");
	        props.put("serializer.class", "kafka.serializer.StringEncoder");
	        //partitioner class is set default to serializer class
	        //props.put("partitioner.class", "example.producer.SimplePartitioner");
	        props.put("request.required.acks", "1");
	 
	        ProducerConfig config = new ProducerConfig(props);
	 
	        Producer<String, String> producer = new Producer<String, String>(config);
	        //read a file or csv file here and send parsed text to kafka
	        for (long nEvents = 0; nEvents < events; nEvents++) { 
	               long runtime = new Date().getTime();  
	               String productName = "sampleProduct_name_" + rnd.nextInt(255);
	               String price = String.valueOf(rnd.nextInt(1000));
	               String topic = "Amazon";
	               String msg = runtime +","+productName+ "," + price; 
	               System.out.println("Sending msg: "+msg);
	               KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic,convertLineToJson(msg));
	               producer.send(data);
	        }
	        
	        System.out.println("Sent events:"+events);
	        producer.close();
		} catch (Exception e) {
			System.err.println("Caught exception : "+e.getMessage());
		}
				
	}
	
	public static String convertLineToJson(final String line) {
		try {
			String[] arr = line.split(",");
			JSONObject record = new JSONObject();
			record.put("time_stamp",arr[0]);
			record.put("product_name", arr[1]);
			record.put("price",arr[2]);
			return record.toString();
		} catch(Exception e) {
			System.err.println("Caught exception: "+e.getMessage());
			return null;
		}
	}

}
