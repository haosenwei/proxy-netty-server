#!/bin/bash
sh test/bin/startup.sh
mvn clean package
java -jar target/proxy-netty-server-1.0-SNAPSHOT.jar
