package reviewsio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import reviewsio.in.Amazon3Reader;
import reviewsio.in.IReader;
import reviewsio.model.Review;
import reviewsio.out.CSVReviewsWriter;
import weka.core.tokenizers.WordTokenizer;

public class AvgReviewLengthMain {

	public static void main(String[] args) throws IOException {
		final String input = "amazon100k.csv";
		final String output = "amazon100k_reviewLength.csv";
		
		CSVReader reader = new CSVReader(new FileReader(new File(input)), ',', CSVParser.DEFAULT_QUOTE_CHARACTER, '\\');
		CSVWriter writer = new CSVWriter(new FileWriter(new File(output)), ',', CSVParser.DEFAULT_QUOTE_CHARACTER, '\\');
		writer.writeNext(new String[] { "count" });
		
		double averageLength = 0;
		
		List<String[]> reviews = reader.readAll();
		for(int r = 1; r<reviews.size(); r++) {
			String[] line = reviews.get(r);
			
			String user = line[0];
			String item = line[1];
			String rating = line[2];
			String title = line[3];
			String review = line[4];
			//String review = line[3];
			
			WordTokenizer wordTokenizer = new WordTokenizer();
			wordTokenizer.tokenize(review);
			
			int terms=0;
			try { 
				while(wordTokenizer.nextElement() != null)
					terms++;
			} catch(NoSuchElementException e) {
				System.out.println("review length " + terms);
			}	
			averageLength += terms;
			
			writer.writeNext(new String[] { String.valueOf(terms) });
		}
		
		reader.close();
		writer.close();
		System.out.println(averageLength / reviews.size() -1);
	}
	
}
