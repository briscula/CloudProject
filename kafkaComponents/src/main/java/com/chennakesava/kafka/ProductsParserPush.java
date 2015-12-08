package com.chennakesava.kafka;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProductsParserPush {

	/**
	 * @param args
	 * input json path -args[0]
	 * topic name    	-args[1]
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		System.out.println("Taking args 1 as filepath and 2 as topic name");
		
		try {
			String filePath = args[0];
			String topic = args[1];
			//Initializing kafka properties
			Properties props = new Properties();
			//set broker address
			props.put("metadata.broker.list", "104.196.53.159:9092,104.196.22.222:9092");
	        props.put("serializer.class", "kafka.serializer.StringEncoder");
	        //partitioner class is set default to serializer class
	        //props.put("partitioner.class", "example.producer.SimplePartitioner");
	        props.put("request.required.acks", "1");
	 
	        ProducerConfig config = new ProducerConfig(props);
	        Producer<String, String> producer = new Producer<String, String>(config);
			
	        Object obj = parser.parse(new FileReader(filePath));
			Set<String> asinSet = new HashSet<String>();

			JSONArray jsonArrayObject = (JSONArray) obj;
			int count = 0;
			System.out.println(jsonArrayObject.size());
			for (Object object : jsonArrayObject) {
				JSONObject jsonObject = (JSONObject) object;
				Object company = (Object) jsonObject.get("Company");
				String strCompany = company.toString();
				String finalCompany = strCompany.replace("[", "").replace("]","");
				String finalAsin = null;
				String finalTitle = null;
				String finalPrice = null;
				String finalDiscount = null;
				
				
				if (finalCompany.equals("Amazon")) {
					Object discount = (Object) jsonObject.get("Discount");
					String strDiscount = discount.toString();
					finalDiscount = strDiscount.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "")
							.replace("$", "");

					Object price = (Object) jsonObject.get("Price");
					String strPrice = price.toString();
					finalPrice = strPrice.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "")
							.replace("$", "");
					if (finalPrice.contains(",")) {
						String[] finalPriceArray = finalPrice.split(",");
						finalPrice = finalPriceArray[0].replace(" ", "");
					}

					Object title = (Object) jsonObject.get("Title");
					String strTitle = title.toString();
					finalTitle = strTitle.replace("[", "")
							.replace("]", "").replace("\"", "");

					Object asin = (Object) jsonObject.get("ASIN");
					String strAsin = null;
					if(asin == null)
						strAsin = "";
					else
					  strAsin= asin.toString();
					finalAsin = strAsin.replace("[", "")
							.replace("]", "");
					if (strAsin.contains("/")) {
						String[] finalAsinArray = strAsin.split("/");
						finalAsin = finalAsinArray[finalAsinArray.length - 1]
								.replace(" ", "");
					}

				} else {
					Object discount = (Object) jsonObject.get("Discount");
					String strDiscount = discount.toString();
					finalDiscount = strDiscount.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "");
					if (finalDiscount.contains("%")) {
						String[] finalDiscountArray = finalDiscount.split("%");
						finalDiscount = finalDiscountArray[0].replace(" ", "")
								+ "%)";
					}

					Object price = (Object) jsonObject.get("Price");
					String strPrice = price.toString();
					finalPrice = strPrice.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "")
							.replace("US ", "");
					if (finalPrice.contains(",")) {
						String[] finalPriceArray = finalPrice.split(",");
						finalPrice = finalPriceArray[0].replace(" ", "");
					}

					Object title = (Object) jsonObject.get("Title");
					String strTitle = title.toString();
					finalTitle = strTitle.replace("[", "")
							.replace("]", "").replace("\"", "");

					Object asin = (Object) jsonObject.get("ASIN");
					String strAsin = asin.toString();
					finalAsin = strAsin.replace("[", "")
							.replace("]", "").replace("\"", "");
					if (strAsin.contains("/")) {
						String[] finalAsinArray = strAsin.split("/");
						finalAsin = finalAsinArray[finalAsinArray.length - 1]
								.replace(" ", "");
					}

					Object category = (Object) jsonObject.get("Category");
					String strCategory = category.toString();
					String finalCategory = strCategory.replace("[", "")
							.replace("]", "");

					if (finalCategory.contains("\"")) {
						String[] finalCategoryArray = finalCategory.split("\"");
						finalCategory = finalCategoryArray[1].replace(" ", "");
					}
				}
				
				if (!(finalTitle.equals("") || finalPrice.equals(""))) {
					System.out.println(finalAsin+"::"+asinSet.contains(finalAsin));
					if (!asinSet.contains(finalAsin)) {
						System.out.println(finalTitle +" :: "+ finalPrice +":: "+finalAsin);
						JSONObject tempJSONObject = new JSONObject();
						tempJSONObject.put("Company", finalCompany);
						tempJSONObject.put("ASIN", finalAsin);
						tempJSONObject.put("Title", finalTitle);
						tempJSONObject.put("Price", finalPrice);
						tempJSONObject.put("Discount", finalDiscount);
						
						//converting json to string
						String msg = tempJSONObject.toString();
						KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic,msg);
			        	
			        	//pushing the json message to kafka
			        	producer.send(data);
			        	System.out.println("Sending:"+msg);
			        	count++;
			        	//if(count > 10) break;
						asinSet.add(finalAsin);
					}
				}
			}
			System.out.println("Pushed:"+count+" items");
			producer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
