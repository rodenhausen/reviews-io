package reviewsio;

import java.util.List;

import reviewsio.in.Amazon2Reader;
import reviewsio.in.Amazon3Reader;
import reviewsio.in.AmazonReader;
import reviewsio.in.IReader;
import reviewsio.in.ScaleDataReader;
import reviewsio.in.YelpReader;
import reviewsio.model.Review;
import reviewsio.model.TitleReview;
import reviewsio.out.CSVReviewsWriter;

public class Main {
	
	public static void main(String[] args) {
		String scaleData = "scale_data";
		String yelpData = "yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_review.json";
		String amazonData = "amazon-meta.txt/amazon-meta.txt";
		String amazon2Data = "productinfo/productinfo.txt";
		String amazon3Data = "reviewsNew/reviewsNew.txt";
		
		IReader reader = new Amazon3Reader(amazon3Data);
		//IReader reader = new ScaleDataReader(scaleData);
		//IReader reader = new YelpReader(yelpData);
		List<Review> reviews = reader.read();
		
		reviews = normalize(reviews);
		
		CSVReviewsWriter writer = new CSVReviewsWriter("amazon20k.csv");
		writer.write(reviews);
		
		//CSVTFReviewsWriter writer = new CSVTFReviewsWriter("scale_data_out3.csv");
		//writer.write(reviews);
		//TFWriter writer = new TFWriter("tf.txt");
		//ReviewToTFReview c = new ReviewToTFReview();
		//List<TFReview> tfReviews = c.convert(reviews);
		//writer.write(tfReviews);
	}

	private static List<Review> normalize(List<Review> reviews) {
		for(Review review : reviews) {
			if(review instanceof TitleReview) {
				((TitleReview) review).title = ((TitleReview) review).title.replaceAll("\"", "");
			}
			review.text = review.text.replaceAll("\"", "");
			review.text = review.text.replaceAll("\\s+", " ");
		}
		return reviews;
	}
}
