package reviewsio.in;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import reviewsio.model.Review;
import reviewsio.model.TitleReview;

public class Amazon3Reader implements IReader {
	
	private String input;
	private static String whitespaceButTabAndSpace = "[\\n\\x0B\\f\\r]";

	public Amazon3Reader(String input) {
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
				line = line.replaceAll(whitespaceButTabAndSpace, " ");
				line = line.replaceAll("[\\x00-\\x08\\x10-\\x1F\\x7F]", "");
				try {
					//System.out.println(line);
					int p1 = line.indexOf("\t");
					String user = line.substring(0, p1);
					String newline = line.substring(p1 + 1, line.length());
					//System.out.println(newline);
					int p2 = newline.indexOf("\t");
					String item = line.substring(0, p2);
					newline = newline.substring(p2 + 1, newline.length());
					//System.out.println(newline);
					int p3 = newline.indexOf("\t");
					newline = newline.substring(p3 + 1, newline.length());
					//System.out.println(newline);
					int p4 = newline.indexOf("\t");
					newline = newline.substring(p4 + 1, newline.length());
					//System.out.println(newline);
					int p5 = newline.indexOf("\t");
					newline = newline.substring(p5 + 1, newline.length());
					//System.out.println(newline);				
					int p6 = newline.indexOf("\t");
					String rating = newline.substring(0, p6);
					newline = newline.substring(p6 + 1, newline.length());
					//System.out.println(newline);
					int p7 = newline.indexOf("\t");
					
					String title = "";
					String text = "";
					if(p7 == -1) {
						System.out.println("p7 == -1");
						title = newline;
						line = ratingReader.readLine();
						System.out.println(line);
						if(line != null) {
							text = line.trim();
						}
					} else {
						title = newline.substring(0, p7);
						newline = newline.substring(p7 + 1, newline.length());
						text = newline;
					}

					/*String[] parts = line.split("\t");
					String rating = parts[parts.length - 3];
					String title = parts[parts.length - 2];
					String text =parts[parts.length - 1];*/
					
					/*String rating = newline.substring(p5, p6);
					String title = newline.substring(p6, p7);
					String text =newline.substring(p7, newline.length());*/
	
					/*System.out.println("Rating: " + rating);
					System.out.println("Title: " + title);
					System.out.println("Text: " + text);*/
					
					Double ratingD = Double.valueOf(rating.trim());
					if(isDot5(ratingD)) {
						System.out.println("filter out a .5 : " + ratingD);
						continue;
					} else {
						ratingD = Math.rint(ratingD);
						
						if(ratingD!=Math.ceil(ratingD)) {
							//System.out.println(ratingD);
						}
						
						Review review = new TitleReview(user, item, ratingD, title.trim(), text.trim());
						reviews.add(review);
						
						i++;
						if (i == 20000)
							break;
					}	
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println(line);
				}
			}
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	private boolean isDot5(Double ratingD) {
		String text = Double.toString(Math.abs(ratingD));
		return text.matches(".*\\.50*");//(".5") || text.endsWith(".50") || text.endsWith(".50");
	}

	public static void main(String[] args) {
		String input = "C:/Users/Thomas/Downloads/reviewsNew/reviewsNew.txt";
		
		
		try (au.com.bytecode.opencsv.CSVWriter csvWriter = new au.com.bytecode.opencsv.CSVWriter(
				new FileWriter(new File("amazon3.csv")))) {
			csvWriter.writeNext(new String[] {"Rating", "Review"});
			csvWriter.flush();
			
			try (BufferedReader ratingReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(input)),
							"UTF8"))) {
				String line = null;
				int i = 0;
				while ((line = ratingReader.readLine()) != null) {
					line = line.replaceAll(whitespaceButTabAndSpace, " ");
					line = line.replaceAll("[\\x00-\\x08\\x10-\\x1F\\x7F]", "");
					try {
						//System.out.println(line);
						int p1 = line.indexOf("\t");
						String newline = line.substring(p1 + 1, line.length());
						//System.out.println(newline);
						int p2 = newline.indexOf("\t");
						newline = newline.substring(p2 + 1, newline.length());
						//System.out.println(newline);
						int p3 = newline.indexOf("\t");
						newline = newline.substring(p3 + 1, newline.length());
						//System.out.println(newline);
						int p4 = newline.indexOf("\t");
						newline = newline.substring(p4 + 1, newline.length());
						//System.out.println(newline);
						int p5 = newline.indexOf("\t");
						newline = newline.substring(p5 + 1, newline.length());
						//System.out.println(newline);				
						int p6 = newline.indexOf("\t");
						String rating = newline.substring(0, p6);
						newline = newline.substring(p6 + 1, newline.length());
						//System.out.println(newline);
						int p7 = newline.indexOf("\t");
						
						String title = "";
						String text = "";
						if(p7 == -1) {
							System.out.println("p7 == -1");
							title = newline;
							line = ratingReader.readLine();
							System.out.println(line);
							if(line != null) {
								text = line.trim();
							}
						} else {
							title = newline.substring(0, p7);
							newline = newline.substring(p7 + 1, newline.length());
							text = newline;
						}
	
						/*String[] parts = line.split("\t");
						String rating = parts[parts.length - 3];
						String title = parts[parts.length - 2];
						String text =parts[parts.length - 1];*/
						
						/*String rating = newline.substring(p5, p6);
						String title = newline.substring(p6, p7);
						String text =newline.substring(p7, newline.length());*/
		
						/*System.out.println("Rating: " + rating);
						System.out.println("Title: " + title);
						System.out.println("Text: " + text);*/
						
						Double ratingD = Double.valueOf(rating.trim());
						
						csvWriter.writeNext(new String[] { String.valueOf(ratingD), title, 
										text });
						i++;
						
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println(line);
					}
				}
				System.out.println(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
