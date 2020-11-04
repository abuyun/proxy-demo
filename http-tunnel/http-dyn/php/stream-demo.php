<?php

// 要访问的目标页面
$targetUrl = "http://test.abuyun.com";

// 代理服务器
define("PROXY_SERVER", "http-dyn.abuyun.com:9020");

// 隧道身份信息
define("PROXY_USER", "H01234567890123D");
define("PROXY_PASS", "0123456789012345");

$proxyAuth = base64_encode(PROXY_USER . ":" . PROXY_PASS);

$headers = implode("\r\n", [
    "Proxy-Authorization: Basic {$proxyAuth}",
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