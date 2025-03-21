#!/bin/bash

## $1 : APK to insert classes.dex files into. All existing .dex/.dex.jar files are removed!!!
## $2 : directory with .dex files to insert in APK

tempDir=temp`date +%NS`

apkFile=$1
dexDir=$2

relativeApkFile="${apkFile##*/}"

mkdir $tempDir
\cp $apkFile $tempDir
cd $tempDir
unzip $relativeApkFile
\rm $relativeApkFile
\rm `find . -name "*.dex"`
\rm `find . -name "*.dex.jar"`
for dexFile in `ls $dexDir/*.dex`;
do
    \cp $dexFile .
done
zip -r ../repacked-$relativeApkFile *
cd ..
\rm -r $tempDir
