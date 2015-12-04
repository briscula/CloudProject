package com.chennakesava.kafka;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Json_Parser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();

		try {
			String filePath = args[0];
			String filePath2 = args[1];
			//Object obj = parser.parse(new FileReader(	"/home/ramakrishna/Desktop/Data/Amazon/items.json"));
			
			Object obj = parser.parse(new FileReader(filePath));
			Set<String> asinSet = new HashSet<String>();

			JSONArray jsonArrayObject = (JSONArray) obj;
			JSONArray finalJSONArray = new JSONArray();
			for (Object object : jsonArrayObject) {
				JSONObject jsonObject = (JSONObject) object;
				Object company = (Object) jsonObject.get("Company");
				String strCompany = company.toString();
				String finalCompany = strCompany.replace("[", "").replace("]",
						"");

				if (finalCompany.equals("Amazon")) {
					Object discount = (Object) jsonObject.get("Discount");
					String strDiscount = discount.toString();
					String finalDiscount = strDiscount.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "")
							.replace("$", "");

					Object price = (Object) jsonObject.get("Price");
					String strPrice = price.toString();
					String finalPrice = strPrice.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "")
							.replace("$", "");
					if (finalPrice.contains(",")) {
						String[] finalPriceArray = finalPrice.split(",");
						finalPrice = finalPriceArray[0].replace(" ", "");
					}

					Object title = (Object) jsonObject.get("Title");
					String strTitle = title.toString();
					String finalTitle = strTitle.replace("[", "")
							.replace("]", "").replace("\"", "");

					Object asin = (Object) jsonObject.get("Asin");
					String strAsin = asin.toString();
					String finalAsin = strAsin.replace("[", "")
							.replace("]", "");
					if (strAsin.contains("/")) {
						String[] finalAsinArray = strAsin.split("/");
						finalAsin = finalAsinArray[finalAsinArray.length - 1]
								.replace(" ", "");
					}

					if (!(finalTitle.equals("") || finalPrice.equals(""))) {
						if (!asinSet.contains(finalAsin)) {
							JSONObject tempJSONObject = new JSONObject();

							tempJSONObject.put("Company", finalCompany);
							tempJSONObject.put("ASIN", finalAsin);
							tempJSONObject.put("Title", finalTitle);
							tempJSONObject.put("Price", finalPrice);
							tempJSONObject.put("Discount", finalDiscount);

							finalJSONArray.add(new JSONObject(tempJSONObject));
							asinSet.add(finalAsin);
						}
					}
				} else {
					Object discount = (Object) jsonObject.get("Discount");
					String strDiscount = discount.toString();
					String finalDiscount = strDiscount.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "");
					if (finalDiscount.contains("%")) {
						String[] finalDiscountArray = finalDiscount.split("%");
						finalDiscount = finalDiscountArray[0].replace(" ", "")
								+ "%)";
					}

					Object price = (Object) jsonObject.get("Price");
					String strPrice = price.toString();
					String finalPrice = strPrice.replace("[", "")
							.replace("]", "").replace("\\n", "")
							.replace("\\t", "").replace("\"", "")
							.replace("US ", "");
					if (finalPrice.contains(",")) {
						String[] finalPriceArray = finalPrice.split(",");
						finalPrice = finalPriceArray[0].replace(" ", "");
					}

					Object title = (Object) jsonObject.get("Title");
					String strTitle = title.toString();
					String finalTitle = strTitle.replace("[", "")
							.replace("]", "").replace("\"", "");

					Object asin = (Object) jsonObject.get("Asin");
					String strAsin = asin.toString();
					String finalAsin = strAsin.replace("[", "")
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

					if (!(finalTitle.equals("") || finalPrice.equals(""))) {
						if (!asinSet.contains(finalAsin)) {

							JSONObject tempJSONObject = new JSONObject();
							tempJSONObject.put("Company", finalCompany);
							tempJSONObject.put("ASIN", finalAsin);
							tempJSONObject.put("Title", finalTitle);
							tempJSONObject.put("Price", finalPrice);
							tempJSONObject.put("Discount", finalDiscount);

							finalJSONArray.add(new JSONObject(tempJSONObject));
							asinSet.add(finalAsin);
						}
					}
				}
			}
			FileWriter file = new FileWriter(filePath2);
			file.write(finalJSONArray.toJSONString());
			System.out
					.println("Successfully Copied JSON Object to File...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
