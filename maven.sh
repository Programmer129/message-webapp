#!/usr/bin/env bash
cd ..
source /etc/environmet
cd demo
maven clean install -DskipTests=true
