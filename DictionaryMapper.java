package Dictionary;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class DictionaryMapper  extends Mapper<Text, Text, Text, Text> {
      // TODO define class variables for translation, language, and file name
      private Text word = new Text();


      public void setup(Context context) {    	   
      // TODO determine the language of the current file by looking at it's name

      	//FileSplit fileSplit = (FileSplit)context.getInputSplit();
        //String filename = fileSplit.getPath().getName();

      }

      public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
      // TODO instantiate a tokenizer based on a regex for the file

  StringTokenizer itr = new StringTokenizer(value.toString(),",");
            while (itr.hasMoreTokens())
            {
                word.set(itr.nextToken());
                context.write(key, word);
            }
        }      

//if (line.trim().startsWith("#"))
	//{
	//    continue; // continue while() loop
   // }

      // iterate through the tokens of the line, parse, transform and write to context
      }

