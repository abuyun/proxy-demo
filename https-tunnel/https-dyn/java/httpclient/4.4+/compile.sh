#!/bin/bash

javac -cp "./lib/*" HttpClient4xProxyDemo.java

java -cp "./lib/*:." HttpClient4xProxyDemo
