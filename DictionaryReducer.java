package Dictionary;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.*;

public class DictionaryReducer extends Reducer<Text,Text,Text,Text> {
    
    Map<String, String> translationMap;
    
    public void reduce(Text word, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
        // TODO iterate through values, parse, transform, and write to context
        translationMap = this.initialTranslationMap();
        System.out.println("Key " + word);
        StringBuilder valueString = new StringBuilder();
        for(Text value: values) {
            String language = this.getLanguage(value.toString());
            //System.out.println("Value " + value + " Language " + language);
            this.handleTranslationValue(language, value.toString());
        }
        String finalValue = this.FinalOutputValue();
        context.write(word, new Text(finalValue));
   	}
    
    
    
    private LinkedHashMap<String, String> initialTranslationMap() {
        LinkedHashMap<String, String> languageHashMap = new LinkedHashMap<String, String>();
        languageHashMap.put("french", "N/A");
        languageHashMap.put("german", "N/A");
        languageHashMap.put("italian", "N/A");
        languageHashMap.put("portuguese", "N/A");
        languageHashMap.put("spanish", "N/A");
        return languageHashMap;
    }
    
    

    
    private String trimLanguage(String input) {
        int position = input.indexOf(':') + 1;
        String trimedValue;
        if(position > 0) trimedValue = input.substring(position);
        else trimedValue = input;
        return trimedValue;
    }
    
    
    private String getLanguage(String input) {
        int position = input.indexOf(':');
        String language;
        if(position != -1) language = input.substring(0, position);
        else language = input;
        return language;
    }
    
    
    
    private void handleTranslationValue(String language, String value) {
        String transValue = translationMap.get(language);
        if(transValue != null) {
            if(transValue.equals("N/A")) translationMap.put(language, value);
            else {
                String valueWithoutLangauge = this.trimLanguage(value);
                StringBuilder newValue = new StringBuilder();
                newValue.append(translationMap.get(language)).append(", ").append(valueWithoutLangauge);
                translationMap.put(language, newValue.toString());
            }
        }
    }
    
    
    private String FinalOutputValue() {
        StringBuilder finalValue = new StringBuilder();
        Iterator<Map.Entry<String, String>> itr = translationMap.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry thisEntry = (Map.Entry<String, String>) itr.next();
            String value = (String) thisEntry.getValue();
            if(value.equals("N/A")) {
                String key = (String) thisEntry.getKey();
                finalValue.append(key).append(":").append(value);	
            }
            else finalValue.append(value);
            if(itr.hasNext()) finalValue.append("|");
        }
        return finalValue.toString();	
    }
}
