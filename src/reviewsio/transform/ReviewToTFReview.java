package reviewsio.transform;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import reviewsio.model.Review;
import reviewsio.model.TFReview;

public class ReviewToTFReview {

	public List<TFReview> convert(List<Review> reviews) {
		List<TFReview> tfReviews = new LinkedList<TFReview>();
		
		Map<String, Integer> allTermsFrequency = new HashMap<String, Integer>();
		for(Review review : reviews) {
			tfReviews.add(createTfReview(review, allTermsFrequency));
		}
		return tfReviews;
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
