package reviewsio.out;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import reviewsio.model.Review;
import reviewsio.model.TitleReview;

public class CSVReviewsWriter {

	private String output;

	public CSVReviewsWriter(String output) {
		this.output = output;
	}

	public void write(List<Review> reviews) {
		try (au.com.bytecode.opencsv.CSVWriter csvWriter = new au.com.bytecode.opencsv.CSVWriter(
				new FileWriter(new File(output)), ',', au.com.bytecode.opencsv.CSVWriter.DEFAULT_QUOTE_CHARACTER, '\\')) {
			if(reviews.get(0) instanceof TitleReview)
				csvWriter.writeNext(new String[] {"User", "Item", "Rating", "Title", "Review"});
			else
				csvWriter.writeNext(new String[] {"User", "Item", "Rating", "Review"});
			for(Review review : reviews) {
				if(review instanceof TitleReview) 
					csvWriter.writeNext(new String[] { String.valueOf(review.user), String.valueOf(review.item),
							String.valueOf(review.rating), ((TitleReview)review).title, 
							review.text });
				else
					csvWriter.writeNext(new String[] { String.valueOf(review.user), String.valueOf(review.item),
							String.valueOf(review.rating), review.text });
			}
			csvWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
