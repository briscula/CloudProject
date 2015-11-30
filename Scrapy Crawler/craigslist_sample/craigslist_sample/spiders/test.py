from scrapy.contrib.spiders import CrawlSpider, Rule
from scrapy.contrib.linkextractors.sgml import SgmlLinkExtractor
from scrapy.selector import HtmlXPathSelector
from craigslist_sample.items import CraigslistSampleItem

class MySpider(CrawlSpider):
    name = "amazon"
    allowed_domains = ["amazon.com"]
    start_urls = ["http://www.amazon.com/gp/product/B00WK55BQ6?redirect=true&ref_=s9_simh_gw_p421_d0_i4"]

    rules = (Rule(SgmlLinkExtractor(allow=(r"/gp/"), restrict_xpaths=('//a[@class="a-link-normal"]',)), callback="parse_items", follow= True),
             Rule(SgmlLinkExtractor(allow=(r"/gp/"), restrict_xpaths=('//a[@class="a-size-base a-link-normal"]',)), callback="parse_items", follow= True),Rule(SgmlLinkExtractor(allow=(r"/dp/"), restrict_xpaths=('//a[@class="a-link-normal"]',)), callback="parse_items", follow= True),
             Rule(SgmlLinkExtractor(allow=(r"/dp/"), restrict_xpaths=('//a[@class="a-size-base a-link-normal"]',)), callback="parse_items", follow= True),)

    def parse_items(self, response):
        hxs = HtmlXPathSelector(response)
        title = hxs.xpath("//span[@class='a-size-large']")
        price = hxs.xpath("//span[@class='a-size-medium a-color-price']")
#        asin = hxs.xpath("//ul/li")
        items = []
        item = CraigslistSampleItem()
#        for asin in asin:
#            val = asin.select("b/text()").extract
#            res = "ASIN: "
#	    asn = asin.select("text()").extract
#            if val == res:
#            item["Asin"] = val
        item["Title"] = title.select("text()").extract()
        item["Price"] = price.select("text()").extract()
        item["Company"] = "Amazon"
        items.append(item)
        return items
