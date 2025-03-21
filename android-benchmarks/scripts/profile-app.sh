#!/usr/bin/env bash

SCRIPT_DIR=$(dirname "$0")

if [ "$1" == "" ] || [ "$2" == "" ] || [ "$3" == "" ]; then
    echo "Usage: profile-app.sh app.apk package activity"
    exit
fi

APK="$1"

if [ "${ANDROID_PLATFORM_TOOLS}" == "" ] || [ ! -d "${ANDROID_PLATFORM_TOOLS}" ]; then
    echo "'${ANDROID_PLATFORM_TOOLS}' does not exist, please set environment variable ANDROID_PLATFORM_TOOLS."
    exit
fi

echo "Uploading busybox..."
${ANDROID_PLATFORM_TOOLS}/adb push ${SCRIPT_DIR}/busybox-android /data/local/tmp
echo "Installing ${APK}..."
${ANDROID_PLATFORM_TOOLS}/adb install -f "${APK}"
echo "Taking HPROF snapshot..."
NO_MONKEY=1 PS="/data/local/tmp/busybox-android ps" ${SCRIPT_DIR}/get_heap_dump.sh $2 $3
