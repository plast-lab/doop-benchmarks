#!/bin/bash

if [ "${DOOP_HOME}" == "" ]; then
    echo "Plase set DOOP_HOME."
    exit
fi

if [ "${PROJECT_DIR}" == "" ]; then
    PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]-$0}" )" && pwd )"
    echo "Using PROJECT_DIR=${PROJECT_DIR}"
fi

pushd $DOOP_HOME
# ./doop -i ${PROJECT_DIR}/113-mockito-byte-buddy-1.0.jar ${PROJECT_DIR}/mockito-core-2.10.0.jar ${PROJECT_DIR}/byte-buddy-1.7.4.jar ${PROJECT_DIR}/byte-buddy-agent-1.7.4.jar ${PROJECT_DIR}/objenesis-2.6.jar --platform java_8 -Xserver-logic-threshold 1000 -a context-insensitive-plusplus --reflection-classic --heapdl ${PROJECT_DIR}/113-mockito.hprof "$@"
./doop -i ${PROJECT_DIR}/113-mockito-byte-buddy-all-1.0.jar --platform java_8 -Xserver-logic-threshold 1000 -a context-insensitive-plusplus --reflection-classic "$@"
popd
