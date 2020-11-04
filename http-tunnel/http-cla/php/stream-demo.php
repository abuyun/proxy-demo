<?php

// 要访问的目标页面
$targetUrl = "http://test.abuyun.com";
//$targetUrl = "http://proxy.abuyun.com/switch-ip";
//$targetUrl = "http://proxy.abuyun.com/current-ip";

// 代理服务器
define("PROXY_SERVER", "http-cla.abuyun.com:9030");

// 隧道身份信息
define("PROXY_USER", "H01234567890123C");
define("PROXY_PASS", "0123456789012345");

$proxyAuth = base64_encode(PROXY_USER . ":" . PROXY_PASS);

$headers = implode("\r\n", [
    "Proxy-Authorization: Basic {$proxyAuth}",
    "Proxy-Switch-Ip: yes",
]);

$options = [
    "http" => [
        "proxy"  => PROXY_SERVER,
        "header" => $headers,
        "method" => "GET",
    ],
];

$context = stream_context_create($options);

$result = file_get_contents($targetUrl, false, $context);

var_dump($result);