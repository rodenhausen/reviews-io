package reviewsio.model;

import java.util.HashMap;
import java.util.Map;

public class TFReview extends Review {

	public TFReview(Double rating, String text) {
		super(rating, text);
	}

	public TFReview(Review review) {
		super(review.rating, review.text);
	}

	public Map<String, Integer> termFrequency = new HashMap<String, Integer>();

	public Integer getTermFrequency(String term) {
		if(termFrequency.containsKey(term))
			return termFrequency.get(term);
		return 0;
	}
	
}
