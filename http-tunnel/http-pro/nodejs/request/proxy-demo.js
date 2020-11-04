"use strict";

const request = require("request");

// 要访问的目标页面
const targetUrl = "http://test.abuyun.com";
//const targetUrl = "http://proxy.abuyun.com/switch-ip";
//const targetUrl = "http://proxy.abuyun.com/current-ip";

// 代理服务器
const proxyHost = "http-pro.abuyun.com";
const proxyPort = 9010;

// 代理隧道验证信息
const proxyUser = "H01234567890123P";
const proxyPass = "0123456789012345";

const proxyUrl = "http://" + proxyUser + ":" + proxyPass + "@" + proxyHost + ":" + proxyPort;

const proxiedRequest = request.defaults({"proxy": proxyUrl});

const options = {
    url     : targetUrl,
    headers : {
        "Proxy-Switch-Ip" : "yes"
    }
};

proxiedRequest
    .get(options, function (err, res, body) {
        console.log("got response: " + res.statusCode);
    })
    .on("error", function (err) {
        console.log(err);
    })
;
