package main

import (
    "net/url"
    "net/http"
    "bytes"
    "fmt"
    "io/ioutil"
)

const AbuyunProxyHost = "proxy.abuyun.com:9010"

type AbuyunProxy struct {
    AppID string
    AppSecret string
}

func (p AbuyunProxy) ProxyClient() http.Client {
    proxyURL, _ := url.Parse("http://"+p.AppID+":"+p.AppSecret+"@"+AbuyunProxyHost)
    return http.Client{Transport: &http.Transport{Proxy:http.ProxyURL(proxyURL)}}
}


func main()  {
    targetURI := "http://www.abuyun.com/test/proxy.php"
    //targetURI := "http://www.abuyun.com/switch-ip"
    //targetURI := "http://www.abuyun.com/current-ip"
    //targetURI := "https://www.baidu.com"

    // 初始化 proxy http client
    client := AbuyunProxy{AppID: "your appid", AppSecret: "your appsecret"}.ProxyClient()

    request, _ := http.NewRequest("GET", targetURI, bytes.NewBuffer([] byte(``)))

    // 切换IP (只支持 HTTP)
    request.Header.Set("Proxy-Switch-Ip", "yes")

    response, err := client.Do(request)

    if err != nil {
        panic("failed to connect: " + err.Error())
    } else {
        bodyByte, err := ioutil.ReadAll(response.Body)
        if err != nil {
            fmt.Println("读取 Body 时出错", err)
            return
        }
        response.Body.Close()
        body := string(bodyByte)

        fmt.Println("Response Status:", response.Status)
        fmt.Println("Response Header:", response.Header)
        fmt.Println("Response Body:\n", body)
    }

}