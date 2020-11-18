#! -*- encoding:utf-8 -*-

SPIDER_MODULES = ['xbot.spiders']
NEWSPIDER_MODULE = 'xbot.spiders'
DEFAULT_ITEM_CLASS = 'xbot.items.TestItem'

ITEM_PIPELINES = {}

COOKIES_ENABLED=True

DOWNLOAD_DELAY=3

DOWNLOADER_MIDDLEWARES = {
    'scrapy.downloadermiddlewares.retry.RetryMiddleware': 90,
    'xbot.middlewares.ProxyMiddleware': 110,
}
