package Dictionary;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DictionaryReducer extends Reducer<Text,Text,Text,Text> {

private Text result = new Text();


   public void reduce(Text word, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
      // TODO iterate through values, parse, transform, and write to context


   	 String translations = "";
            for (Text val : values)
            {
                translations += "|"+val.toString();
   }
    result.set(translations);
            context.write(key, result);
}
}
