#!/usr/bin/env bash

# 切换隧道IP
curl -x "http://http-cla.abuyun.com:9030" --proxy-basic --proxy-user H01234567890123C:0123456789012345 http://proxy.abuyun.com/switch-ip

# 查看隧道当前IP
curl -x "http://http-cla.abuyun.com:9030" --proxy-basic --proxy-user H01234567890123C:0123456789012345 http://proxy.abuyun.com/current-ip

# 通过隧道请求目标URL
curl -x "http://http-cla.abuyun.com:9030" --proxy-basic --proxy-user H01234567890123C:0123456789012345 http://test.abuyun.com
