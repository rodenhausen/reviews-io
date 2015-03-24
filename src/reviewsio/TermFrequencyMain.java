package reviewsio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import weka.core.tokenizers.WordTokenizer;
import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class TermFrequencyMain {

	private static Map<String, Integer> allTermsFrequency = new HashMap<String, Integer>();
	
	public static void main(String[] args) throws IOException {
		String name = "scale";
		final String input = name + ".csv";
		final String output = name + "_termFrequency.csv";

		CSVReader reader = new CSVReader(new FileReader(new File(input)), ',',
				CSVParser.DEFAULT_QUOTE_CHARACTER, '\\');
		CSVWriter writer = new CSVWriter(new FileWriter(new File(output)), ',',
				CSVParser.DEFAULT_QUOTE_CHARACTER, '\\');
		writer.writeNext(new String[] { "term", "count" });

		List<String[]> reviews = reader.readAll();
		for (int r = 1; r < reviews.size(); r++) {
			String[] line = reviews.get(r);

			String user = line[0];
			String item = line[1];
			String rating = line[2];
			// String title = line[3];
			// String review = line[4];
			String review = line[3];

			WordTokenizer wordTokenizer = new WordTokenizer();
			wordTokenizer.tokenize(review);

			List<String> terms = new LinkedList<String>();
			try {
				String term = null;
				while ((term = (String)wordTokenizer.nextElement()) != null)
					terms.add(term);
			} catch (NoSuchElementException e) {
			}
			
			for(String term : terms) {
				if(allTermsFrequency.containsKey(term))
					allTermsFrequency.put(term, 0);
				allTermsFrequency.put(term, allTermsFrequency.get(term));
			}
			
			
			for(String term : allTermsFrequency.keySet()) {
				writer.writeNext(new String[] { term, String.valueOf(allTermsFrequency.get(term)) });
			}
		}

		reader.close();
		writer.close();
	}

}
