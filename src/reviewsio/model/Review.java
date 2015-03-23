package reviewsio.model;

import java.util.HashMap;
import java.util.Map;

public class Review {
	
	public String user;
	public String item;
	public Double rating;
	public String text;
	public Map<String, String> otherColumns = new HashMap<String, String>();
	
	public Review(String user, String item, Double rating, String text) {
		super();
		this.user = user;
		this.item = item;
		this.rating = rating;
		this.text = text;
	}
	
	public String toString() {
		return rating + ": " + text;
	}
}
