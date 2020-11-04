#!/usr/bin/env bash

# 通过隧道请求目标URL
curl -x "http://http-pro.abuyun.com:9010" --proxy-basic --proxy-user H01234567890123P:0123456789012345 http://test.abuyun.com

# 切换隧道IP
curl -x "http://http-pro.abuyun.com:9010" --proxy-basic --proxy-user H01234567890123P:0123456789012345 http://proxy.abuyun.com/switch-ip

# 查看隧道当前IP
curl -x "http://http-pro.abuyun.com:9010" --proxy-basic --proxy-user H01234567890123P:0123456789012345 http://proxy.abuyun.com/current-ip
