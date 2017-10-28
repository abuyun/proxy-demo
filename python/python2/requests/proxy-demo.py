#! -*- encoding:utf-8 -*-

import requests

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

proxies = {
    "http"  : proxyMeta,
    "https" : proxyMeta,
}

resp = requests.get(targetUrl, proxies=proxies)

print resp.status_code
print resp.text
