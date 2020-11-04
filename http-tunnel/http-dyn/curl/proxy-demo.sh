#!/usr/bin/env bash

# 通过隧道请求目标URL
curl -x "http://http-dyn.abuyun.com:9020" --proxy-basic --proxy-user H01234567890123D:0123456789012345 http://test.abuyun.com
