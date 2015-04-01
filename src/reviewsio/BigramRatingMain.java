package reviewsio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import weka.core.tokenizers.WordTokenizer;
import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class BigramRatingMain {

	private static Map<String, Map<String, Integer>> allTermsFrequency = new HashMap<String, Map<String, Integer>>();
	private static MaxentTagger tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
	
	public static void main(String[] args) throws IOException {
		String name = "amazon100k";
		final String input = name + ".csv";
		final String output = name + "_bigramRatingFrequency.csv";

		CSVReader reader = new CSVReader(new FileReader(new File(input)), ',',
				CSVParser.DEFAULT_QUOTE_CHARACTER, '\\');
		CSVWriter writer = new CSVWriter(new FileWriter(new File(output)), ',',
				CSVParser.DEFAULT_QUOTE_CHARACTER, '\\');
		writer.writeNext(new String[] { "term",  "rating", "count" });

		List<String[]> reviews = reader.readAll();
		for (int r = 1; r < reviews.size(); r++) {
			String[] line = reviews.get(r);

			String user = line[0];
			String item = line[1];
			String rating = line[2];
			String title = line[3].toLowerCase();
			String review = line[4].toLowerCase();
			//String review = line[3].toLowerCase();

			PTBTokenizer<Word> ptbt = PTBTokenizer.newPTBTokenizer(new StringReader(review));
			
			LinkedList<String> gramList = new LinkedList<String>();
			for(Word word : ptbt.tokenize()) {
				gramList.add(word.word());
				if(gramList.size() == 2) {
					String gram = gramList.get(0) + " " + gramList.get(1);
					if(!allTermsFrequency.containsKey(gram))
						allTermsFrequency.put(gram, new HashMap<String, Integer>());
					Map<String, Integer> sub1 = allTermsFrequency.get(gram);
					if(!sub1.containsKey(rating))
						sub1.put(rating, 0);
					sub1.put(rating, sub1.get(rating) + 1);
					gramList.removeFirst();
				}
			}
	    }

		for(String gram : allTermsFrequency.keySet()) {
			for(String rating : allTermsFrequency.get(gram).keySet()) {
				int count = allTermsFrequency.get(gram).get(rating);
				writer.writeNext(new String[] { gram, rating, String.valueOf(count) });
			}
		}

		reader.close();
		writer.close();
	}

}
