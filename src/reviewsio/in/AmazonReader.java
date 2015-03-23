package reviewsio.in;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import reviewsio.model.Review;

public class AmazonReader implements IReader {
	
	private String input;

	public AmazonReader(String input) {
		this.input = input;
	}
	
	public List<Review> read() {
		List<Review> reviews = new LinkedList<Review>();

		try (BufferedReader ratingReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(input)),
						"UTF8"))) {
			String line = null;
			int i = 0;
			while ((line = ratingReader.readLine()) != null) {
				/*JSONObject elements = new JSONObject(line);
				String stars = elements.getString("stars");
				String text = elements.getString("text");

				Review review = new Review(Integer.valueOf(stars), text);
				// Review review = new Review(r.intValue(), subj);
				reviews.add(review);
				i++;
				if (i > 1000)
					break;
				System.out.println(review);*/
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}
}
