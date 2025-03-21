#!/bin/bash

tempName=temp`date +%NS`

tempFile1=$tempName.1.txt
tempFile2=$tempName.2.txt

sort -u $1 > $tempFile1
sort -u $2 > $tempFile2

# print lines in both files
comm -1 -2 $tempFile1 $tempFile2

\rm $tempFile1 $tempFile2
