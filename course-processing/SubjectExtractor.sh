#!/bin/bash

printvars (){
	echo "Len1: $len1"
	echo "Len2: $len2"
	echo "Len3: $len3"
	echo "Len4: $len4"
	echo "Len5: $len5"
}

dir=./data

cat "$dir/$1" | grep "course-number-title-separator" | tr -d '\n' | sed -e 's/'$1'<\/span> <span class="course-number">/\n'$1'<\/span> <span class="course-number">/g' | sed -e 's/<\/span>.*class="course-number">/ /g' | sed -e 1d > tmp1.txt
cat tmp1.txt | grep "<span>Prerequisite(s):</span>" > tmp2.txt
cat tmp1.txt | grep -v "<span>Prerequisite(s):</span>" > tmp3.txt

len1=$(wc -l < tmp1.txt)
len2=$(wc -l < tmp2.txt)
len3=$(wc -l < tmp3.txt)

if [ $((len2 + len3 -1)) = $len1 ]
then
	cat tmp2.txt | sed -e 's/<\/span>.*class="course-title">/\n/g' -e 's/<\/span>.*<span>Prerequisite(s):<\/span> <span>/\n/g' -e 's/<\/span> <span>/\n/2' -e 's/<\/span> <span>//' -e 's/<\/span>.*$//' > tmp4.txt

	cat tmp3.txt | sed -e 's/<\/span>.*class="course-title">/\n/g' -e 's/<\/span>.*class="row course-summary-paragraph">//' -e 's/<span>//' -e 's/<span>//' -e 's/<span>/<desstart>/' -e 's/<div.*<desstart>/\n\n/' -e 's/<\/span>.*$//' > tmp5.txt

	len4=$(wc -l < tmp4.txt)
	len5=$(wc -l < tmp5.txt)
	
	if [ $((len2*4)) = $len4 ] && [ $((len3*4)) = $len5 ]
	then
		cat tmp4.txt >> prereq_classes.txt
		cat tmp5.txt >> no_prereq_classes.txt

		echo "$1 parse success"
		exit 0
	else
		echo "$1 Error: secondary parsing fail"
		printvars
		exit 1
	fi
	
else 
	echo "$1 Error: tmp2 and 3 line mismatch"
	printvars
	exit 1
fi
