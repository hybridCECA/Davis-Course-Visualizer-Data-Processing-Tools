#!/bin/bash

for file in major_requirements2/*
do
	name=$(echo $file | sed -e 's/major_requirements2\///')
	echo $name
	java FlowChartGenerator "$file" > "flowcharts/$name.txt"
done
