#! -*- encoding:utf-8 -*-

import base64
from scrapy.downloadermiddlewares.httpproxy import HttpProxyMiddleware

# 代理服务器
proxyServer = "http://http-cla.abuyun.com:9030"

# 隧道身份信息
proxyUser = b"H01234567890123C"
proxyPass = b"0123456789012345"
proxyAuth = "Basic " + base64.b64encode(proxyUser + b":" + proxyPass).decode()

class ProxyMiddleware(HttpProxyMiddleware):
    proxies = {}

    def __init__(self, auth_encoding='latin-1'):
        self.auth_encoding = auth_encoding

        self.proxies[proxyServer] = proxyUser + proxyPass

    def process_request(self, request, spider):
        request.meta["proxy"] = proxyServer

        request.headers["Proxy-Authorization"] = proxyAuth
