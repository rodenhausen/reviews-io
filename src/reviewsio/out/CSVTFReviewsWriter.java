package reviewsio.out;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import reviewsio.model.Review;
import reviewsio.model.TFReview;

public class CSVTFReviewsWriter {

	private String output;

	public CSVTFReviewsWriter(String output) {
		this.output = output;
	}

	public void write(List<Review> reviews) {
		try (au.com.bytecode.opencsv.CSVWriter csvWriter = new au.com.bytecode.opencsv.CSVWriter(
				new FileWriter(new File(output)))) {			
			List<TFReview> tfReviews = new LinkedList<TFReview>();
			
			Map<String, Integer> allTermsFrequency = new HashMap<String, Integer>();
			for(Review review : reviews) {
				tfReviews.add(createTfReview(review, allTermsFrequency));
			}

			Map<String, Integer> termDocumentCount = new HashMap<String, Integer>();
			Map<String, Integer> allTermsMaxFrequency = new HashMap<String, Integer>();
			createAllTermsMaxFrequency(allTermsFrequency, allTermsMaxFrequency, termDocumentCount, tfReviews);
			
			for(TFReview tfReview : tfReviews) {
				String[] line = new String[allTermsFrequency.size() + 1];
				line[0] = String.valueOf(tfReview.rating);
				
				List<String> allTermsList = new ArrayList<String>(allTermsFrequency.keySet());
				int col = 1;
				for(String term : allTermsList) {
					if(termDocumentCount.get(term) <= 1) 
						continue;
					if(allTermsFrequency.get(term) <= 5)
						continue;
					line[col++] = String.valueOf(tfReview.getTermFrequency(term));
				}
				csvWriter.writeNext(line);
			}
			csvWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createAllTermsMaxFrequency(Map<String, Integer> allTermsFrequency, Map<String, Integer> allTermsMaxFrequency, 
			Map<String, Integer> termDocumentCounts, List<TFReview> tfReviews) {		
		for(String term : allTermsFrequency.keySet()) {
			int max = 0;
			int termDocumentCount = 0;
			for(TFReview r : tfReviews) {
				if(r.getTermFrequency(term) > 0)
					termDocumentCount++;
				if(r.getTermFrequency(term) > max) 
					max = r.getTermFrequency(term);
			}
			allTermsMaxFrequency.put(term, max);
			termDocumentCounts.put(term, termDocumentCount);
		}
	}

	private TFReview createTfReview(Review review, Map<String, Integer> allTermsFrequency) {

	      /*DocumentPreprocessor dp = new DocumentPreprocessor(arg);
	      for (List sentence : dp) {
	        System.out.println(sentence);
	      }
	     
	      PTBTokenizer ptbt = new PTBTokenizer(new FileReader(arg),
	              new CoreLabelTokenFactory(), "");
	      for (CoreLabel label; ptbt.hasNext(); ) {
	        label = ptbt.next();
	        System.out.println(label);
	      }
	    }*/
		
		
		String[] terms = review.text.split("\\s");
		
		TFReview r = new TFReview(review);
		for(String term : terms) {
			if(allTermsFrequency.containsKey(term))
				allTermsFrequency.put(term, 0);
			allTermsFrequency.put(term, allTermsFrequency.get(term));
			
			if(!r.termFrequency.containsKey(term))
				r.termFrequency.put(term, 0);
			r.termFrequency.put(term, r.termFrequency.get(term) + 1);
		}
		
		return r;
	}
}
