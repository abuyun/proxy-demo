<?php

// 代理服务器
define("PROXY_SERVER", "https://https-dyn.abuyun.com:9020");

// 隧道身份信息
define("PROXY_USER", "H01234567890123D");
define("PROXY_PASS", "0123456789012345");
define("PROXY_AUTH", PROXY_USER . ":" . PROXY_PASS);

function request($targetUrl)
{
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $targetUrl);

    curl_setopt($ch, CURLOPT_HTTPPROXYTUNNEL, true);

    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

    // 设置代理服务器
    curl_setopt($ch, CURLOPT_PROXYTYPE, CURLPROXY_HTTP);
    curl_setopt($ch, CURLOPT_PROXY, PROXY_SERVER);

    // 设置隧道验证信息
    curl_setopt($ch, CURLOPT_PROXYAUTH, CURLAUTH_BASIC);
    curl_setopt($ch, CURLOPT_PROXYUSERPWD, PROXY_AUTH);

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
