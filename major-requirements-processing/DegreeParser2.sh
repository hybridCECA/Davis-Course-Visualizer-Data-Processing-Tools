#!/bin/bash

for file in data2/*
do
	name=$(echo $file | sed -e 's/data2\///')
	html2text -style compact -width 1000000 "$file" | sed '/^[A-Z][A-Z][A-Z] [0-9][0-9][0-9].\?.\?$/ {n;d;}' | sed '/^[A-Z][A-Z][A-Z] [0-9][0-9][0-9].\?.\?$/ {n;d;}' | sed -e '/^[0-9].*[0-9]$/ d' -e 's/^.\?.\?.\?$//' -e '/^$/d' > "data3/$name"
done
