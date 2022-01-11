#!/bin/sh

./gradlew build

rm -rf 60733-0.txt out1 out2 out3

wget http://www.gutenberg.org/files/60733/60733-0.txt
hadoop jar app/build/libs/app.jar l4.WordCount 60733-0.txt out1

./task1.py out1/part-00000

hadoop jar app/build/libs/app.jar l4.WordCount2 60733-0.txt out2
sort -k +3nr out2/part-00000 | head -n20

hadoop jar app/build/libs/app.jar l4.WordCount3 60733-0.txt out3
cat out3/part-00000
