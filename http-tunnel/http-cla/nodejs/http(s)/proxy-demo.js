"use strict";

const http = require("http");
const url = require("url");

const zlib = require('zlib');
const gunzipStream = zlib.createGunzip();

// 要访问的目标页面
const targetUrl = "http://test.abuyun.com";
//const targetUrl = "http://proxy.abuyun.com/switch-ip";
//const targetUrl = "http://proxy.abuyun.com/current-ip";

const urlParsed = url.parse(targetUrl);

// 代理服务器
const proxyHost = "http-cla.abuyun.com";
const proxyPort = 9030;

// 代理隧道验证信息
const proxyUser = "H01234567890123C";
const proxyPass = "0123456789012345";

const proxyAuth = new Buffer(proxyUser + ":" + proxyPass).toString("base64");

const options = {
    hostname : proxyHost,
    port     : proxyPort,
    path     : targetUrl,
    method   : "GET",
    headers  : {
        "Host"                : urlParsed.hostname,
        "Port"                : urlParsed.port,
        "Accept-Encoding"     : "gzip",
        "Proxy-Authorization" : "Basic " + proxyAuth
    }
};

http
    .request(options, function (res) {
        console.log("got response: " + res.statusCode);
        console.log(res.headers);

        if(res.headers.hasOwnProperty('content-encoding') && res.headers['content-encoding'].indexOf('gzip') != -1) {
            res
                .pipe(gunzipStream)
                .on("data",function(data) {
                    console.log(data.toString());
                })
                .on("end",function() {
                    //
                })
            ;
        } else {
            res.on('data', function (data) {
                console.log(data.toString());
            });
        }
    })
    .on("error", function (err) {
        console.log(err);
    })
    .end()
;
