#! -*- encoding:utf-8 -*-

from urllib import request

# 要访问的目标页面
targetUrl = "http://test.abuyun.com/proxy.php"
#targetUrl = "http://proxy.abuyun.com/switch-ip"
#targetUrl = "http://proxy.abuyun.com/current-ip"

# 代理服务器
proxyHost = "proxy.abuyun.com"
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

proxy_handler = request.ProxyHandler({
    "http"  : proxyMeta,
    "https" : proxyMeta,
})

#auth = request.HTTPBasicAuthHandler()
#opener = request.build_opener(proxy_handler, auth, request.HTTPHandler)

opener = request.build_opener(proxy_handler)

# opener.addheaders = [("Proxy-Switch-Ip", "yes")]
request.install_opener(opener)
resp = request.urlopen(targetUrl).read()

print (resp)