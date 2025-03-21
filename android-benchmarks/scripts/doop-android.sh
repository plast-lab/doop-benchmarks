#!/bin/bash

./doop --android \
       --gen-proguard-keep \
       --souffle-jobs 8 \
       --platform android_25_fulljars \
       --cache \
       --timeout 360 \
       --Xextra-metrics \
       --Xsymlink-cached-facts \
       --simulate-native-returns \
       --reflection-classic \
       $@

# --Xserver-logic \
# --Xstats-none \
