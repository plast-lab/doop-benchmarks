#!/bin/bash

## Gets a splitted APK (with the .dex.jar secondary file under the
## assets dir) in the form our fact generation currently expects (no
## jar.dex files)

tempDir=temp`date +%NS`

apkFile=$1

relativeApkFile="${apkFile##*/}"

mkdir $tempDir
\cp $apkFile $tempDir
cd $tempDir
unzip $relativeApkFile
\rm $relativeApkFile
\mv `find * -name "*.dex"` .
jarDexes=`find * -name "*.dex.jar"`
for jarDex in $jarDexes;
do
    \mv $jarDex .
    jar -xvf $jarDex
done
zip ../repacked-$relativeApkFile *
cd ..
\rm -r $tempDir
