# CloudProject

The purpose of this product is to provide a robust and distributed framework for realtime extraction, processing along with streamlined analytics on data from various e-commerce websites.

We built this system using the components Scrapy, Apache Kafka, Elasticsearch and Kibana. Each individual component is capable of demonstrating high scalability with large volumes of data within reasonable low latencies.

Main Objectives of the project are

1. Selecting the targeted websites and extracting the required data through configured Xpaths using Scrapy.

2. Formatting the extracted data into JSON. 

3. Pushing the JSON data using Kafka Producers into Kafka Brokers as topics.

4. Reading the data from Topics using Kafka Consumers and indexing the data into Elasticsearch.

5. Showing the indexed data in Kibana from Elasticsearch in terms of user defined Dashboards

Details of each component and their features are mentioned in respective their respective readme files.
