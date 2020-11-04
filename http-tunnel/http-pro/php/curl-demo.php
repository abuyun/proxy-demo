<?php

// 切换IP
define("PROXY_SWITCH_IP_URL", "http://proxy.abuyun.com/switch-ip");
// 当前IP
define("PROXY_CURRENT_IP_URL", "http://proxy.abuyun.com/current-ip");

// 代理服务器
define("PROXY_SERVER", "http://http-pro.abuyun.com:9010");

// 隧道身份信息
define("PROXY_USER", "H01234567890123P");
define("PROXY_PASS", "0123456789012345");
define("PROXY_AUTH", PROXY_USER . ":" . PROXY_PASS);

function request($targetUrl)
{
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $targetUrl);

    curl_setopt($ch, CURLOPT_HTTPPROXYTUNNEL, false);

    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

    // 设置代理服务器
    curl_setopt($ch, CURLOPT_PROXYTYPE, CURLPROXY_HTTP);
    curl_setopt($ch, CURLOPT_PROXY, PROXY_SERVER);

    // 设置隧道验证信息
    curl_setopt($ch, CURLOPT_PROXYAUTH, CURLAUTH_BASIC);
    curl_setopt($ch, CURLOPT_PROXYUSERPWD, PROXY_AUTH);

    // 加此请求头会让每个请求都自动切换IP
    curl_setopt ($ch, CURLOPT_HTTPHEADER, [
        "Proxy-Switch-Ip: yes",
    ]);

    curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 3);
    curl_setopt($ch, CURLOPT_TIMEOUT, 5);

    curl_setopt($ch, CURLOPT_HEADER, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $result = curl_exec($ch);
    //$info = curl_getinfo($ch);

    $error = [];

    if (!$result)
    {
        $error["code"] = curl_errno($ch);
        $error["msg"]  = curl_error($ch);
    }

    curl_close($ch);

    print_r(empty($error) ? $result : $error);
}

function main()
{
    // 要访问的目标页面
    $targetUrl = "http://test.abuyun.com";
    //$targetUrl = "https://test.abuyun.com";

    request($targetUrl);
}

main();
