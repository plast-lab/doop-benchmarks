#!/bin/bash

tempName=temp`date +%NS`

tempFile1=$tempName.1.txt
tempFile2=$tempName.2.txt

sort -u $1 > $tempFile1
sort -u $2 > $tempFile2

# print only lines in file 1 that are not in 2.
# typically used for sanity checking, in cases when file 2 should (mostly) be a superset of file 1.
comm -2 -3 $tempFile1 $tempFile2

\rm $tempFile1 $tempFile2
