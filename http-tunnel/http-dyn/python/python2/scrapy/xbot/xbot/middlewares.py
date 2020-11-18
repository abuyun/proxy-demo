#! -*- encoding:utf-8 -*-

import base64
from scrapy.downloadermiddlewares.httpproxy import HttpProxyMiddleware

# 代理服务器
proxyServer = "http://http-dyn.abuyun.com:9020"

# 隧道身份信息
proxyUser = "H01234567890123D"
proxyPass = "0123456789012345"
proxyAuth = "Basic " + base64.urlsafe_b64encode(proxyUser + ":" + proxyPass)

class ProxyMiddleware(HttpProxyMiddleware):
    proxies = {}

    def __init__(self, auth_encoding='latin-1'):
        self.auth_encoding = auth_encoding

        self.proxies[proxyServer] = proxyUser + proxyPass

    def process_request(self, request, spider):
        request.meta["proxy"] = proxyServer

        request.headers["Proxy-Authorization"] = proxyAuth
