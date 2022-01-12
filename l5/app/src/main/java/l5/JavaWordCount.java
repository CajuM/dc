package l5;

import scala.Tuple2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class JavaWordCount {
	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			System.err.println("Usage: JavaWordCount <file or directory>");
			System.exit(1);
		}

		SparkConf sparkConf = new SparkConf().setAppName("JavaWordCount");
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);
		
		ctx.hadoopConfiguration().set("mapreduce.input.fileinputformat.input.dir.recursive","true");
		
		JavaRDD<String> lines = ctx.textFile(args[0], 1);

		JavaRDD<String> words = lines.flatMap((String s) -> Arrays.asList(SPACE.split(s)).iterator());

		JavaPairRDD<String, Integer> ones = words.mapToPair((String s) -> new Tuple2<>(s, 1));

		JavaPairRDD<String, Integer> counts = ones.reduceByKey((Integer i1, Integer i2) -> i1 + i2);

		List<Tuple2<String, Integer>> output = counts.collect();
		for (Tuple2<?,?> tuple : output) {
			System.out.println(tuple._1() + ": " + tuple._2());
		}
		ctx.stop();
	}
}
