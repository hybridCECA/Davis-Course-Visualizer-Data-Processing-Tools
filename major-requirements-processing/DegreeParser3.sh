#!/bin/bash

for file in data3/*
do
	name=$(echo $file | sed -e 's/data3\///')
	cat "$file" | grep -B1 -e "^[A-Z][A-Z][A-Z] [0-9][0-9][0-9][A-Z]\?[A-Z]\?$" | sed -e '/^--$/d' > "data4/$name"
done
