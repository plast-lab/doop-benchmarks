#!/usr/bin/env bash

APKTOOL=/opt/apktool/apktool
APK_DIR=debuggable-apk
MANIFEST=${APK_DIR}/AndroidManifest.xml

function unpack() {
    echo "Unpacking ${APK} into ${APK_DIR}..."
    if [ -d "${APK_DIR}" ]; then
	echo "Removing old directory '${APK_DIR}'..."
	rm -rf ${APK_DIR}
    fi
    ${APKTOOL} d ${APK} -o ${APK_DIR} ${EXTRA_APK_FLAGS}
}

function editManifest() {
    echo "Changing ${MANIFEST}..."
    sed -i -e 's/android:debuggable="false"//g' "${MANIFEST}"
    local MANIFEST_GREP=$(grep -F 'android:debuggable' ${MANIFEST} | wc -l)
    if [ "${MANIFEST_GREP}" != "0" ]; then
	echo "Manifest ${MANIFEST} already contains 'android:debuggable' entries. Press Control-C to abort, ENTER to continue."
	read
    fi
    sed -i -e 's/<application /<application android:debuggable="true" /g' "${MANIFEST}"
}

function repack() {
    echo "Repacking as ${APK_DEBUGGABLE}..."
    ${APKTOOL} b ${APK_DIR} -o "${APK_DEBUGGABLE}"
}

function sign() {
    echo "Signing as ${APK_DEBUGGABLE_SIGNED}..."
    jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ${HOME}/.android/debug.keystore "${APK_DEBUGGABLE}" androiddebugkey -storepass android -signedjar "${APK_DEBUGGABLE_SIGNED}"
}

if [ "$1" == "" ]; then
    echo "Usage: make-apk-debuggable.sh app.apk"
    echo
    echo "Produces debuggable and debuggable+signed variants of the app.apk."
    exit
fi

if [ ! -f "${APKTOOL}" ]; then
    echo "File ${APKTOOL} does not exist, please set variable APKTOOL in script."
    exit
fi

APK="$1"
APK_DEBUGGABLE="$(basename ${APK} .apk)_debuggable.apk"
APK_DEBUGGABLE_SIGNED="$(basename ${APK} .apk)_debuggable_signed.apk"

unpack
editManifest
repack
sign
