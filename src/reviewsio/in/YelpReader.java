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

import org.json.JSONObject;

import reviewsio.model.Review;

public class YelpReader implements IReader {

	private String input;

	public YelpReader(String input) {
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
				//System.out.println(line);
				JSONObject elements = new JSONObject(line);
				String user = elements.getString("user_id");
				String item = elements.getString("business_id");
				String stars = elements.getString("stars");
				String text = elements.getString("text");
				
				Double v = Double.valueOf(stars);
				if(v!=Math.ceil(v)) {
					//System.out.println(v);
				}
				
				Review review = new Review(user, item, v, text);
				// Review review = new Review(r.intValue(), subj);
				reviews.add(review);
				i++;
				
				//if (i == 50000)
				//	break;
			}
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}

}
