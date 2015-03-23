package reviewsio.in;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import reviewsio.model.Review;

public class ScaleDataReader implements IReader {

	private String input;

	public ScaleDataReader(String input) {
		this.input = input;
	}
	
	public List<Review> read() {
		List<Review> reviews = new LinkedList<Review>();
		String[] ratings = {input + "/scaledata/Dennis+Schwartz/rating.Dennis+Schwartz", 
				input + "/scaledata/James+Berardinelli/rating.James+Berardinelli",
				input + "/scaledata/Scott+Renshaw/rating.Scott+Renshaw",
				input + "/scaledata/Steve+Rhodes/rating.Steve+Rhodes"};
		String[] subjs = {input + "/scaledata/Dennis+Schwartz/subj.Dennis+Schwartz", 
				input + "/scaledata/James+Berardinelli/subj.James+Berardinelli",
				input + "/scaledata/Scott+Renshaw/subj.Scott+Renshaw",
				input + "/scaledata/Steve+Rhodes/subj.Steve+Rhodes"};

		int x=0;
		for(int i=0; i<ratings.length; i++) {
			String ratingFile = ratings[i];
			String subjFile = subjs[i];
			try (BufferedReader ratingReader = new BufferedReader(
			           new InputStreamReader(
			                      new FileInputStream(new File(ratingFile)), "UTF8"))) {
				try (BufferedReader subjReader = new BufferedReader(
				           new InputStreamReader(
				                      new FileInputStream(new File(subjFile)), "UTF8"))) {
					String rating = null;
					while((rating = ratingReader.readLine()) != null) {
						String subj = subjReader.readLine();
						Double r = Double.parseDouble(rating);
						r = r * 10;
						
						r = Math.rint(r);
						if(r!=Math.ceil(r)) {
							System.out.println(ratingFile.split("rating.")[1] + " " + rating);
							System.out.println(r);
						}
						
						Review review = new Review(ratingFile.split("rating.")[1], "NA", r, subj);
						reviews.add(review);
						
						//System.out.println(review);
						x++;
					}
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(x);
		}
		return reviews;
	}
	
}
