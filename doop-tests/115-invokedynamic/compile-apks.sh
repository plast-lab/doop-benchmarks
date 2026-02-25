#!/usr/bin/env bash

function compileApk() {
    local ID="$1"
    local OPT="$2"

    pushd ${TMP_DIR} &> /dev/null
    TMP_APK=$(mktemp --suffix=.apk)
    ${DX} --dex --min-sdk-version=${SDK} ${OPT} --output=${TMP_APK} *.class
    popd &> /dev/null

    local OUT_APK=Main-invokedynamic-${SDK}-${ID}.apk
    mv ${TMP_APK} ${OUT_APK}
    echo "Generated ${OUT_APK}"
}

if [ "${DX}" == "" ]; then
    if [ "${ANDROID_SDK}" != "" ]; then
        DX="${ANDROID_SDK}/build-tools/28.0.2/dx"
        if [ ! -f "${DX}" ]; then
            echo "Default dx in ${DX} does not exist, please set DX environment variable."
            exit
        fi
    fi
fi

TMP_DIR=$(mktemp -d)

cp build/classes/main/*.class ${TMP_DIR}
cp InvokedynamicClass.class ${TMP_DIR}

for SDK in 26 27; do
    compileApk "unoptimized" "--no-optimize"
    compileApk "optimized" ""
done

rm -rf ${TMP_DIR}
