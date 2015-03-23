package reviewsio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import reviewsio.model.Review;

public class ReadFile {

	public static void main(String[] args) {
		String input = "amazon1m.csv";
		try (BufferedReader ratingReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(input)),
						"UTF8"))) {
			String line = null;
			int i = 0;
			while ((line = ratingReader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
