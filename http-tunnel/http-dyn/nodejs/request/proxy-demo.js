"use strict";

const request = require("request");

// 要访问的目标页面
const targetUrl = "http://test.abuyun.com";

// 代理服务器
const proxyHost = "http-dyn.abuyun.com";
const proxyPort = 9020;

// 代理隧道验证信息
const proxyUser = "H01234567890123D";
const proxyPass = "0123456789012345";

const proxyUrl = "http://" + proxyUser + ":" + proxyPass + "@" + proxyHost + ":" + proxyPort;

const proxiedRequest = request.defaults({"proxy": proxyUrl});

const options = {
    url     : targetUrl,
    headers : {
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
