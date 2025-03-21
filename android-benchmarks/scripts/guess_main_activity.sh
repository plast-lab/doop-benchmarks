#!/usr/bin/env bash
adb shell pm dump $1 | grep -A 1 MAIN | grep $1\\/ |  awk '{print $2}'
