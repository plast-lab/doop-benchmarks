#!/usr/bin/env bash

OUT_DIR=lambdas

rm -rf ${OUT_DIR}
mkdir ${OUT_DIR}
../gradlew run
