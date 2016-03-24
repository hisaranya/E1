package Dictionary;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DictionaryMapper  extends Mapper<Text, Text, Text, Text> {
    // TODO define class variables for translation, language, and file name
    private Text word = new Text();
    private String filename = new String();
    public void setup(Context context) {
        // TODO determine the language of the current file by looking at it's name
        
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        filename = fileSplit.getPath().getName();
        filename = filename.substring(0, filename.toString().lastIndexOf('.') );
        
    }
    
    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        // TODO instantiate a tokenizer based on a regex for the file
        
        String line = value.toString();
        
        if(line.contains("[Verb]") || line.contains("[Noun]") || line.contains("[Adjective]") ||
           line.contains("[Adverb]") || line.contains("[Conjunction]") || line.contains("[Preposition]") ||
           line.contains("[Pronoun]") || line.contains("[interjection]"))
        {
            int startIndexPartsOfSpeech = line.indexOf("[");
            
            String partsOfSpeech = line.substring(startIndexPartsOfSpeech);
            String finalValue = filename+":"+line.substring(0,startIndexPartsOfSpeech);
            String finalKey = key +": " + partsOfSpeech ;
            context.write(new Text(finalKey),new Text(finalValue));
            
        }
    }
    
}