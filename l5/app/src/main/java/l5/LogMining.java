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

public final class LogMining {
	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			System.err.println("Usage: JavaWordCount <file or directory>");
			System.exit(1);
		}

		SparkConf sparkConf = new SparkConf().setAppName("LogMining");
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);
		
		ctx.hadoopConfiguration().set("mapreduce.input.fileinputformat.input.dir.recursive","true");
		
		JavaRDD<String> lines = ctx
			.textFile(args[0], 1)
			.filter(line -> line.contains("ERROR"));

		for (String line : lines.collect()) {
			System.out.println(line);
		}
		ctx.stop();
	}
}
