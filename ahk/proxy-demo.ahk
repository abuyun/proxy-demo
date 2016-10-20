HTTPREQUEST_PROXYSETTING_PROXY := 2
HTTPREQUEST_SETCREDENTIALS_FOR_PROXY := 1

;~ 代理服务器
proxyHost := "proxy.abuyun.com:9010"

;~ 代理隧道验证信息
proxyUser := "H01234567890123P"
proxyPass := "0123456789012345"

;~ 要访问的目标页面
targetUrl := "http://test.abuyun.com/proxy.php"

whr := ComObjCreate("WinHttp.WinHttpRequest.5.1")
whr.Open("GET", targetUrl, true)

;~ 模拟curl的ua，方便测试
whr.SetRequestHeader("User-Agent", "curl/7.41.0")

;~ 设置代理服务器
whr.SetProxy(HTTPREQUEST_PROXYSETTING_PROXY, proxyHost)

;~ 设置代理隧道验证信息
whr.SetCredentials(proxyUser, proxyPass, HTTPREQUEST_SETCREDENTIALS_FOR_PROXY)

whr.Send()
whr.WaitForResponse()

MsgBox % whr.ResponseText
