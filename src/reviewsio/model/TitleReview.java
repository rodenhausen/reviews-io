package reviewsio.model;

public class TitleReview extends Review {

	public String title;
	
	public TitleReview(String user, String item, Double rating, String title, String text) {
		super(user, item, rating, text);
		this.title = title;
	}

}
