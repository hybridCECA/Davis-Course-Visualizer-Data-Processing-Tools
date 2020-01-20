#!/bin/bash

IFS=$'\n'
for line in $(cat tmp2.txt)
do
        code=$(echo $line | awk '{ print $1 }')
        url=$(echo $line | awk '{ print $2 }')
        wget -O ./data/$code $url
done

