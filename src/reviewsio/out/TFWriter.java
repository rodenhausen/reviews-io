package reviewsio.out;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import reviewsio.model.TFReview;

public class TFWriter {

	private String output;

	public TFWriter(String output) {
		this.output = output;
	}
	
	public void write(List<TFReview> reviews) {
		Map<String, Integer> termCounts = new HashMap<String, Integer>();
		for(TFReview r : reviews) {
			for(String term : r.termFrequency.keySet()) {
				if(!termCounts.containsKey(term))
					termCounts.put(term, 0);
				termCounts.put(term, termCounts.get(term) + 1);
			}
		}
		
		try (au.com.bytecode.opencsv.CSVWriter csvWriter = new au.com.bytecode.opencsv.CSVWriter(
				new FileWriter(new File(output)))) {	
			csvWriter.writeNext(new String[] { "term", "frequency" });
			for(String term : termCounts.keySet()) 
				csvWriter.writeNext(new String[] { term, String.valueOf(termCounts.get(term)) });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
