#!/usr/bin/env bash
#
# Builds the native test for Android.
#
# Usage:
#
# 1. Create a toolchain in ./toolchain:
#    android-ndk/build/tools/make-standalone-toolchain.sh  --arch=arm --abis=21 --install-dir=toolchain
#
# 2. Set the environment variable ANDROID_SDK to point to the location of the Android SDK:
#    export ANDROID_SDK=/usr/data/gfour/Android/Sdk
#
# 3. Compile all sources (C and Java) and build the .apk:
#    TOOLCHAIN=$(realpath toolchain) ./make-jni-android.sh
#

if [ "${TOOLCHAIN}" == "" ]; then
    echo "Plase create a toolchain:"
    echo "  android-ndk/build/tools/make-standalone-toolchain.sh --arch=arm --abis=21 --install-dir=toolchain"
    exit
else
    echo "Using toolchain: ${TOOLCHAIN}"
fi

export JNI_INCLUDE_PATH=${TOOLCHAIN}/sysroot/usr/include
export EXTRA_INCLUDE_PATH=${TOOLCHAIN}/sysroot/usr/include

function build() {
    local target_host="$1"
    export ARCH="$2"

    ########### Taken from https://developer.android.com/ndk/guides/standalone_toolchain

    # Add the standalone toolchain to the search path.
    export PATH=$PATH:${TOOLCHAIN}/bin

    # Tell configure what tools to use.
    export AR=$target_host-ar
    export AS=$target_host-clang
    export CC=$target_host-clang
    export CXX=$target_host-clang++
    export LD=$target_host-ld
    export STRIP=$target_host-strip

    # Tell configure what flags Android requires.
    export CFLAGS="-fPIE -fPIC"
    export LDFLAGS="-pie"

    ###########

    echo "* Building native library: target_host=${target_host}, ARCH=${ARCH}"
    make jni
}

build 'aarch64-linux-android21' 'aarch64'
build 'arm-linux-androideabi' 'arm'

echo "* Building .apk..."
make jar

if [ "${ANDROID_SDK}" == "" ]; then
    echo "Please set environment variable ANDROID_SDK to point to Android SDK location to add the library to the APK."
else
    CUR_DIR=$(realpath .)
    pushd build/classes/java/main
    DX=${ANDROID_SDK}/build-tools/28.0.2/dx
    ${DX} --dex --min-sdk-version=25 --output=${CUR_DIR}/Main.apk HelloJNI.class
    popd

    RES_DIR="src/main/resources"
    ARCH="arm"
    echo "Using ARCH=${ARCH}"
    ${ANDROID_SDK}/platforms/android-6/tools/aapt add Main.apk ${RES_DIR}/lib/${ARCH}/libhello.so
    echo "Contents of APK:"
    unzip -l Main.apk
fi
