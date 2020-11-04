package main

import (
    "net/url"
    "net/http"
    "bytes"
    "fmt"
    "io/ioutil"
)

const ProxyServer = "http-cla.abuyun.com:9030"

type ProxyAuth struct {
    License string
    SecretKey string
}

func (p ProxyAuth) ProxyClient() http.Client {
    proxyURL, _ := url.Parse("http://" + p.License + ":" + p.SecretKey + "@" + ProxyServer)
    return http.Client{Transport: &http.Transport{Proxy:http.ProxyURL(proxyURL)}}
}

func main()  {
    targetURI := "http://test.abuyun.com"
    //targetURI := "http://proxy.abuyun.com/switch-ip"
    //targetURI := "http://proxy.abuyun.com/current-ip"

    // 初始化 proxy http client
    client := ProxyAuth{License: "H01234567890123C", SecretKey: "0123456789012345"}.ProxyClient()

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