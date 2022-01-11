The application can be built using the gradle build-system and runs on Java 11.
It contains three classes in the app/src/main/java/l4 directory, one for each exercise.
In order to evaluate, run the runme.sh script. It will compile all classes and run the
appropriate experiments.

For exercise 1, the WordCount.java file was added to the project as it was and an
attempt was made to fit the resulting histogram to Zipf's law, it does respect
Zipf's law. A histogram will be show on-screen, to proceed with the remainder of the
exercises quit the matplotlib window.

For exercise 2, WordCount2.java was modified to pass as values every token tuple. The
top 20 words and thier frequencies are displayed on-screen.

For exercise 3, a custom Writeable was implemented in order to hold both the number of
words and their total lengths for each character. Only words begining with ASCII leters
were taken into account, and the keys were lower-case letters. Given the low number of
operations required in order to calculate the average word length, once for each (k, v)
pair in the output, it would be best to not implement it in map-reduce. In WordCount3
it is calculated by abusing the toString function.
