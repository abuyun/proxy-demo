#! -*- encoding:utf-8 -*-

from scrapy.spiders import Spider
from scrapy.selector import Selector

from xbot.items import TestItem

class AbuyunSpider(Spider):
    name = "abuyun"
    allowed_domains = ["test.abuyun.com"]
    start_urls = [
        "http://test.abuyun.com",
    ]

    def parse(self, response):
        item = TestItem()
        item['text'] = response

        items = [item]

        return items
