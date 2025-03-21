#!/bin/bash

tempName=temp`date +%NS`

tempFile1=$tempName.1.txt
tempFile2=$tempName.2.txt

sort -u $1 > $tempFile1
sort -u $2 > $tempFile2

echo "V1:" `wc -l < $tempFile1`
echo "V2:" `wc -l < $tempFile2`
echo "V1 - V2:" `comm -2 -3 $tempFile1 $tempFile2 | wc -l`

\rm $tempFile1 $tempFile2
