from scrapy.contrib.spiders import CrawlSpider, Rule
from scrapy.contrib.linkextractors.sgml import SgmlLinkExtractor
from scrapy.selector import HtmlXPathSelector
from amazon_sample.items import AmazonSampleItem

class MySpider(CrawlSpider):
    name = "amazon"
    allowed_domains = ["amazon.com"]
    start_urls = ["http://www.amazon.com/Levis-Big-Tall-Athletic-Straight-Monks/dp/B0042C63A8/ref=sr_1_8?s=photo&ie=UTF8&qid=1448980889&sr=1-8&keywords=jeans+for+men"]

    rules = (Rule(SgmlLinkExtractor(allow=(r"/gp/"), restrict_xpaths=('//a[@class="a-link-normal"]',)), callback="parse_items", follow= True),
             Rule(SgmlLinkExtractor(allow=(r"/gp/"), restrict_xpaths=('//a[@class="a-size-base a-link-normal"]',)), callback="parse_items", follow= True),Rule(SgmlLinkExtractor(allow=(r"/dp/"), restrict_xpaths=('//a[@class="a-link-normal"]',)), callback="parse_items", follow= True),
             Rule(SgmlLinkExtractor(allow=(r"/dp/"), restrict_xpaths=('//a[@class="a-size-base a-link-normal"]',)), callback="parse_items", follow= True),)

    def parse_items(self, response):
        hxs = HtmlXPathSelector(response)
        title = hxs.xpath("//span[@class='a-size-large']")
        price = hxs.xpath("//span[@class='a-size-medium a-color-price']")
        discount = hxs.xpath("//td[@class='a-span12 a-color-price a-size-base']")
        category = hxs.xpath("//a[@class='a-link-normal a-color-tertiary']")
        items = []
        item = AmazonSampleItem()
        item["Asin"] = str(response.request.url)
        item["Discount"] = discount.select("text()").extract()
        item["Title"] = title.select("text()").extract()
        item["Price"] = price.select("text()").extract()
        item["Category"] = category.select("text()").extract()
        item["Company"] = "Amazon"
        items.append(item)
        return items
