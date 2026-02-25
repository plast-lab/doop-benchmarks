#!/usr/bin/env bash

MERGE=${HOME}/Downloads/clue/bytecode-utils/merge-zips.sh
DIR=/tmp/5220869481077116410
SRC=mockito-core-2.10.0-sources.jar
JSON=mockito-core-2.10.0-json.jar
TARGET=fat-doop-project

cp ${DIR}/${SRC} ${SRC}
cp ${DIR}/json.jar ${JSON}

${MERGE} ${SRC} ./build/libs/113-mockito-byte-buddy-1.0-sources.jar ${TARGET}/113-mockito-byte-buddy-1.0-sources.jar
${MERGE} ${JSON} ./build/scavenge/jcplugin.zip ${TARGET}/jcplugin.zip
