#!/bin/sh

./gradlew build

rm -rf 2600-0.txt 2600-in journal.log JavaWordCount2.log
wget http://www.gutenberg.org/files/2600/2600-0.txt

stop-all.sh
start-all.sh

mkdir -p 2600-in
for idx in $(seq 1 10); do
	cp 2600-0.txt 2600-in/2600-${idx}.txt
done

spark-submit --class l5.JavaWordCount --master 'spark://127.0.0.2:7077' app/build/libs/app.jar 2600-in

journalctl --boot >journal.log
spark-submit --class l5.LogMining --master 'spark://127.0.0.2:7077' app/build/libs/app.jar journal.log

spark-submit --class l5.JavaWordCount2 --master 'spark://127.0.0.2:7077' app/build/libs/app.jar 2600-in | grep TIME >JavaWordCount2.log
./exp3.py JavaWordCount2.log

((echo "kasdhj lakjsd lkjasd"; echo "asdasd kajkjsd") | nc -l 4444; sleep 10s) &
timeout 10s run-example org.apache.spark.examples.streaming.JavaNetworkWordCount localhost 4444
