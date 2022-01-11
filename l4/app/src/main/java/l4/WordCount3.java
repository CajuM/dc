package l4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class WordCount3 {
	public static class TupleWritablePP implements Writable {
		private IntWritable v1;
		private IntWritable v2;

		public TupleWritablePP() {
			this.v1 = new IntWritable();
			this.v2 = new IntWritable();
		}

		public TupleWritablePP(int v1, int v2) {
			this.v1 = new IntWritable(v1);
			this.v2 = new IntWritable(v2);
		}

		public int get1() {
			return this.v1.get();
		}

		public int get2() {
			return this.v2.get();
		}

		@Override
		public void readFields(DataInput in) throws IOException {
			v1.readFields(in);
			v2.readFields(in);
		}

		@Override
		public void write(DataOutput out) throws IOException {
			v1.write(out);
			v2.write(out);
		}

		@Override
		public String toString() {
			return String.format("%d %d %f", this.v1.get(), this.v2.get(), ((double) this.v2.get()) / this.v1.get());
		}
	}

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, TupleWritablePP> {
		public void map(LongWritable key, Text value, OutputCollector<Text, TupleWritablePP> output, Reporter reporter) throws IOException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				char a = Character.toLowerCase(token.charAt(0));
				if ((a > 'z') || (a < 'a'))
					continue;

				Text k = new Text(new String(new char[] { a }));
				TupleWritablePP v = new TupleWritablePP(1, token.length());
				output.collect(k, v);
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, TupleWritablePP, Text, TupleWritablePP> {
		public void reduce(Text key, Iterator<TupleWritablePP> values, OutputCollector<Text, TupleWritablePP> output, Reporter reporter) throws IOException {
			int sum = 0;
			int totalLength = 0;

			while (values.hasNext()) {
				TupleWritablePP w = values.next();
				sum += w.get1();
				totalLength += w.get2();
			}

			TupleWritablePP t = new TupleWritablePP(sum, totalLength);
			output.collect(key, t);
		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(WordCount.class);
		conf.setJobName("wordcount");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(TupleWritablePP.class);

		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
	}
}

