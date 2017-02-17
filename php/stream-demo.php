<?php

// 要访问的目标页面
$targetUrl = "http://test.abuyun.com/proxy.php";
//$targetUrl = "http://proxy.abuyun.com/switch-ip";
//$targetUrl = "http://proxy.abuyun.com/current-ip";

// 代理服务器
define("PROXY_SERVER", "proxy.abuyun.com:9010");

// 隧道身份信息
define("PROXY_USER", "H01234567890123P");
define("PROXY_PASS", "0123456789012345");

$proxyAuth = base64_encode(PROXY_USER . ":" . PROXY_PASS);

$headers = implode("\r\n", [
    "Proxy-Authorization: Basic {$proxyAuth}",
    "Proxy-Switch-Ip: yes",
]);

$options = [
    "http" => [
        "proxy"  => $proxyServer,
        "header" => $headers,
        "method" => "GET",
    ],
];

$context = stream_context_create($options);

$result = file_get_contents($url, false, $context);

var_dump($result);