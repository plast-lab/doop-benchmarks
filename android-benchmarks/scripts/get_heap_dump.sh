#!/usr/bin/env bash
# set -x

if [ "$1" == "" ] || [ "$2" == "" ]
then
    echo "Usage: get_heap_dump.sh <application package> <main activity>"
    echo "Environment variables (optional):"
    echo "  ANDROID_PLATFORM_TOOLS  the location of adb/hprof-conv"
    echo "  NO_MONKEY               if set to 1, don't run Monkey"
    echo "  PS                      custom 'ps' command to use (e.g. '/data/local/tmp/busybox-android ps')"
    exit
fi

timestamp() {
  date +"%T"
}

if [ "${ANDROID_PLATFORM_TOOLS}" == "" ]
then
    ADB=adb
    HPROF_CONV=hprof-conv
else
    ADB=${ANDROID_PLATFORM_TOOLS}/adb
    HPROF_CONV=${ANDROID_PLATFORM_TOOLS}/hprof-conv
fi

set -e
echo "trying to start $1"
${ADB} shell am start --track-allocation $1/.$2
sleep 10s # HACK

echo Press ENTER when ready to take a heap dump...
read

if [ "${NO_MONKEY}" != "1" ]
then
    echo "pushing events to $1"
    ${ADB} shell monkey --throttle 2 -p $1  1024  # -c android.intent.category.LAUNCHER
fi

echo "dumping heap and waiting..."

if [ "{PS}" == "" ]; then
    echo "Using generic 'ps' from the device/emulator..."
    PS=ps
    # We assume the built-in 'ps' has the PID at position $2.
    APP_PID=$(${ADB} shell ${PS} | grep $1\$ | awk '{print $2}')
else
    # We assume a busybox 'ps' that has the PID at position $1.
    APP_PID=$(${ADB} shell ${PS} | grep $1\$ | awk '{print $1}')
fi
echo "PS=${PS}, APP_PID=${APP_PID}"

if [ "${APP_PID}" == "" ]; then
    echo "Could not find app PID, set PS to use appropriate 'ps' (such as a busybox binary uploaded to the device)."
    exit
fi

${ADB} shell am dumpheap ${APP_PID} /data/local/tmp/$1.android.hprof
sleep 25s
echo "downloading heap dump"
${ADB} pull /data/local/tmp/$1.android.hprof .
echo "converting heap..."
${HPROF_CONV} $1.android.hprof $1.hprof
echo "deleting temporary files"
rm $1.android.hprof
${ADB} shell rm /data/local/tmp/$1.android.hprof
