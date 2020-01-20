#!/bin/bash

for file in flowcharts2/*
do
	name=$(echo $file | sed -e 's/flowcharts2\///')
	cat "$file" | tr -d '()' > "flowcharts4/$name"
done
