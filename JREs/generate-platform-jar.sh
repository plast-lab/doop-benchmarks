#!/usr/bin/env bash

# This helper script generates the platform JAR file for Java 9+.

if [ "$1" == "" ] || [ "$2" == "" ]; then
    echo "Usage: generate-platform-jar.sh JAVA_MODS_DIRECTORY OUTPUT_JAR"
    echo ""
    echo "  JAVA_MODS_DIRECTORY  The directory of the Java modules (e.g., /usr/lib/jvm/java-11-openjdk-amd64/jmods)"
    echo "  OUTPUT_JAR           The name of the output platform JAR file."
    exit
fi

JAVA_MODS_DIRECTORY="$1"
OUTPUT_JAR="$2"
PLATFORM_DIR='platform-classes'

if [ -d "${PLATFORM_DIR}" ]; then
    echo "Work directory exists, remove and run again script: ${PLATFORM_DIR}"
    exit
else
    mkdir "${PLATFORM_DIR}"
fi

# Extract modules using 'jmod'.
pushd ${PLATFORM_DIR} &> /dev/null
for JMOD in ${JAVA_MODS_DIRECTORY}/*.jmod; do
    echo "Processing module: ${JMOD}"
    jmod extract --dir . ${JMOD}
done

# Show additional .jar files (that will not be included in the platform archive).
echo "Extracted .jar files (will not be included): "
find . -name "*.jar"

echo "Extracted .class files: "$(find . -name "*.class" | wc -l)
CLASSES_DIR='classes'
echo "Extracted .class files that will be included (under /${CLASSES_DIR}): "$(find ${CLASSES_DIR} -name "*.class" | wc -l)

rm -f ${OUTPUT_JAR}
jar -cf ${OUTPUT_JAR} -C ${CLASSES_DIR} .
echo "Output: "$(realpath ${OUTPUT_JAR})

popd &> /dev/null
