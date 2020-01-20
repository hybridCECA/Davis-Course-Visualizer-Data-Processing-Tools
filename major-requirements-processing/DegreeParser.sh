#!/bin/bash

data=./data
tabs=./tabs

for file in data/*
do
	name=$(echo $file | sed -e 's/data\///')

	IFS=$'\n'
	for line in $(cat $tabs/$name)
	do
		id=$(echo $line | awk '{ print $1 }')
		name=$(echo $line | cut -d' ' -f2- )
		echo $file $id $name
		
		xmllint --html --xpath "//div[@id='$id']" $file 2>/dev/null > "./data2/$name"

	done
done
