#!/bin/bash

dir=./data

for file in $dir/*
do
	./SubjectExtractor.sh $(echo $file | sed -e 's/.\/data\///')
done
