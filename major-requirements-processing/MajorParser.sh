#!/bin/bash

dir=./data
tabs=./tabs

for file in $dir/*
do
	name=$(echo $file | sed -e 's/.\/data\///')
	xmllint --html --xpath "//ul[@class='nav nav-tabs']"  $file 2>/dev/null | sed -e 's/a href="#/\n/g' | sed -e 1d -e 's/">/ /' -e 's/<\/a>.*$//g' | grep -v "Courses" | grep -v "Information" | sed -e 's/\&amp;/\&/g' > $tabs/$name
done
