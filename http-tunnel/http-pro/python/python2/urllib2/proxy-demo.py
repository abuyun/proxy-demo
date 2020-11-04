#! -*- encoding:utf-8 -*-

import urllib2

# 要访问的目标页面
targetUrl = "http://test.abuyun.com"
#targetUrl = "http://proxy.abuyun.com/switch-ip"
#targetUrl = "http://proxy.abuyun.com/current-ip"

# 代理服务器
proxyHost = "http-pro.abuyun.com"
proxyPort = "9010"

# 代理隧道验证信息
proxyUser = "H01234567890123P"
proxyPass = "0123456789012345"

proxyMeta = "http://%(user)s:%(pass)s@%(host)s:%(port)s" % {
    "host" : proxyHost,
    "port" : proxyPort,
    "user" : proxyUser,
    "pass" : proxyPass,
}

proxy_handler = urllib2.ProxyHandler({
    "http"  : proxyMeta,
    "https" : proxyMeta,
})

opener = urllib2.build_opener(proxy_handler)

#opener.addheaders = [("Proxy-Switch-Ip", "yes")]
urllib2.install_opener(opener)
resp = urllib2.urlopen(targetUrl).read()

print resp