#!/bin/bash

INSTAGRAM_APK=com.instagram.android_10.5.1-48243323_minAPI16_x86_nodpi_apkmirror.com.apk
PINTEREST_APK=com.pinterest_6.13.0-613068_minAPI16_x86_nodpi_apkmirror.com.apk
CHROME_APK=com.android.chrome_57.0.2987.132-298713212_minAPI24_x86_nodpi_apkmirror.com.apk
PHOTOEDIT_APK=com.steam.photoeditor-1.07-www.APK4Fun.com.apk
TRANSLATE_APK=com.google.android.apps.translate_5.8.0.RC11.151331239-58001163_minAPI17_x86_nodpi_apkmirror.com.apk
ANDROIDTERM_APK=jackpal.androidterm-1.0.70-71-minAPI4.apk

BENCH_DIR=${HOME}/doop-benchmarks/android-benchmarks

function bench {
    for refl in "" "-reflection-classic"
    do
	DOOP_CMD="./doop -i ${BENCH_DIR}/$2 -a context-insensitive ${refl} -platform android_25_fulljars --timeout 500"
	set -x
	${DOOP_CMD}                                       -id $1${refl}           |& tee $1${refl}.txt
	${DOOP_CMD} --analyze-memory-dump ${BENCH_DIR}/$3 -id $1${refl}-heap-dump |& tee $1${refl}-heap-dump.txt
	set +x
    done
}

if [ "$1" == "" ]
then
    bench androidterm ${ANDROIDTERM_APK} jackpal.androidterm.hprof
    bench photoedit ${PHOTOEDIT_APK} com.steam.photoeditor.hprof
    bench translate ${TRANSLATE_APK} com.google.android.apps.translate.hprof
    bench instagram ${INSTAGRAM_APK} com.instagram.android.hprof
    bench pinterest ${PINTEREST_APK} com.pinterest.hprof
    bench chrome    ${CHROME_APK}    com.android.chrome.hprof
else
    bench $1 $2 $3
fi
