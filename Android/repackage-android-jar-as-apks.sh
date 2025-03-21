#!/usr/bin/env bash
#
# This script converts an android.jar platform archive to a set of APK
# files, one per top-level package.

# BUILD_TOOLS=28.0.2
BUILD_TOOLS=28.0.3

# OPTIMIZE=
OPTIMIZE='--no-optimize'

JAR_CONTENTS='jar-contents'

if [ "$1" == "" ]; then
    echo "Usage: repackage-android-jar-as-apks.sh path/to/android.jar"
    exit
else
    ANDROID_JAR=$(realpath "$1")
fi

if [ "${ANDROID_SDK}" == "" ]; then
    echo "Environment variable ANDROID_SDK is not set."
    exit
fi

if [ -d "${JAR_CONTENTS}" ]; then
    echo "Directory '${JAR_CONTENTS}' exists, delete it and run again this script."
    exit
else
    mkdir ${JAR_CONTENTS}
fi

echo "Settings:"
echo "ANDROID_SDK = ${ANDROID_SDK}"
echo "BUILD_TOOLS = ${BUILD_TOOLS}"

pushd ${JAR_CONTENTS}

jar xf ${ANDROID_JAR}

for d in $(find android/* -type d -maxdepth 0) com dalvik java javax jdk libcore org sun
do
    echo "Processing: ${d}"
    dFixed=$(echo "$d" | sed -e 's/\//_/g')
    rm -f ${dFixed}.jar ${dFixed}.apk
    jar cf ${dFixed}.jar $(find ${d} -name "*.class")
    JAVA_TOOL_OPTIONS="-Xmx400g -Xss20m" ${ANDROID_SDK}/build-tools/${BUILD_TOOLS}/dx --dex ${OPTIMIZE} --debug --verbose --core-library --multi-dex --min-sdk-version=26 --output=${dFixed}.apk ${dFixed}.jar
done
popd
