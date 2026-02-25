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
./doop -i ${PROJECT_DIR}/112-mockito-all-1.0.jar --platform java_7 -Xserver-logic-threshold 1000 -a context-insensitive-plusplus --reflection-classic "$@"
popd
