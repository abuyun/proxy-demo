#!/bin/bash

javac -Djava.ext.dirs=./lib HttpClient4xProxyDemo.java

java -Djava.ext.dirs=./lib HttpClient4xProxyDemo
