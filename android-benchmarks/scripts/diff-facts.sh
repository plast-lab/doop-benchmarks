#!/bin/bash

tempName=temp`date +%NS`

tempFile1=$tempName.1.txt
tempFile2=$tempName.2.txt
tempFileOut=$tempName.out.txt

for file in `ls $1/`;
do
    echo "########################"
    echo $file

    sort -u $1/$file > $tempFile1
    sort -u $2/$file > $tempFile2

    set-diff-stats.sh $tempFile1 $tempFile2 > $tempFileOut
    cat $tempFileOut
       # leave open for reverting to more complex processing in the future
done

\rm $tempFile1 $tempFile2 $tempFileOut
