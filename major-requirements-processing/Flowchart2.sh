#!/bin/bash

for file in flowcharts2/*
do
	name=$(echo $file | sed -e 's/flowcharts2\///')
	~/node_modules/.bin/mmdc -i "$file" -o "pics/$name.png" -w 100000 -H 100000
done
