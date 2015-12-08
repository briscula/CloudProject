#Scrapy Crawler

We used scrapy for data extraction from targetted websites. Scrapy has been developed in Python and is very simple, fast, powerful and robust cross platform software.

We seed the URLs to scrapy, and data is crawled from the required websites.

We have implemented parsing layer which will take specific parameters that help in parsing data. In the current use case product details and prices are parsed from crawled data of an ecommerce website then corresponding parameters are set to parse details specific to product name and price tags on crawled website

The data extracted from the scrapy spider are inserted into a json files. .

