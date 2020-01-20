#!/bin/bash

wget -O subject_codes https://ucdavis.pubs.curricunet.com/Catalog/courses-subject-code
cat subject_codes | grep "/Catalog/" | grep id=\"bodytop\" | sed -e 's/\/Catalog\//\n\/Catalog\//g' | sed -e 's/courses-sc/courses-sc\n/g' | grep "/Catalog/" > tmp1.txt

IFS=$'\n'
for line in $(cat tmp1.txt)
do
	code=$(echo $line | sed -e 's/\/Catalog\///g' | sed -e 's/-courses-sc//g' | tr '[:lower:]' '[:upper:]')
	echo "$code https://ucdavis.pubs.curricunet.com$line" >> tmp2.txt
done

mkdir data

for line in $(cat tmp2.txt)
do
	code=$(echo $line | awk '{ print $1 }')
	url=$(echo $line | awk '{ print $2 }')
	wget -O ./data/$code $url
done
